package com.cheery.vo;

import com.cheery.pojo.ProductCommentReply;
import com.cheery.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * @desc: 回复评论 value-object
 * @className: CommentReplyvo
 * @author: RONALDO
 * @date: 2019-03-05 14:22
 */
@Data
public class CommentReplyvo {

    private List<ProductCommentReply> commentReplyList;

    private User user;

}
