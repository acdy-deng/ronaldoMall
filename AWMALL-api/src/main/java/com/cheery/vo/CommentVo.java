package com.cheery.vo;

import com.cheery.pojo.User;
import lombok.Data;


/**
 * @desc: 评论 value-object
 * @className: CommentVo
 * @author: RONALDO
 * @date: 2019-03-05 11:26
 */
@Data
public class CommentVo {

    private Long id;

    private String content;

    private Integer rate;

    private Integer effect;

    private User user;

    private CommentReplyvo replyvo;

}
