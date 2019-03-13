package com.cheery.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @desc: 支付状态枚举
 * @className: ApiStatus
 * @author: RONALDO
 * @date: 2019-02-23 15:23
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {

    /**
     * 101
     */
    NO_PAY(101, "未支付"),

    /**
     * 201
     */
    PAID(201, "已付款"),

    /**
     * 301
     */
    SHIPPED(301, "已发货"),

    /**
     * 401
     */
    ORDER_SUCCESS(401, "订单完成"),

    /**
     * 501
     */
    ORDER_CLOSE(501, "订单关闭");

    private int code;
    private String value;

    public static OrderStatus codeOf(int code) {
        for (OrderStatus orderStatusEnum : values()) {
            if (orderStatusEnum.getCode() == code) {
                return orderStatusEnum;
            }
        }
        throw new RuntimeException("么有找到对应的枚举");
    }

}
