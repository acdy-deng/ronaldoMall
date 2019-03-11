package com.cheery.controller.portal;

import com.cheery.common.ApiResult;
import com.cheery.pojo.Product;
import com.cheery.service.ICommentService;
import com.cheery.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @desc: 产品模块前台控制器
 * @className: ProductController
 * @author: RONALDO
 * @date: 2019-02-28 21:52
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/product")
@Api("产品模块Api")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICommentService commentService;

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
            @ApiImplicitParam(name = "product", value = "产品实体", dataType = "Product")
    })
    @GetMapping("/list")
    public ApiResult<?> findProductByMultipleconditions(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            String keyWords, String brandname, String stylename, Product product
    ) {
        return productService.findProductByMultipleconditions(page, size, keyWords, brandname, stylename, product);
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

    /**
     * desc: 根据产品id查询对应的评论
     *
     * @param page 页码
     * @param size 每页条数
     * @param id   产品id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-03 21:16
     */
    @ApiOperation(value = "根据产品id查询对应的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "Integer"),
            @ApiImplicitParam(name = "id", value = "产品id", dataType = "Long")
    })
    @GetMapping("/p/comment")
    public ApiResult<?> findAllByProductId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            Long id
    ) {
        return commentService.findCommentByProductId(page, size, id);
    }

    /**
     * desc: 根据用户id查询对应的评论
     *
     * @param page 页码
     * @param size 每页条数
     * @param id   用户id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-06 14:49
     */
    @ApiOperation(value = "根据用户id查询对应的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "Integer"),
            @ApiImplicitParam(name = "id", value = "产品id", dataType = "Long")
    })
    @GetMapping("/u/comment")
    public ApiResult<?> findAllByUserId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            Long id
    ) {
        return commentService.findCommentByUserId(page, size, id);
    }

}
