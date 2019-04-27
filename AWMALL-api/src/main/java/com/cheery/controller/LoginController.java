package com.cheery.controller;

import com.alibaba.fastjson.JSON;
import com.cheery.common.BaseController;
import com.cheery.common.Constant;
import com.cheery.common.ApiResult;
import com.cheery.pojo.User;
import com.cheery.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @desc: 登录控制器
 * @className: LoginController
 * @author: RONALDO
 * @date: 2019-02-27 14:23
 */
@RestController
@Api("登录模块Api")
public class LoginController extends BaseController {

    /**
     * desc: 用户登录
     *
     * @param email    邮箱
     * @param password 密码
     * @param session  session会话
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-23 22:03
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/login")
    public ApiResult<?> userlogin(HttpSession session, String email, String password) {
        ApiResult<User> response = userService.login(email, password);
        User user = response.getData();
        if (null == user) {
            return ApiResult.createByErrorMsg("用户名或密码错误");
        }
        if (response.isSuccess()) {
            session.setAttribute(Constant.CURRENT_USER, user);
        }
        return ApiResult.createBySuccessMsgAndData("登录成功", JSON.toJSON(user));
    }

}