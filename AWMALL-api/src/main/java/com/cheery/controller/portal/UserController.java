package com.cheery.controller.portal;

import com.alibaba.fastjson.JSON;
import com.cheery.common.ApiCode;
import com.cheery.common.ApiResult;
import com.cheery.common.GlobalException;
import com.cheery.pojo.User;
import com.cheery.service.IUserService;
import com.cheery.common.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @desc: 用户模块前台控制器
 * @className: UserController
 * @author: RONALDO
 * @date: 2019-02-23 14:52
 */
@RestController
@RequestMapping("/usr")
@Api("用户模块Api")
public class UserController {

    @Autowired
    private IUserService userService;

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
        if (null != session.getAttribute(Constant.CURRENT_USER)) {
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
     * @param session session会话
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-24 20:07
     */
    @ApiOperation(value = "获取用户信息")
    @PostMapping("/user")
    public ApiResult<?> getUserInfo(HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        } else {
            return ApiResult.createBySuccessData(JSON.toJSON(currentUser));
        }
    }

    /**
     * desc: 根据手机号码查询密保问题
     *
     * @param phone 电话号码
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-24 20:39
     */
    @ApiOperation(value = "未登录情况下获取用户密保问题")
    @ApiImplicitParam(name = "phone", value = "登录名（手机号）", required = true, dataType = "String")
    @PostMapping("/question")
    public ApiResult<?> getQuestion(String phone) {
        if (null == phone) {
            throw new GlobalException(ApiCode.ILLEGAL_ARGUMENT.getCode(), ApiCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return userService.findQuestionByPhone(phone);
    }

    /**
     * desc: 效验密保答案
     *
     * @param phone    手机号码
     * @param question 密保问题
     * @param answer   密保答案
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-24 21:09
     */
    @ApiOperation(value = "效验密保答案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "登录名（手机号）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "question", value = "密保问题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "answer", value = "密保答案", required = true, dataType = "String")
    })
    @PostMapping("/checkanswer")
    public ApiResult<?> checkAnswer(String phone, String question, String answer) {
        return userService.checkAnswer(phone, question, answer);
    }

    /**
     * desc: 未登录情况下重置密码
     *
     * @param phone       手机号码
     * @param newPassword 新密码
     * @param token       效验密码通过后返回的token
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-25 08:32
     */
    @ApiOperation(value = "未登录状态下修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "登录名（手机号）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String")
    })
    @PutMapping("/respwd")
    public ApiResult<?> restPassword(String phone, String newPassword, String token) {
        return userService.restPassword(phone, newPassword, token);
    }

    /**
     * desc: 登录状态下重置密码
     *
     * @param session     session对象
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
    public ApiResult<?> restPassword(HttpSession session, String oldPassword, String newPassword) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        }
        return userService.restPassword(currentUser, oldPassword, newPassword);
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
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == currentUser) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        }
        ApiResult<?> response = userService.updateInfo(userService.getInfoById(currentUser.getId()), user);
        if (response.isSuccess()) {
            // 更新session
            session.setAttribute(Constant.CURRENT_USER, response.getData());
        }
        return response;
    }

}