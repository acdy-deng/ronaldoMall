package com.cheery.service;

import com.cheery.pojo.User;
import com.cheery.common.ServerResponse;

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
     * @param phone    手机号码
     * @param password 密码
     * @return ServerResponse<User>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-23 16:36
     */
    ServerResponse<User> login(String phone, String password);

    /**
     * desc: 用户注册
     *
     * @param user 用户对象
     * @return ServerResponse<User>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-23 23:15
     */
    ServerResponse<?> register(User user);

    /**
     * desc: 根据手机号码查询用户密保问题
     *
     * @param phone 手机号码
     * @return ServerResponse<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-24 20:25
     */
    ServerResponse<?> findQuestionByPhone(String phone);

    /**
     * desc: 效验密保答案
     *
     * @param phone    手机号码
     * @param question 密保问题
     * @param answer   密保答案
     * @return ServerResponse<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-24 21:26
     */
    ServerResponse<?> checkAnswer(String phone, String question, String answer);

    /**
     * desc: 未登录状态下的重置密码
     *
     * @param phone       手机号码
     * @param newPassword 新密码
     * @param token       效验密码通过后返回的token
     * @return ServerResponse<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-25 08:38
     */
    ServerResponse<?> restPassword(String phone, String newPassword, String token);

    /**
     * desc:
     *
     * @param user        用户对象
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return ServerResponse<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-25 11:55
     */
    ServerResponse<?> restPassword(User user, String oldPassword, String newPassword);

    /**
     * desc: 修改用户信息
     *
     * @param currentUser 当前session中的用户对象
     * @param user        用户对象
     * @return ServerResponse<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-25 14:59
     */
    ServerResponse<?> updateInfo(User currentUser, User user);

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
     * desc: 检验用户角色是否为管理员
     *
     * @param user 用户对象
     * @return ServerResponse<?>
     * @throws Exception
     * @auther RONALDO
     * @date: 2019-02-27 19:44
     */
    ServerResponse<?> checkUserRole(User user);

}
