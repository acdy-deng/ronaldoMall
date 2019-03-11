package com.cheery.controller.portal;

import com.cheery.common.ApiCode;
import com.cheery.common.ApiResult;
import com.cheery.common.Constant;
import com.cheery.common.GlobalException;
import com.cheery.pojo.User;
import com.cheery.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

/**
 * @desc: 支付模块前台控制器
 * @className: PayController
 * @author: RONALDO
 * @date: 2019-03-10 20:22
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/order")
@Api("支付模块Api")
public class PayController {

    @Autowired
    private IOrderService orderService;

    /**
     * desc: 支付
     *
     * @param orderNo 订单号
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-10 20:25
     */
    @ApiOperation(value = "支付")
    @ApiImplicitParam(name = "orderNo", value = "订单号", dataType = "orderNo")
    @GetMapping("/pay")
    public ApiResult<?> pay(HttpServletRequest request, HttpSession session, BigInteger orderNo) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return orderService.pay(orderNo, currentUser.getId(), request.getSession().getServletContext().getRealPath("upload"));
        }
    }


}
