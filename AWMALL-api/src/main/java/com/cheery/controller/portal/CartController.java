package com.cheery.controller.portal;

import com.cheery.common.ApiCode;
import com.cheery.common.ApiResult;
import com.cheery.common.Constant;
import com.cheery.common.GlobalException;
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
 * @desc: 购物车模块前台控制器
 * @className: CartController
 * @author: RONALDO
 * @date: 2019-03-04 14:15
 */
@RestController
@RequestMapping("/cart")
@Api("购物车模块Api")
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * desc: 获取当前用户的购物车列表
     *
     * @param
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 19:50
     */
    @ApiOperation(value = "获取当前用户的购物车列表")
    @PostMapping("/list")
    public ApiResult<?> list(HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.cartInfo(currentUser.getId());
        }
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
    @PostMapping("/count")
    public ApiResult<?> count(HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.countProduct(currentUser.getId());
        }
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
    public ApiResult<?> add(HttpSession session, Long productId, Integer count) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.add(currentUser.getId(), productId, count);
        }
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
    public ApiResult<?> update(HttpSession session, Long productId, Integer count) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.update(currentUser.getId(), productId, count);
        }
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
    public ApiResult<?> delete(HttpSession session, Long[] productId) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.delete(currentUser.getId(), productId);
        }
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
    public ApiResult<?> selectAll(HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.selectOrUnselectAll(Constant.Cart.CHECKED, currentUser.getId());
        }
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
    public ApiResult<?> unSelectAll(HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.selectOrUnselectAll(Constant.Cart.UN_CHECKED, currentUser.getId());
        }
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
    public ApiResult<?> selectOne(HttpSession session, Integer productId) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.selectOrUnselectOne(Constant.Cart.CHECKED, currentUser.getId(), productId);
        }
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
    public ApiResult<?> unSelectOne(HttpSession session, Integer productId) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return cartService.selectOrUnselectOne(Constant.Cart.UN_CHECKED, currentUser.getId(), productId);
        }
    }

}
