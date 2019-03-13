package com.cheery.pojo;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @desc: 订单实体
 * @className: Order
 * @author: RONALDO
 * @date: 2019-03-11 19:46
 */
@Data
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "shipping_id")
    private Long shippingId;

    @Column(name = "payment")
    private BigDecimal payment;

    @Column(name = "status")
    private Integer status;

    @Column(name = "payment_time")
    private Date paymentTime;

}
