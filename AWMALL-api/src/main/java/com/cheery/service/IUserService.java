package com.cheery.service;

import com.cheery.common.ApiResult;
import com.cheery.pojo.User;

/**
 * @desc: 用户业务逻辑层接口
 * @className: IUserService
 * @author: RONALDO
 * @date: 2019-02-23 16:35
 */
public interface IUserService {

    /**
     * desc: 用户登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return ApiResult<User>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-23 16:36
     */
    ApiResult<User> login(String email, String password);

    /**
     * desc: 用户注册
     *
     * @param user 用户对象
     * @param otp  验证码
     * @return ApiResult<User>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-23 23:15
     */
    ApiResult<?> register(User user, String otp);

    /**
     * desc: 未登录状态下的重置密码
     *
     * @param email       邮箱
     * @param newPassword 新密码
     * @param otp         otp
     * @return ApiResult<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-25 08:38
     */
    ApiResult<?> restPassword(String email, String newPassword, String otp);

    /**
     * desc:
     *
     * @param user        用户对象
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return ApiResult<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-25 11:55
     */
    ApiResult<?> restPassword(User user, String oldPassword, String newPassword);

    /**
     * desc: 修改用户信息
     *
     * @param currentUser 当前session中的用户对象
     * @param user        用户对象
     * @return ApiResult<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-25 14:59
     */
    ApiResult<?> updateInfo(User currentUser, User user);

    /**
     * desc: 根据用户id查询信息
     *
     * @param id 用户id
     * @return User对象
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-26 20:06
     */
    User getInfoById(Long id);


    /**
     * desc: 根据邮箱查看用户是否存在
     *
     * @param email 邮箱
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-09 22:04
     */
    User isExistByEmail(String email);

    /**
     * desc: 检验用户角色是否为管理员
     *
     * @param user 用户对象
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-27 19:44
     */
    ApiResult<?> checkUserRole(User user);

}
