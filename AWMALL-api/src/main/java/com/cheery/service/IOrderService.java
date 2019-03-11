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
     * desc: 支付
     *
     * @param orderNo 订单号
     * @param userId  用户id
     * @param path    路径
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-11 20:04
     */
    ApiResult<?> pay(BigInteger orderNo, Long userId, String path);

}
