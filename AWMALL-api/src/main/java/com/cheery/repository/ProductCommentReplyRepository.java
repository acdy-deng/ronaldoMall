package com.cheery.repository;

import com.cheery.pojo.ProductCommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @desc:
 * @className: ProductCommentReplyRepository
 * @author: RONALDO
 * @date: 2019-03-05 14:24
 */
@Repository
public interface ProductCommentReplyRepository extends JpaRepository<ProductCommentReply, Long> {

    /**
     * desc: 根据评论id查询回复的评论
     *
     * @param commentId 评论id
     * @return List<ProductCommentReply>
     * @auther RONALDO
     * @date: 2019-03-05 14:43
     */
    List<ProductCommentReply> findAllByCommentId(Long commentId);

}
