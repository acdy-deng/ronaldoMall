package com.cheery.util;

import com.cheery.common.ResponseCode;
import com.cheery.common.ServerResponse;

/**
 * @desc: 提示工具类
 * @className: TipsUtil
 * @author: RONALDO
 * @date: 2019-03-06 15:04
 */
public class TipsUtil {

    /**
     * desc: 参数错误
     */
    public static ServerResponse<?> parameterError(Object... value) {
        for (Object i : value) {
            if (null != i) {
                return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
            }
        }
        return null;
    }

    public static ServerResponse<?> parameterError_(Object[] value) {
        for (Object i : value) {
            if (null != i) {
                return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
            }
        }
        return null;
    }

    /**
     * desc: 未登录
     */
    public static ServerResponse<?> noLogin() {
        return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }

    /**
     * desc: 服务器异常
     */
    public static ServerResponse<?> serverError() {
        return ServerResponse.createByErrorCodeMsg(ResponseCode.ERROR_SERVER.getCode(), ResponseCode.ERROR_SERVER.getDesc());
    }

}
