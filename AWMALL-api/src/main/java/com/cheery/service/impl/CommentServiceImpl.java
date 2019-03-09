package com.cheery.service.impl;

import com.alibaba.fastjson.JSON;
import com.cheery.common.ApiResult;
import com.cheery.pojo.ProductComment;
import com.cheery.repository.ProductCommentReplyRepository;
import com.cheery.repository.ProductCommentRepository;
import com.cheery.repository.UserRepository;
import com.cheery.service.ICommentService;
import com.cheery.vo.CommentReplyvo;
import com.cheery.vo.CommentVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;



/**
 * @desc: 评论业务逻辑层接口实现
 * @className: CommentServiceImpl
 * @author: RONALDO
 * @date: 2019-03-03 21:09
 */
@Service("commentService")
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductCommentRepository repository;

    @Autowired
    private ProductCommentReplyRepository productCommentReplyRepository;

    @Override
    public ApiResult<?> findCommentByProductId(Integer page, Integer size, Long productId) {
        Pageable pageable = new PageRequest(page, size, new Sort(new Sort.Order(Sort.Direction.DESC, "createtime")));
        Page<ProductComment> products = repository.findAll(new Specification<ProductComment>() {
            @Override
            public Predicate toPredicate(Root<ProductComment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("productId"), productId);
            }
        }, pageable);
        List<Object> list = Lists.newArrayList();
        for (ProductComment i : products.getContent()) {
            list.add(assembleProductCommentVo(i));
        }
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ApiResult.createBySuccessMsgAndData("查询成功", JSON.toJSON(pageResult));
    }

    @Override
    public ApiResult<?> findCommentByUserId(Integer page, Integer size, Long userId) {
        Pageable pageable = new PageRequest(page, size, new Sort(new Sort.Order(Sort.Direction.DESC, "createtime")));
        Page<ProductComment> products = repository.findAll(new Specification<ProductComment>() {
            @Override
            public Predicate toPredicate(Root<ProductComment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("user").get("id"), userId);
            }
        }, pageable);
        List<Object> list = Lists.newArrayList();
        for (ProductComment i : products.getContent()) {
            list.add(assembleProductCommentVo(i));
        }
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ApiResult.createBySuccessMsgAndData("查询成功", JSON.toJSON(pageResult));
    }

    private CommentVo assembleProductCommentVo(ProductComment pc) {
        CommentVo vo = new CommentVo();
        vo.setId(pc.getId());
        vo.setRate(pc.getRate());
        vo.setEffect(pc.getEffect());
        vo.setContent(pc.getContent());
        vo.setUser(pc.getUser());
        CommentReplyvo replyList = new CommentReplyvo();
        replyList.setCommentReplyList(productCommentReplyRepository.findAllByCommentId(pc.getId()));
        replyList.setUser(userRepository.findById(pc.getUser().getId()));
        vo.setReplyvo(replyList);
        return vo;
    }

}
