package com.cheery.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.cheery.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * @desc: 产品实体
 * @className: Product
 * @author: RONALDO
 * @date: 2019-02-28 21:18
 */
@Getter
@Setter
@Entity
@Table(name = "product")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity implements Serializable {

    @Column(name = "subtitle")
    private String subtitle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private ProductBrand brand;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "style_id", referencedColumnName = "id")
    private ProductStyle style;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "sub_images")
    private String subImages;

    @Column(name = "detail")
    private String detail;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "ishot")
    private Integer ishot;

    @Column(name = "isboutique")
    private Integer isboutique;

    @Column(name = "isspike")
    private Integer isspike;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "sale")
    private Integer sale;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

}
