package com.cheery.controller.portal;

import com.cheery.common.*;
import com.cheery.pojo.User;
import com.cheery.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @desc: 订单模块前端控制器
 * @className: OrderController
 * @author: RONALDO
 * @date: 2019-03-12 11:35
 */
@RestController
@RequestMapping("/order")
@Api("订单模块Api")
public class OrderController extends BaseController{

    /**
     * desc: 创建订单
     *
     * @param shippingId 购物车id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-12 11:59
     */
    @ApiOperation(value = "新增订单")
    @ApiImplicitParam(name = "shippingId", value = "购物车id", dataType = "Long")
    @GetMapping("/add")
    public ApiResult<?> add(Long shippingId) {
        return orderService.createOrder(user().getId(), shippingId);
    }

    /**
     * desc: 取消订单
     *
     * @param orderNo 订单号
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-13 18:21
     */
    @ApiOperation(value = "取消订单")
    @ApiImplicitParam(name = "orderNo", value = "订单号", dataType = "Long")
    @GetMapping("/cancel")
    public ApiResult<?> cancel(Long orderNo) {
        return orderService.cancelOrder(user().getId(), orderNo);
    }

    /**
     * desc: 获取购物车中商品信息
     *
     * @param orderNo 订单号
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-13 18:35
     */
    @ApiOperation(value = "获取购物车中商品信息")
    @ApiImplicitParam(name = "orderNo", value = "订单号", dataType = "Long")
    @GetMapping("/info")
    public ApiResult<?> getOrderProduct(Long orderNo) {
        return orderService.getOrderInfo(user().getId(), orderNo);
    }

    /**
     * desc: 订单详情
     *
     * @param orderNo 订单号
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-13 19:05
     */
    @ApiOperation(value = "订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单号", dataType = "Long")
    @GetMapping("/detail")
    public ApiResult<?> orderDetails(Long orderNo) {
        return orderService.getOrderDetails(user().getId(), orderNo);
    }

    /**
     * desc: 订单列表
     *
     * @param
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-13 19:05
     */
    @ApiOperation(value = "订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "Integer"),
            @ApiImplicitParam(name = "keyWords", value = "搜索关键字", dataType = "String"),
            @ApiImplicitParam(name = "product", value = "产品实体", dataType = "Product")
    })
    @GetMapping("/list")
    public ApiResult<?> orderList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size
    ) {
        return orderService.getOrderList(page, size, user().getId());
    }

}
