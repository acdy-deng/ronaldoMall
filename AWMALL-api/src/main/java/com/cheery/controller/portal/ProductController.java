package com.cheery.controller.portal;

import com.cheery.common.ApiResult;
import com.cheery.common.BaseController;
import com.cheery.common.Constant;
import com.cheery.pojo.Product;
import com.cheery.pojo.User;
import com.cheery.service.ICommentService;
import com.cheery.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @desc: 产品模块前端控制器
 * @className: ProductController
 * @author: RONALDO
 * @date: 2019-02-28 21:52
 */
@RestController
@RequestMapping("/product")
@Api("产品模块Api")
public class ProductController extends BaseController {

    /**
     * desc: 根据不同的条件组合查询
     *
     * @param page     页码
     * @param size     每页条数
     * @param keyWords 搜索关键字
     * @param product  产品实体( 用于传多条件参数 )
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-28 23:02
     */
    @ApiOperation(value = "根据不同的条件组合查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "Integer"),
            @ApiImplicitParam(name = "keyWords", value = "搜索关键字", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "产品ID", dataType = "Integer"),
            @ApiImplicitParam(name = "brandName", value = "品牌名称", dataType = "String"),
            @ApiImplicitParam(name = "styleName", value = "风格名称", dataType = "String"),
            @ApiImplicitParam(name = "ishot", value = "是否热销", dataType = "Integer"),
            @ApiImplicitParam(name = "isboutique", value = "是否精品", dataType = "Integer"),
            @ApiImplicitParam(name = "isspike", value = "是否秒杀", dataType = "Integer"),
    })
    @GetMapping("/list")
    public ApiResult<?> findProductByMultipleconditions(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            String keyWords, String categoryId, String brandName, String styleName, Product product
    ) {
        return productService.findProductByMultipleconditions(page, size, keyWords, categoryId, brandName, styleName, product);
    }

    /**
     * desc: 根据产品id查询产品详情
     *
     * @param id 产品id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-03 16:48
     */
    @ApiOperation(value = "根据产品id查询产品详情")
    @ApiImplicitParam(name = "id", value = "产品id", dataType = "Long")
    @GetMapping("/details")
    public ApiResult<?> findProductDetails(Long id) {
        return productService.findProductDetailsById(id);
    }

}
