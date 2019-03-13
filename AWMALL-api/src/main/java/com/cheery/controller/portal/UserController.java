package com.cheery.controller.portal;

import com.alibaba.fastjson.JSON;
import com.cheery.common.*;
import com.cheery.pojo.User;
import com.cheery.service.IMailService;
import com.cheery.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.cheery.util.OtpUtil.otp;

/**
 * @desc: 用户模块前端控制器
 * @className: UserController
 * @author: RONALDO
 * @date: 2019-02-23 14:52
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/usr")
@Api("用户模块Api")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMailService mailService;

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
     * desc: 用户登出
     *
     * @param session session会话
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-23 22:34
     */
    @ApiOperation(value = "用户登出")
    @GetMapping("/logout")
    public ApiResult<?> logout(HttpSession session) {
        session.removeAttribute(Constant.CURRENT_USER);
        if (null != user()) {
            return ApiResult.createBySuccessMsg("登出异常,请稍候再试");
        } else {
            return ApiResult.createBySuccessMsg("登出成功");
        }
    }

    /**
     * desc: 用户注册
     *
     * @param user 用户对象
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-23 22:39
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PostMapping("/register")
    public ApiResult<?> register(User user) {
        return userService.register(user);
    }

    /**
     * desc: 获取用户信息
     *
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-24 20:07
     */
    @ApiOperation(value = "获取用户信息")
    @PostMapping("/user")
    public ApiResult<?> getUserInfo() {
        return ApiResult.createBySuccessData(JSON.toJSON(user()));
    }

    /**
     * desc: 根据邮箱获取otp验证码
     *
     * @param email 邮箱
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-09 22:03
     */
    @PostMapping("/getotp")
    public ApiResult<?> getOtpByEmail(HttpSession session, String email) {
        if (null == userService.isExistByEmail(email)) {
            return ApiResult.createByErrorMsg("用户不存在");
        } else {
            String otpCode = otp();
            session.setMaxInactiveInterval(3 * 60);
            session.setAttribute(email, otpCode);
            mailService.sendSimpleMail(email, "AWMALL商城官网", "尊敬的用户您的验证码为{ " + otpCode + " },请于3分钟内正确输入，如非本人操作，请忽略此邮件。");
        }
        return ApiResult.createBySuccessData("发送成功");
    }

    /**
     * desc: 未登录情况下重置密码
     *
     * @param email       邮箱
     * @param newPassword 新密码
     * @param otp         otp码
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-25 08:32
     */
    @ApiOperation(value = "未登录状态下修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String")
    })
    @PutMapping("/respwd")
    public ApiResult<?> restPassword(String email, String newPassword, String otp) {
        return userService.restPassword(email, newPassword, otp);
    }

    /**
     * desc: 登录状态下重置密码
     *
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-25 11:46
     */
    @ApiOperation(value = "登录状态下修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String")
    })
    @PutMapping("/respwds")
    public ApiResult<?> restPassword(String oldPassword, String newPassword) {
        return userService.restPassword(user(), oldPassword, newPassword);
    }

    /**
     * desc: 修改用户信息
     *
     * @param session session对象
     * @param user    用户对象
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-25 14:57
     */
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PutMapping("/update")
    public ApiResult<?> updateUserInfo(HttpSession session, User user) {
        ApiResult<?> response = userService.updateInfo(userService.getInfoById(user().getId()), user);
        if (response.isSuccess()) {
            session.setAttribute(Constant.CURRENT_USER, response.getData());
        }
        return response;
    }

}