package com.cheery.common;

import com.cheery.pojo.Product;
import com.cheery.vo.ProductDetailsVo;
import com.cheery.vo.ProductListVo;

/**
 * @desc: 用户pojo对象转vo对象
 * @className: PojoToVo
 * @author: RONALDO
 * @date: 2019-03-03 17:11
 */
public class PojoToVo {

    /**
     * desc: 用于产品pojo转vo -- 基本信息
     *
     * @param p 产品实体
     * @return ProductListVo
     * @auther RONALDO
     * @date: 2019-03-01 14:16
     */
    public static ProductListVo assembleProductListVo(Product p) {
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
    public static ProductDetailsVo assembleProductDetailsVo(Product p) {
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

}
