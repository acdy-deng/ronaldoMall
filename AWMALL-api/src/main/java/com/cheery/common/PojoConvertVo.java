package com.cheery.common;

import com.cheery.pojo.*;
import com.cheery.repository.ShippingRepository;
import com.cheery.util.DateTimeUtil;
import com.cheery.vo.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc: 用于pojo对象转vo对象
 * @className: PojoToVo
 * @author: RONALDO
 * @date: 2019-03-03 17:11
 */
@Component
public class PojoConvertVo {

    @Autowired
    private ShippingRepository shippingRepository;

    /**
     * desc: 用于产品pojo转vo -- 基本信息
     *
     * @param p 产品实体
     * @return ProductListVo
     * @auther RONALDO
     * @date: 2019-03-01 14:16
     */
    public ProductListVo assembleProductListVo(Product p) {
        ProductListVo vo = new ProductListVo();
        vo.setId(p.getId());
        vo.setSubtitle(p.getSubtitle());
        vo.setBrand(p.getBrand().getName());
        vo.setStyle(p.getStyle().getName());
        vo.setPrice(p.getPrice());
        vo.setDiscountPrice(p.getDiscountPrice());
        vo.setIshot(p.getIshot());
        vo.setIsboutique(p.getIsboutique());
        vo.setIsspike(p.getIsspike());
        vo.setSale(p.getSale());
        return vo;
    }

    /**
     * desc: 用于产品pojo转vo -- 详情信息
     *
     * @param p 产品实体
     * @return ProductListVo
     * @auther RONALDO
     * @date: 2019-03-03 16:57
     */
    public ProductDetailsVo assembleProductDetailsVo(Product p) {
        ProductDetailsVo vo = new ProductDetailsVo();
        vo.setId(p.getId());
        vo.setSubtitle(p.getSubtitle());
        vo.setBrand(p.getBrand().getName());
        vo.setStyle(p.getStyle().getName());
        vo.setMainImage(p.getMainImage());
        vo.setSubImages(p.getSubImages());
        vo.setDetail(p.getDetail());
        vo.setPrice(p.getPrice());
        vo.setDiscountPrice(p.getDiscountPrice());
        vo.setIshot(p.getIshot());
        vo.setIsboutique(p.getIsboutique());
        vo.setIsspike(p.getIsspike());
        vo.setStock(p.getStock());
        vo.setSale(p.getSale());
        return vo;
    }

    /**
     * desc: 用于订单 和 订单明细pojo转vo
     *
     * @param order         订单对象
     * @param orderItemList 订单详情集合
     * @return OrderVo
     * @auther RONALDO
     * @date: 2019-03-12 22:00
     */
    public OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo vo = new OrderVo();
        vo.setOrderNo(order.getOrderNo());
        vo.setPayment(order.getPayment());
        vo.setStatus(order.getStatus());
        vo.setStatusDesc(OrderStatus.codeOf(order.getStatus()).getValue());
        vo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        vo.setShipping(order.getShippingId());
        Shipping shipping = shippingRepository.findOne(order.getShippingId());
        if (null != shipping) {
            vo.setUserName(shipping.getReceiverName());
            vo.setShippingVo(this.assembleShippingVo(shipping));
        }
        vo.setImageHost("http://106.13.45.248/img/");
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            orderItemVoList.add(this.assembleOrderItemVo(orderItem));
        }
        vo.setOrderItemVoList(orderItemVoList);
        return vo;
    }

    /**
     * desc: 用于收货地址pojo转vo
     *
     * @param shipping 收货地址对象
     * @return ShippingVo
     * @auther RONALDO
     * @date: 2019-03-12 22:18
     */
    private ShippingVo assembleShippingVo(Shipping shipping) {
        ShippingVo vo = new ShippingVo();
        vo.setReceiverAddress(shipping.getReceiverAddress());
        vo.setReceiverCity(shipping.getReceiverCity());
        vo.setReceiverDistrict(shipping.getReceiverDistrict());
        vo.setReceiverName(shipping.getReceiverName());
        vo.setReceiverProvince(shipping.getReceiverProvince());
        vo.setReceiverPhone(vo.getReceiverPhone());
        return vo;
    }

    /**
     * desc: 用于订单详情pojo转vo
     *
     * @param orderItem 订单详情
     * @return OrderItemVo
     * @auther RONALDO
     * @date: 2019-03-12 22:35
     */
    public OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo vo = new OrderItemVo();
        vo.setCreatetime(orderItem.getCreatetime());
        vo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        vo.setOrderNo(orderItem.getOrderNo());
        vo.setProductId(orderItem.getProductId());
        vo.setProductImage(orderItem.getProductImage());
        vo.setQuantity(orderItem.getQuantity());
        vo.setTotalPrice(orderItem.getTotalPrice());
        vo.setProductName(orderItem.getProductName());
        return vo;
    }

}
