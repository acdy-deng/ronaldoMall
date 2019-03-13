package com.cheery.pojo;

import com.cheery.common.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @desc: 订单详情实体
 * @className: OrderItem
 * @author: RONALDO
 * @date: 2019-03-11 20:32
 */
@Getter
@Setter
@Entity
@Table(name = "order_item")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class OrderItem extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_no")
    private long orderNo;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "current_unit_price")
    private BigDecimal currentUnitPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

}
