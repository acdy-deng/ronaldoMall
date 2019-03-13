package com.cheery.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc: 购物车中的的商品信息 value-object
 * @className: OrderProductVo
 * @author: RONALDO
 * @date: 2019-03-13 18:38
 */
@Data
public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList;

    private BigDecimal productTotalPrice;

    private String imageHost;

}
