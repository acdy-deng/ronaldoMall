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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

/**
 * @desc: 支付模块前端控制器
 * @className: PayController
 * @author: RONALDO
 * @date: 2019-03-10 20:22
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Api("支付模块Api")
public class PayController extends BaseController {

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
    public ApiResult<?> pay(HttpServletRequest request, HttpSession session, long orderNo) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        return BaseController(currentUser, orderService.pay(orderNo, currentUser.getId(),
                request.getSession().getServletContext().getRealPath("upload")));
    }

}
