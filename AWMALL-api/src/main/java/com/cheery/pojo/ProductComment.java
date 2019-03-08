package com.cheery.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.cheery.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @desc: 产品评论实体
 * @className: ProductComment
 * @author: RONALDO
 * @date: 2019-03-03 17:31
 */
@Data
@Entity
@Table(name = "product_comment")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ProductComment extends BaseEntity implements Serializable {

    @JSONField(serialize = false)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "content")
    private String content;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "effect")
    private Integer effect;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
