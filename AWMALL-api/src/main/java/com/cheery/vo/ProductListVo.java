package com.cheery.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @desc: 产品列表 value-object
 * @className: ProductListVo
 * @author: RONALDO
 * @date: 2019-03-01 14:04
 */
@Data
public class ProductListVo {

    private Long id;

    private String subtitle;

    private String brand;

    private String style;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer ishot;

    private Integer isboutique;

    private Integer isspike;

    private Integer sale;

}
