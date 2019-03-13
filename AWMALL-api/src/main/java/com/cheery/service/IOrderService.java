package com.cheery.service;

import com.cheery.common.ApiResult;

import java.math.BigInteger;

/**
 * @desc: 订单业务逻辑层接口
 * @className: IOrderService
 * @author: RONALDO
 * @date: 2019-03-10 20:28
 */
public interface IOrderService {

    /**
     * desc: 创建订单
     *
     * @param userId     用户id
     * @param shippingId 购物车id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-12 14:02
     */
    ApiResult<?> createOrder(Long userId, Long shippingId);

    /**
     * desc: 支付
     *
     * @param orderNo 订单号
     * @param userId  用户id
     * @param path    基础路径
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-11 20:04
     */
    ApiResult<?> pay(long orderNo, Long userId, String path);

}
