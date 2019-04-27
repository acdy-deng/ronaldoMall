package com.cheery.controller.portal;

import com.cheery.common.*;
import com.cheery.pojo.User;
import com.cheery.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @desc: 购物车模块前端控制器
 * @className: CartController
 * @author: RONALDO
 * @date: 2019-03-04 14:15
 */
@RestController
@RequestMapping("/cart")
@Api("购物车模块Api")
public class CartController extends BaseController{

    /**
     * desc: 获取当前用户的购物车列表
     *
     * @param
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 19:50
     */
    @ApiOperation(value = "获取当前用户的购物车列表")
    @GetMapping("/list")
    public ApiResult<?> list() {
        return cartService.cartInfo(user().getId());
    }

    /**
     * desc: 获取购物车商品总数
     *
     * @param
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 21:14
     */
    @ApiOperation(value = "获取购物车商品总数")
    @GetMapping("/count")
    public ApiResult<?> count() {
        return cartService.countProduct(user().getId());
    }

    /**
     * desc: 添加商品到购物车
     *
     * @param productId 产品id
     * @param count     总数
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 16:02
     */
    @ApiOperation(value = "添加商品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", dataType = "Integer"),
            @ApiImplicitParam(name = "count", value = "总数", dataType = "Integer")
    })
    @GetMapping("/add")
    public ApiResult<?> add(Long productId, Integer count) {
        return cartService.add(user().getId(), productId, count);
    }

    /**
     * desc: 更新购物车
     *
     * @param
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 18:33
     */
    @ApiOperation(value = "更新购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", dataType = "Integer"),
            @ApiImplicitParam(name = "count", value = "总数", dataType = "Integer")
    })
    @PutMapping("/update")
    public ApiResult<?> update(Long productId, Integer count) {
        return cartService.update(user().getId(), productId, count);
    }

    /**
     * desc: 从购物车删除商品
     *
     * @param productId 产品id数组
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 18:33
     */
    @ApiOperation(value = "从购物车删除商品")
    @ApiImplicitParam(name = "productIds", value = "产品ids", dataType = "Long")
    @DeleteMapping("/delete")
    public ApiResult<?> delete(Long[] productId) {
        return cartService.delete(user().getId(), productId);
    }

    /**
     * desc: 全选
     *
     * @param
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 20:17
     */
    @ApiOperation(value = "购物车全选")
    @GetMapping("/select_all")
    public ApiResult<?> selectAll() {
        return cartService.selectOrUnselectAll(Constant.Cart.CHECKED, user().getId());
    }

    /**
     * desc: 全反选
     *
     * @param
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 20:17
     */
    @ApiOperation(value = "购物车全反选")
    @GetMapping("/un_select_all")
    public ApiResult<?> unSelectAll() {
        return cartService.selectOrUnselectAll(Constant.Cart.UN_CHECKED, user().getId());
    }

    /**
     * desc: 单选
     *
     * @param productId 产品id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 20:21
     */
    @ApiOperation(value = "购物车单选")
    @ApiImplicitParam(name = "productId", value = "产品id", dataType = "Integer")
    @GetMapping("/select_one")
    public ApiResult<?> selectOne(Integer productId) {
        return cartService.selectOrUnselectOne(Constant.Cart.CHECKED, user().getId(), productId);
    }

    /**
     * desc: 单反选
     *
     * @param productId 产品id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-04 20:21
     */
    @ApiOperation(value = "购物车单反选")
    @ApiImplicitParam(name = "productId", value = "产品id", dataType = "Integer")
    @GetMapping("/un_select_one")
    public ApiResult<?> unSelectOne(Integer productId) {
        return cartService.selectOrUnselectOne(Constant.Cart.UN_CHECKED, user().getId(), productId);
    }

}
