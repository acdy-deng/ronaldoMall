package com.cheery.service.impl;

import com.cheery.common.ServerResponse;
import com.cheery.pojo.Category;
import com.cheery.repository.CategoryRepository;
import com.cheery.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;import static com.cheery.util.TipsUtil.parameterError;


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
    public ServerResponse<?> findParallelAndChilderCategoryByParentId(int parentId) {
        parameterError(parentId);
        List<Category> categoryList = repository.findAllByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)) {
            return ServerResponse.createByErrorMsg("未找到当前分类的子分类");
        }
        for (Category c : categoryList) {
            c.setProducts(null);
        }
        return ServerResponse.createBySuccessMsgAndData("查询成功", categoryList);
    }

}
