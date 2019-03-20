package com.cheery.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.cheery.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * @desc: 用户实体
 * @className: User
 * @author: RONALDO
 * @date: 2019-02-23 16:26
 */
@Setter
@Getter
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity implements Serializable {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JSONField(serialize = false)
    private String password;

    @Column(name = "headpic")
    private String headPic;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    @JSONField(serialize = false)
    private Integer role;

    @Column(name = "status")
    @JSONField(serialize = false)
    private Integer status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JSONField(serialize = false)
    private Collection<ProductComment> comments;

}
