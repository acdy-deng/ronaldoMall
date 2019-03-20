package com.cheery.vo;

import lombok.Data;


/**
 * @desc: 收货地址 value-object
 * @className: ShippingVo
 * @author: RONALDO
 * @date: 2019-03-12 21:58
 */
@Data
public class ShippingVo {

    private String receiverName;

    private String receiverPhone;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

}
