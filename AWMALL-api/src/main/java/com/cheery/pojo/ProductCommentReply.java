package com.cheery.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.cheery.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @desc: 产品评论回复实体
 * @className: ProductCommentReply
 * @author: RONALDO
 * @date: 2019-03-05 11:06
 */
@Data
@Entity
@Table(name = "product_comment_reply")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ProductCommentReply extends BaseEntity implements Serializable {

    @Column(name = "comment_id")
    @JSONField(serialize = false)
    private Long commentId;

    @Column(name = "to_uid")
    @JSONField(serialize = false)
    private Long toUid;

    @Column(name = "content")
    private String content;

    @Column(name = "from_uid")
    @JSONField(serialize = false)
    private Long fromUid;

}
