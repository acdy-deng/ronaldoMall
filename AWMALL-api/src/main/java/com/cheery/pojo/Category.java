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
import java.util.Collection;

/**
 * @desc: 产品分类实体
 * @className: Category
 * @author: RONALDO
 * @date: 2019-02-27 14:53
 */

@Getter
@Setter
@Entity
@Table(name = "category")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseEntity implements Serializable {

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JSONField(serialize = false)
    private Collection<Product> products;

}
