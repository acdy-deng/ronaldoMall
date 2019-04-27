package com.cheery.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.cheery.common.*;
import com.cheery.pojo.Cart;
import com.cheery.pojo.Order;
import com.cheery.pojo.OrderItem;
import com.cheery.pojo.Product;
import com.cheery.repository.CartRepository;
import com.cheery.repository.OrderItemRepository;
import com.cheery.repository.OrderRepository;
import com.cheery.repository.ProductRepository;
import com.cheery.service.IOrderService;
import com.cheery.util.BigDecimalUtil;
import com.cheery.util.FtpUtil;
import com.cheery.vo.OrderItemVo;
import com.cheery.vo.OrderProductVo;
import com.cheery.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.cheery.util.SnowFlakeUtil.snowFlake;

/**
 * @desc: 订单业务逻辑层接口实现
 * @className: OrderServiceImpl
 * @author: RONALDO
 * @date: 2019-03-10 20:28
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private StrengthenQuery strengthenQuery;

    @Autowired
    private PojoConvertVo pojoConvertVo;

    private static AlipayTradeService tradeService;

    static {
        Configs.init("zfbinfo.properties");
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> createOrder(Long userId, Long shippingId) {
        // 从购物车中获取数据
        List<Cart> cartList = cartRepository.findAllByUserIdAndChecked(userId, 1);
        ApiResult<List<OrderItem>> result = strengthenQuery.getCartOrderItem(userId, cartList);
        if (!result.isSuccess()) {
            return result;
        }
        // 计算该订单总价
        List<OrderItem> orderItemList = result.getData();
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ApiResult.createByErrorMsg("购物车为空");
        }
        BigDecimal payment = strengthenQuery.getOrderTotalPrice(orderItemList);
        // 生成订单
        Order order = strengthenQuery.assembleOrder(userId, shippingId, payment);
//        order.setId(System.currentTimeMillis() + new Random().nextInt(99));
        if (null == orderRepository.save(order)) {
            return ApiResult.createByErrorMsg("订单生成错误");
        }
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        // 批量插入
        orderItemRepository.save(orderItemList);
        // 减少库存数据
        strengthenQuery.reduceProductStoct(orderItemList);
        // 清空购物车
        strengthenQuery.clearCart(cartList);
        return ApiResult.createBySuccessData(pojoConvertVo.assembleOrderVo(order, orderItemList));
    }

    @Override
    public ApiResult<?> cancelOrder(Long userId, Long orderNo) {
        Order order = orderRepository.findByUserIdAndOrderNo(userId, orderNo);
        if (null == order) {
            return ApiResult.createByErrorMsg("该订单不存在");
        }
        Order newOrder = new Order();
        newOrder.setId(order.getId());
        newOrder.setStatus(OrderStatus.ORDER_CLOSE.getCode());
        if (null == orderRepository.save(newOrder)) {
            return ApiResult.createByErrorMsg("订单取消失败");
        }
        return ApiResult.createBySuccessMsg("订单取消成功");
    }

    @Override
    public ApiResult<?> getOrderInfo(Long userId, Long orderNo) {
        OrderProductVo vo = new OrderProductVo();
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        // 从购物车中获取数据
        List<Cart> cartList = cartRepository.findAllByUserIdAndChecked(userId, 1);
        ApiResult<List<OrderItem>> result = strengthenQuery.getCartOrderItem(userId, cartList);
        if (!result.isSuccess()) {
            return result;
        }
        // 计算该订单总价
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem item : result.getData()) {
            payment = BigDecimalUtil.add(payment.doubleValue(), item.getTotalPrice().doubleValue());
            orderItemVoList.add(pojoConvertVo.assembleOrderItemVo(item));
        }
        vo.setProductTotalPrice(payment);
        vo.setOrderItemVoList(orderItemVoList);
        vo.setImageHost("http://106.13.45.248/img/");
        return ApiResult.createBySuccessData(vo);
    }

    @Override
    public ApiResult<?> getOrderDetails(Long userId, Long orderNo) {
        Order order = orderRepository.findByUserIdAndOrderNo(userId, orderNo);
        if (null != order) {
            List<OrderItem> orderItemList = orderItemRepository.findAllByUserIdAndOrderNo(userId, orderNo);
            return ApiResult.createBySuccessData(pojoConvertVo.assembleOrderVo(order, orderItemList));
        }
        return ApiResult.createByErrorMsg("该订单不存在");
    }

    @Override
    public ApiResult<?> getOrderList(Integer page, Integer size, Long userId) {
        Pageable pageable = new PageRequest(page, size);
        List<Order> orderList = orderRepository.findAllByUserId(userId);
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            Page<OrderItem> orderItems = orderItemRepository.findAll(new Specification<OrderItem>() {
                @Override
                public Predicate toPredicate(Root<OrderItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = Lists.newArrayList();
                    list.add(criteriaBuilder.equal(root.get("userId"), userId));
                    Predicate[] predicates = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(predicates));
                }
            }, pageable);
            orderVoList.add(pojoConvertVo.assembleOrderVo(order, orderItems.getContent()));
        }
        PageInfo pageResult = new PageInfo(orderVoList);
        pageResult.setList(orderVoList);
        return ApiResult.createBySuccessMsgAndData("查询成功", pageResult);
    }

    @Override
    public ApiResult<?> pay(long orderNo, Long userId, String path) {
        Map<String, String> resultMap = Maps.newHashMap();
        Order order = orderRepository.findByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ApiResult.createByErrorMsg("用户没有该订单");
        }
        resultMap.put("orderNo", String.valueOf(order.getOrderNo()));
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        String outTradeNo = String.valueOf(order.getOrderNo());

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "\uD83D\uDE33AWMALL商城,订单号:" + outTradeNo;

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "订单" + outTradeNo + "购买商品共" + totalAmount + "元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "RONALDO";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "10000001";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为60分钟
        String timeoutExpress = "60m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        // 获取订单列表
        List<OrderItem> orderItemList = orderItemRepository.findAllByUserIdAndOrderNo(userId, orderNo);
        for (OrderItem orderItem : orderItemList) {
            GoodsDetail goods = GoodsDetail.newInstance(
                    orderItem.getProductId().toString(),
                    orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), new Double(100).doubleValue()).longValue(),
                    orderItem.getQuantity());
            goodsDetailList.add(goods);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                File folder = new File(path);
                if (!folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                // 需要修改为运行机器上的路径
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
                File targetFile = new File(path, qrFileName);
                try {
                    FtpUtil.uploadFile(targetFile);
                } catch (IOException e) {
                    logger.error("上传二维码异常", e);
                }
                logger.info("qrPath:" + qrPath);
                String qrUrl = "http://106.13.45.248/img/" + targetFile.getName();
                resultMap.put("qrUrl", qrUrl);
                return ApiResult.createBySuccessData(resultMap);
            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return ApiResult.createByErrorMsg("支付宝预下单失败!!!");
            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return ApiResult.createByErrorMsg("系统异常，预下单状态未知!!!");
            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return ApiResult.createByErrorMsg("不支持的交易状态，交易返回异常!!!");
        }
    }

    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

}