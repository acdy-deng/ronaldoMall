package com.cheery.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @desc: 购物车产品 value-object
 * @className: CartProductVo
 * @author: RONALDO
 * @date: 2019-03-04 14:47
 */
@Data
public class CartProductVo {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private BigDecimal discountPrice;

    private BigDecimal productTotalPrice;

    private Integer productStock;

    private Integer productChecked;

    // 限制数量的合理性

    private String limitQuantity;

}
