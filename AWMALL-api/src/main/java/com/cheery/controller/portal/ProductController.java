package com.cheery.controller.portal;

import com.cheery.common.ServerResponse;
import com.cheery.pojo.Product;
import com.cheery.service.ICommentService;
import com.cheery.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.cheery.util.TipsUtil.serverError;

/**
 * @desc: 产品模块前台控制器
 * @className: ProductController
 * @author: RONALDO
 * @date: 2019-02-28 21:52
 */
@RestController
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
     * @return ServerResponse<?>
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
    public ServerResponse<?> findProductByMultipleconditions(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            String keyWords, String p_brand, String p_style, Product product
    ) {
        try {
            return productService.findProductByMultipleconditions(page, size, keyWords, p_brand, p_style, product);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 根据产品id查询产品详情
     *
     * @param id 产品id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-03 16:48
     */
    @ApiOperation(value = "根据产品id查询产品详情")
    @ApiImplicitParam(name = "id", value = "产品id", dataType = "Long")
    @GetMapping("/details")
    public ServerResponse<?> findProductDetails(Long id) {
        try {
            return productService.findProductDetailsById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 根据产品id查询对应的评论
     *
     * @param page 页码
     * @param size 每页条数
     * @param id   产品id
     * @return ServerResponse<?>
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
    public ServerResponse<?> findAllByProductId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            Long id
    ) {
        try {
            return commentService.findCommentByProductId(page, size, id);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 根据用户id查询对应的评论
     *
     * @param page 页码
     * @param size 每页条数
     * @param id   用户id
     * @return ServerResponse<?>
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
    public ServerResponse<?> findAllByUserId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            Long id
    ) {
        try {
            return commentService.findCommentByUserId(page, size, id);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

}
