package com.cheery.pojo;

import com.cheery.common.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @desc: 购物车实体
 * @className: Cart
 * @author: RONALDO
 * @date: 2019-03-04 13:34
 */
@Getter
@Setter
@Entity
@Table(name = "cart")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Cart extends BaseEntity implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "checked")
    private Integer checked;

}
