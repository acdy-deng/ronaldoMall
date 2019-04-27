package com.cheery.service.impl;

import com.alibaba.fastjson.JSON;
import com.cheery.common.ApiResult;
import com.cheery.common.PojoConvertVo;
import com.cheery.pojo.Category;
import com.cheery.repository.CategoryRepository;
import com.cheery.service.ICategoryService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @desc: 分类业务逻辑层接口实现
 * @className: CategoryServiceImpl
 * @author: RONALDO
 * @date: 2019-02-27 14:59
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public ApiResult<?> findParallelAndChilderCategoryByParentId(int parentId) {
        List<Category> categoryList = repository.findAllByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)) {
            return ApiResult.createByErrorMsg("未找到当前分类的子分类");
        }
        return ApiResult.createBySuccessMsgAndData("查询成功", JSON.toJSON(categoryList));
    }

}
