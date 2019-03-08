package com.cheery.service;

import com.cheery.common.ServerResponse;
import com.cheery.pojo.Category;

/**
 * @desc: 分类业务逻辑层接口
 * @className: ICategoryService
 * @author: RONALDO
 * @date: 2019-02-27 14:58
 */
public interface ICategoryService {

    /**
     * desc:
     * 获取当前父类id下的所有产品,不递归查询
     * 如当前父分类ID为0 它有子类ID为1000 1001 1002 会全部查询出来 但是子类下面的子类不会查询出来了
     *
     * @param parentId 父类别id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-27 20:39
     */
    ServerResponse<?> findParallelAndChilderCategoryByParentId(int parentId);

}
