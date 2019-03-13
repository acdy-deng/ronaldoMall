package com.cheery.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @desc: 订单明细 value-object
 * @className: OrderItemVo
 * @author: RONALDO
 * @date: 2019-03-12 21:54
 */
@Data
public class OrderItemVo {

    private long orderNo;

    private Long productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private int quantity;

    private BigDecimal totalPrice;

    private Date createtime;

}
