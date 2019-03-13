package com.cheery.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc: 订单 value-object
 * @className: OrderVo
 * @author: RONALDO
 * @date: 2019-03-12 21:25
 */
@Data
public class OrderVo {

    private long orderNo;

    private BigDecimal payment;

    private Integer status;

    private String statusDesc;

    private String paymentTime;

    /**
     * 订单明细
     */
    private List<OrderItemVo> orderItemVoList;

    private String imageHost;

    private Long shipping;

    private String userName;

    /**
     * 收货地址
     */
    private ShippingVo shippingVo;

}
