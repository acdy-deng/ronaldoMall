package com.cheery.controller.portal;

import com.cheery.common.*;
import com.cheery.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @desc: 订单模块前端控制器
 * @className: OrderController
 * @author: RONALDO
 * @date: 2019-03-12 11:35
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/order")
@Api("订单模块Api")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    /**
     * desc: 新增订单
     *
     * @param shippingId 购物车地址id
     * @return
     * @auther RONALDO
     * @date: 2019-03-12 11:59
     */
    @ApiOperation(value = "新增订单")
    @ApiImplicitParam(name = "orderNo", value = "订单号", dataType = "orderNo")
    @GetMapping("/add")
    public void add(HttpSession session, Integer shippingId) {
//        BaseController((User) session.getAttribute(Constant.CURRENT_USER), new ApiResult());
    }

}
