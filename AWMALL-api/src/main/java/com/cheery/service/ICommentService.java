package com.cheery.service;

import com.cheery.common.ServerResponse;

/**
 * @desc: 评论业务逻辑层接口
 * @className: ICommentService
 * @author: RONALDO
 * @date: 2019-03-03 21:09
 */
public interface ICommentService {

    /**
     * desc: 根据产品id查询对应的评论
     *
     * @param page      页码
     * @param size      每页条数
     * @param productId 产品id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-03 21:11
     */
    ServerResponse<?> findCommentByProductId(Integer page, Integer size, Long productId);

    /**
     * desc: 根据用户id查询对应的评论
     *
     * @param page   页码
     * @param size   每页条数
     * @param userId 用户id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 14:43
     */
    ServerResponse<?> findCommentByUserId(Integer page, Integer size, Long userId);

}
