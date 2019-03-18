package com.cheery.service.impl;

import com.cheery.common.ApiStatus;
import com.cheery.common.ApiResult;
import com.cheery.pojo.Shipping;
import com.cheery.repository.ShippingRepository;
import com.cheery.service.IShippingService;
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
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

/**
 * @desc: 收货地址业务逻辑层接口实现
 * @className: ShippingServiceImpl
 * @author: RONALDO
 * @date: 2019-03-06 10:56
 */
@Service("shippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingRepository repository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> addAddress(Shipping shipping) {
        shipping.setId(System.currentTimeMillis() + new Random().nextInt(99));
        Shipping s = repository.save(shipping);
        if (null == s) {
            return ApiResult.createByErrorMsg("新增地址失败");
        }
        return ApiResult.createBySuccessMsgAndData("新增地址成功", s);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> updateAddress(Shipping shipping) {
        Shipping s = repository.save(shipping);
        if (null == s) {
            return ApiResult.createByErrorMsg("更新地址失败");
        }
        return ApiResult.createBySuccessMsgAndData("更新地址成功", s);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> deleteAddress(Long id) {
        if (null == id) {
            return ApiResult.createByErrorCodeMsg(ApiStatus.ILLEGAL_ARGUMENT.getCode(), ApiStatus.ILLEGAL_ARGUMENT.getDesc());
        }
        repository.delete(id);
        return ApiResult.createBySuccessMsg("删除地址成功");
    }

    @Override
    public ApiResult<?> findAllAddress(Integer page, Integer size, Long userId) {
        Pageable pageable = new PageRequest(page, size, new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        Page<Shipping> shippings = repository.findAll(new Specification<Shipping>() {
            @Override
            public Predicate toPredicate(Root<Shipping> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("userId"), userId);
            }
        }, pageable);
        List<Object> list = Lists.newArrayList();
        list.add(shippings.getContent());
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ApiResult.createBySuccessMsgAndData("查询成功", pageResult);
    }

}
