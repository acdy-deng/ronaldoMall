package com.cheery.service;

import com.cheery.common.ApiResult;

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
     * desc: 取消订单
     *
     * @param userId  用户id
     * @param orderNo 订单号
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-13 18:23
     */
    ApiResult<?> cancelOrder(Long userId, Long orderNo);

    /**
     * desc: 获取购物车商品信息
     *
     * @param userId  用户id
     * @param orderNo 订单号
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-13 18:37
     */
    ApiResult<?> getOrderInfo(Long userId, Long orderNo);

    /**
     * desc: 订单详情
     *
     * @param userId  用户id
     * @param orderNo 订单号
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-13 19:07
     */
    ApiResult<?> getOrderDetails(Long userId, Long orderNo);

    /**
     * desc: 订单列表
     *
     * @param page   页码
     * @param size   每页条数
     * @param userId 用户id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-13 19:07
     */
    ApiResult<?> getOrderList(Integer page, Integer size, Long userId);

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
