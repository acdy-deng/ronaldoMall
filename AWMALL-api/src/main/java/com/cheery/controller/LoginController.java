package com.cheery.controller;

import com.alibaba.fastjson.JSON;
import com.cheery.common.Constant;
import com.cheery.common.ResponseCode;
import com.cheery.common.ServerResponse;
import com.cheery.pojo.User;
import com.cheery.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.cheery.util.TipsUtil.serverError;

/**
 * @desc: 登录控制器
 * @className: LoginController
 * @author: RONALDO
 * @date: 2019-02-27 14:23
 */
@RestController
@Api("登录模块Api")
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * desc: 用户登录
     *
     * @param phone    手机号码
     * @param password 密码
     * @param session  session会话
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-23 22:03
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "登录名（手机号）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/login")
    public ServerResponse<?> userlogin(HttpSession session, String phone, String password) {
        try {
            ServerResponse<User> response = userService.login(phone, password);
            User user = response.getData();
            if (response.isSuccess()) {
                session.setAttribute(Constant.CURRENT_USER, user);
            }
            return ServerResponse.createBySuccessData(JSON.toJSON(user));
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }
}