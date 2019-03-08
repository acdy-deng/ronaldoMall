package com.cheery.pojo;

import com.cheery.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @desc: 收货地址实体
 * @className: Shipping
 * @author: RONALDO
 * @date: 2019-03-06 10:48
 */
@Data
@Entity
@Table(name = "shipping")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Shipping extends BaseEntity implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "receiver_mobile")
    private String receiverMobile;

    @Column(name = "receiver_province")
    private String receiverProvince;

    @Column(name = "receiver_city")
    private String receiverCity;

    @Column(name = "receiver_district")
    private String receiverDistrict;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_zip")
    private String receiverZip;

}
