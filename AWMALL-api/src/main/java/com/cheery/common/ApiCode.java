package com.cheery.common;

import lombok.Getter;

/**
 * @desc: 响应编码枚举类
 * @className: ApiCode
 * @author: RONALDO
 * @date: 2019-02-23 15:23
 */
@Getter
public enum ApiCode {

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

    ApiCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
