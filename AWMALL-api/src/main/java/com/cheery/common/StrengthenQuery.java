package com.cheery.common;

import com.cheery.pojo.Cart;
import com.cheery.pojo.Order;
import com.cheery.pojo.OrderItem;
import com.cheery.pojo.Product;
import com.cheery.repository.CartRepository;
import com.cheery.repository.OrderRepository;
import com.cheery.repository.ProductRepository;
import com.cheery.util.BigDecimalUtil;
import com.cheery.vo.CartProductVo;
import com.cheery.vo.CartVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.cheery.util.SnowFlakeUtil.snowFlake;

/**
 * @desc: 增强各表之间的关联查询 用于接口实现类
 * @className: StrengthenQuery
 * @author: RONALDO
 * @date: 2019-03-12 15:26
 */
@Component
public class StrengthenQuery {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * desc: 购物车高复用返回数据
     *
     * @param userId 用户id
     * @return CartVo
     * @auther RONALDO
     * @date: 2019-03-04 15:43
     */
    public CartVo getCartVoLimit(Long userId) {
        CartVo vo = new CartVo();
        // 查询当前用户的购物车列表
        List<Cart> cartList = cartRepository.findAllByUserId(userId);
        // 初始化一个CartProductVo
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        // 初始化购物车总价
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (0 < cartList.size()) {
            for (Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());
                // 获取产品
                Product product = productRepository.findOne(cartItem.getProductId());
                if (null != product) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductPrice(product.getDiscountPrice());
                    cartProductVo.setProductStock(product.getStock());
                    // 判断库存
                    int buyCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        // 库存充足的时候
                        buyCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Constant.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyCount = product.getStock();
                        cartProductVo.setLimitQuantity(Constant.Cart.LIMIT_NUM_FAIL);
                        // 更新购物车的有效库存
                        Cart cart = new Cart();
                        cart.setId(cartItem.getId());
                        cart.setQuantity(buyCount);
                        cartRepository.save(cart);
                    }
                    cartProductVo.setQuantity(buyCount);
                    // 计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    // 设置为选择状态
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if (cartItem.getChecked() == Constant.Cart.CHECKED) {
                    // 如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        vo.setCartTotalPrice(cartTotalPrice);
        vo.setCartProductVoList(cartProductVoList);
        vo.setAllChecked(getAllCheckedStatus(userId));
        return vo;
    }

    /**
     * desc:  判断是否为全选
     *
     * @param userId 用户id
     * @return boolean
     * @auther RONALDO
     * @date: 2019-03-04 15:43
     */
    private boolean getAllCheckedStatus(Long userId) {
        if (userId == null) {
            return false;
        }
        return cartRepository.findCartProductCheckStatusByUserId(userId) == 0;
    }

    /**
     * desc: 清空购物车
     *
     * @param cartList 购物车列表
     * @return
     * @auther RONALDO
     * @date: 2019-03-12 21:21
     */
    public void clearCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartRepository.delete(cart.getId());
        }
    }

    /**
     * desc: 减少库存数量
     *
     * @param orderItemList 订单详情列表
     * @return
     * @auther RONALDO
     * @date: 2019-03-12 21:16
     */
    public void reduceProductStoct(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productRepository.findOne(orderItem.getProductId());
            // 减少库存
            product.setStock(product.getStock() - orderItem.getQuantity());
            // 更新库存
            productRepository.save(product);
        }
    }

    /**
     * desc: 生成订单
     *
     * @param userId   用户名
     * @param shipping 购物车id
     * @param payment  总价
     * @return Order
     * @auther RONALDO
     * @date: 2019-03-12 15:49
     */
    public Order assembleOrder(Long userId, Long shipping, BigDecimal payment) {
        Order order = new Order();
        order.setOrderNo(snowFlake());
        order.setStatus(OrderStatus.NO_PAY.getCode());
        order.setShippingId(shipping);
        order.setPayment(payment);
        order.setUserId(userId);
        orderRepository.save(order);
        return order;
    }

    /**
     * desc: 获取订单总价
     *
     * @param orderItemList 订单详情列表
     * @return BigDecimal
     * @auther RONALDO
     * @date: 2019-03-12 15:47
     */
    public BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    /**
     * desc: 获取订单详情
     *
     * @param userId   用户名
     * @param cartList 订单列表
     * @return ApiResult<List < OrderItem>>
     * @auther RONALDO
     * @date: 2019-03-12 15:48
     */
    public ApiResult<List<OrderItem>> getCartOrderItem(Long userId, List<Cart> cartList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(cartList)) {
            return ApiResult.createByErrorMsg("购物车为空");
        }
        // 效验购物车的数据
        for (Cart cart : cartList) {
            OrderItem orderItem = new OrderItem();
            Product product = productRepository.findOne(cart.getProductId());
            // 判断商品是否正常状态
            if (1 != product.getStatus()) {
                return ApiResult.createByErrorMsg("该商品不在售卖状态");
            }
            // 效验库存
            if (product.getStock() < cart.getQuantity()) {
                return ApiResult.createByErrorMsg("该商品库存不足");
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getSubtitle());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cart.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ApiResult.createBySuccessData(orderItemList);
    }

}
