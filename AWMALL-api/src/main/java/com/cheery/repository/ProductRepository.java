package com.cheery.repository;

import com.cheery.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @desc: 产品数据访问层接口
 * @className: ProductRepository
 * @author: RONALDO
 * @date: 2019-02-28 21:42
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}