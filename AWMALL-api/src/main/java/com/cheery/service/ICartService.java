package com.cheery.service;

import com.cheery.common.ServerResponse;

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
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-04 19:57
     */
    ServerResponse<?> cartInfo(Long userId);

    /**
     * desc: 添加商品到购物车
     *
     * @param userId    用户id
     * @param productId 商品id
     * @param count     商品总数
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-04 14:26
     */
    ServerResponse<?> add(Long userId, Long productId, Integer count);

    /**
     * desc: 更新购物车
     *
     * @param userId    用户id
     * @param productId 商品id
     * @param count     商品总数
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-04 18:28
     */
    ServerResponse<?> update(Long userId, Long productId, Integer count);

    /**
     * desc: 从购物车中删除
     *
     * @param userId    用户id
     * @param productId 商品id数组
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 18:38
     */
    ServerResponse<?> delete(Long userId, Long[] productId);

    /**
     * desc: 全选 / 全反选
     *
     * @param checked 选择状态
     * @param userId  用户id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-04 20:05
     */
    ServerResponse<?> selectOrUnselectAll(Integer checked, Long userId);

    /**
     * desc: 单选 / 单反选
     *
     * @param checked   选择状态
     * @param userId    用户id
     * @param productId 产品id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-04 20:42
     */
    ServerResponse<?> selectOrUnselectOne(Integer checked, Long userId, Integer productId);


    /**
     * desc:
     *
     * @param userId 用户id
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 21:33
     */
    ServerResponse<?> countProduct(Long userId);
}
