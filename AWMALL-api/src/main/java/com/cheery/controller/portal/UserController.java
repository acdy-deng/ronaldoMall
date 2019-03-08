package com.cheery.controller.portal;

import com.alibaba.fastjson.JSON;
import com.cheery.pojo.User;
import com.cheery.service.IUserService;
import com.cheery.common.Constant;
import com.cheery.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.cheery.util.TipsUtil.noLogin;
import static com.cheery.util.TipsUtil.serverError;

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
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-23 22:34
     */
    @ApiOperation(value = "用户登出")
    @GetMapping("/logout")
    public ServerResponse<?> logout(HttpSession session) {
        try {
            session.removeAttribute(Constant.CURRENT_USER);
            if (null != session.getAttribute(Constant.CURRENT_USER)) {
                return ServerResponse.createBySuccessMsg("登出异常,请稍候再试");
            } else {
                return ServerResponse.createBySuccessMsg("登出成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 用户注册
     *
     * @param user 用户对象
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-23 22:39
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PostMapping("/register")
    public ServerResponse<?> register(User user) {
        try {
            return userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 获取用户信息
     *
     * @param session session会话
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-24 20:07
     */
    @ApiOperation(value = "获取用户信息")
    @PostMapping("/user")
    public ServerResponse<?> getUserInfo(HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        try {
            if (null == currentUser) {
                return noLogin();
            } else {
                return ServerResponse.createBySuccessData(JSON.toJSON(currentUser));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 根据手机号码查询密保问题
     *
     * @param phone 电话号码
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-24 20:39
     */
    @ApiOperation(value = "未登录情况下获取用户密保问题")
    @ApiImplicitParam(name = "phone", value = "登录名（手机号）", required = true, dataType = "String")
    @PostMapping("/question")
    public ServerResponse<?> getQuestion(String phone) {
        try {
            return userService.findQuestionByPhone(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 效验密保答案
     *
     * @param phone    手机号码
     * @param question 密保问题
     * @param answer   密保答案
     * @return ServerResponse<?>
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
    public ServerResponse<?> checkAnswer(String phone, String question, String answer) {
        try {
            return userService.checkAnswer(phone, question, answer);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 未登录情况下重置密码
     *
     * @param phone       手机号码
     * @param newPassword 新密码
     * @param token       效验密码通过后返回的token
     * @return ServerResponse<?>
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
    public ServerResponse<?> restPassword(String phone, String newPassword, String token) {
        try {
            return userService.restPassword(phone, newPassword, token);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 登录状态下重置密码
     *
     * @param session     session对象
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-25 11:46
     */
    @ApiOperation(value = "登录状态下修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String")
    })
    @PutMapping("/respwds")
    public ServerResponse<?> restPassword(HttpSession session, String oldPassword, String newPassword) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        ServerResponse<?> response = null;
        try {
            if (null == currentUser) {
                return noLogin();
            }
            response = userService.restPassword(currentUser, oldPassword, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
        return response;
    }

    /**
     * desc: 修改用户信息
     *
     * @param session session对象
     * @param user    用户对象
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-25 14:57
     */
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PutMapping("/update")
    public ServerResponse<?> updateUserInfo(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        try {
            if (null == currentUser) {
                return noLogin();
            }
            ServerResponse<?> response = userService.updateInfo(userService.getInfoById(currentUser.getId()), user);
            if (response.isSuccess()) {
                // 更新session
                session.setAttribute(Constant.CURRENT_USER, response.getData());
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

}