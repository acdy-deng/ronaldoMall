package com.cheery.pojo;

import com.cheery.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @desc: 产品品牌实体
 * @className: ProductBrand
 * @author: RONALDO
 * @date: 2019-03-01 16:13
 */
@Data
@Entity
@Table(name = "product_style")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ProductStyle extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

}
