package com.cheery.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc: 购物车 value-object
 * @className: CartVo
 * @author: RONALDO
 * @date: 2019-03-04 14:59
 */
@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private BigDecimal cartTotalPrice;

    private Boolean allChecked;

}
