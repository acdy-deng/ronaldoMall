package com.cheery.service;

import com.cheery.common.ApiResult;

/**
 * @desc: 购物车业务逻辑层接口
 * @className: ICartService
 * @author: RONALDO
 * @date: 2019-03-04 14:23
 */
public interface ICartService {

    /**
     * desc: 获取购物车列表
     *
     * @param userId 用户id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 19:57
     */
    ApiResult<?> cartInfo(Long userId);

    /**
     * desc: 添加商品到购物车
     *
     * @param userId    用户id
     * @param productId 商品id
     * @param count     商品总数
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 14:26
     */
    ApiResult<?> add(Long userId, Long productId, Integer count);

    /**
     * desc: 更新购物车
     *
     * @param userId    用户id
     * @param productId 商品id
     * @param count     商品总数
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 18:28
     */
    ApiResult<?> update(Long userId, Long productId, Integer count);

    /**
     * desc: 从购物车中删除
     *
     * @param userId    用户id
     * @param productId 商品id数组
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 18:38
     */
    ApiResult<?> delete(Long userId, Long[] productId);

    /**
     * desc: 全选 / 全反选
     *
     * @param checked 选择状态
     * @param userId  用户id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 20:05
     */
    ApiResult<?> selectOrUnselectAll(Integer checked, Long userId);

    /**
     * desc: 单选 / 单反选
     *
     * @param checked   选择状态
     * @param userId    用户id
     * @param productId 产品id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 20:42
     */
    ApiResult<?> selectOrUnselectOne(Integer checked, Long userId, Integer productId);


    /**
     * desc:
     *
     * @param userId 用户id
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 21:33
     */
    ApiResult<?> countProduct(Long userId);
}
