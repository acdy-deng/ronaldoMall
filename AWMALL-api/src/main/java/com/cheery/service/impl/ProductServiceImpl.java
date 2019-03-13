package com.cheery.service.impl;

import com.cheery.common.ApiResult;
import com.cheery.common.PojoConvertVo;
import com.cheery.pojo.Product;
import com.cheery.repository.ProductRepository;
import com.cheery.service.IProductService;
import com.cheery.vo.ProductListVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.cheery.common.PojoConvertVo.*;

/**
 * @desc: 产品业务逻辑层接口实现
 * @className: ProductServiceImpl
 * @author: RONALDO
 * @date: 2019-02-28 21:46
 */
@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private PojoConvertVo pojoConvertVo;

    @Override
    public ApiResult<?> findProductByMultipleconditions(Integer page, Integer size, String keyWords, String brand, String style, Product product) {
        Pageable pageable = new PageRequest(page, size);
        Page<Product> products = repository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = Lists.newArrayList();
                if (null != keyWords && StringUtils.isEmpty(keyWords)) {
                    list.add(criteriaBuilder.like(root.get("name"), "%" + keyWords.trim() + "%"));
                }
                if (null != brand && !StringUtils.isEmpty(brand)) {
                    list.add(criteriaBuilder.equal(root.get("brand").get("name"), brand));
                }
                if (null != style && !StringUtils.isEmpty(style)) {
                    list.add(criteriaBuilder.equal(root.get("style").get("name"), style));
                }
                if (null != product.getIshot()) {
                    list.add(criteriaBuilder.equal(root.get("ishot"), product.getIshot()));
                }
                if (null != product.getIsboutique()) {
                    list.add(criteriaBuilder.equal(root.get("isboutique"), product.getIsboutique()));
                }
                if (null != product.getIsspike()) {
                    list.add(criteriaBuilder.equal(root.get("isspike"), product.getIsspike()));
                }
                Predicate[] predicates = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(predicates));
            }
        }, pageable);
        List<ProductListVo> list = Lists.newArrayList();
        for (Product i : products.getContent()) {
            ProductListVo productListVo = pojoConvertVo.assembleProductListVo(i);
            list.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ApiResult.createBySuccessMsgAndData("查询成功", pageResult);
    }

    @Override
    public ApiResult<?> findProductDetailsById(Long id) {
        Product product = repository.findOne(id);
        if (null == product) {
            return ApiResult.createByErrorMsg("该商品已下架或删除");
        }
        return ApiResult.createBySuccessMsgAndData("查询成功", pojoConvertVo.assembleProductDetailsVo(product));
    }

}


