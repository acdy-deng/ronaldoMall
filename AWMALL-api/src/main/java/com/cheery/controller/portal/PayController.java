package com.cheery.controller.portal;

import com.cheery.common.*;
import com.cheery.pojo.User;
import com.cheery.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @desc: 支付模块前端控制器
 * @className: PayController
 * @author: RONALDO
 * @date: 2019-03-10 20:22
 */
@RestController
@Api("支付模块Api")
public class PayController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private HttpSession session;

    private User user() {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == user) {
            throw new GlobalException(ApiStatus.NEED_LOGIN.getCode(), ApiStatus.NEED_LOGIN.getDesc());
        }
        return user;
    }

    /**
     * desc: 支付
     *
     * @param orderNo 订单号
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-10 20:25
     */
    @ApiOperation(value = "支付")
    @ApiImplicitParam(name = "orderNo", value = "订单号", dataType = "Long")
    @PostMapping("/pay")
    public ApiResult<?> pay(HttpServletRequest request, long orderNo) {
        return orderService.pay(orderNo, user().getId(), request.getSession().getServletContext().getRealPath("upload"));
    }

}
