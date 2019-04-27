package com.cheery.controller.portal;

import com.cheery.common.ApiResult;
import com.cheery.common.BaseController;
import com.cheery.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @desc: 分类模块前端控制器
 * @className: CategoryController
 * @author: RONALDO
 * @date: 2019-03-04 13:30
 */
@RestController
@RequestMapping("/category")
@Api("产品分类模块Api")
public class CategoryController extends BaseController {

    /**
     * desc:
     * 获取当前父类id下的所有产品,不递归查询
     * 如当前父分类ID为0 它有子类ID为1000 1001 1002 会全部查询出来 但是子类下面的子类不会查询出来了
     *
     * @param parentId 父类别id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-27 20:29
     */
    @ApiOperation(value = "获取当前父类id下的所有产品,不递归查询")
    @ApiImplicitParam(name = "parentId", value = "父类id", dataType = "String")
    @GetMapping("/list")
    public ApiResult<?> getParallelCategory(@RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        return categoryService.findParallelAndChilderCategoryByParentId(parentId);
    }

}
