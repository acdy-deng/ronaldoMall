package com.cheery.repository;

import com.cheery.pojo.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @desc: 产品评论数据访问层接口
 * @className: ProductCommentRepository
 * @author: RONALDO
 * @date: 2019-03-03 17:50
 */
@Repository
public interface ProductCommentRepository extends JpaRepository<ProductComment, Long>, JpaSpecificationExecutor<ProductComment> {
}
