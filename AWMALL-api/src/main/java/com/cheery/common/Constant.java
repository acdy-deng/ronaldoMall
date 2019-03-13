package com.cheery.common;

/**
 * @desc: 全局常量
 * @className: Constant
 * @author: RONALDO
 * @date: 2019-02-23 16:12
 */
public class Constant {

    /**
     * 当前用户
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * 用户权限
     */
    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    /**
     * 缓存的token
     */
    public static final String TOKEN = "token_";

    /**
     * 字符编码
     */
    public final static String ENCODING = "UTF-8";

    /**
     * 购物车
     */
    public interface Cart {
        int CHECKED = 1;
        int UN_CHECKED = 0;
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }
}
