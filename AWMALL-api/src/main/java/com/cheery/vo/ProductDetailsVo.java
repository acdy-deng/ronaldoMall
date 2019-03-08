package com.cheery.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @desc: 产品详情 value-object
 * @className: ProductDetailsVo
 * @author: RONALDO
 * @date: 2019-03-03 16:57
 */
@Data
public class ProductDetailsVo {

    private Long id;

    private String subtitle;

    private String brand;

    private String style;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer ishot;

    private Integer isboutique;

    private Integer isspike;

    private Integer stock;

    private Integer sale;

}
