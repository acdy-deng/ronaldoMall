package com.cheery.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @desc: api响应编码枚举
 * @className: ApiStatus
 * @author: RONALDO
 * @date: 2019-02-23 15:23
 */
@Getter
@AllArgsConstructor
public enum ApiStatus {

    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),

    /**
     * 内部服务器错误
     */
    ERROR_SERVER(500, "ERROR_SERVER( 服务器内部错误 )"),

    /**
     * 异常
     */
    ERROR(10001, "ERROR"),

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT(10002, "ILLEGAL_ARGUMENT( 非法参数 / 参数缺失 / 参数溢出 )"),

    /**
     * 需要登录
     */
    NEED_LOGIN(10003, "NEED_LOGIN( 未登录 )");

    private final Integer code;
    private final String desc;

}
