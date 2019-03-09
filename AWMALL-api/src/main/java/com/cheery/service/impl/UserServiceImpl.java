package com.cheery.service.impl;

import com.cheery.common.ApiResult;
import com.cheery.common.TokenCache;
import com.cheery.pojo.User;
import com.cheery.repository.UserRepository;
import com.cheery.service.IUserService;
import com.cheery.common.Constant;
import com.cheery.util.Md5Util;
import com.cheery.util.UpdateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


/**
 * @desc: 用户业务逻辑层接口实现
 * @className: UserServiceImpl
 * @author: RONALDO
 * @date: 2019-02-23 16:37
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public ApiResult<User> login(String phone, String password) {
        // 将文本密码二次加密
        String md5Password = Md5Util.md5EncodeUtf8(password);
        User user = repository.findByPhoneAndPassword(phone, md5Password);
        if (null == user) {
            return ApiResult.createByErrorMsg("用户名或密码错误");
        }
        // 密码制空
        user.setPassword(StringUtils.EMPTY);
        user.setComments(null);
        return ApiResult.createBySuccessMsgAndData("登录成功", user);
    }

    @Override
    public ApiResult<?> register(User user) {
        // 设置权限为0
        user.setRole(Constant.Role.ROLE_CUSTOMER);
        // 保证邮箱和手机是用户系统唯一的
        if (0 < repository.countByEmail(user.getEmail())) {
            return ApiResult.createByErrorMsg("该邮箱已被占用");
        }
        if (0 < repository.countByPhone(user.getPhone())) {
            return ApiResult.createByErrorMsg("该手机号码已被占用");
        }
        // MD5加密用户密码
        user.setPassword(Md5Util.md5EncodeUtf8(user.getPassword()));
        if (null == repository.save(user)) {
            return ApiResult.createByErrorMsg("注册异常请稍候再试");
        }
        return ApiResult.createBySuccessMsg("注册成功");
    }

    @Override
    public ApiResult<?> findQuestionByPhone(String phone) {
        User user = repository.findByPhone(phone);
        if (null == user) {
            return ApiResult.createByErrorMsg("该用户不存在");
        }
        if (StringUtils.isNoneBlank(user.getQuestion())) {
            return ApiResult.createBySuccessData(user.getQuestion());
        }
        return ApiResult.createByErrorMsg("找回的密码问题是空的");
    }

    @Override
    public ApiResult<?> checkAnswer(String phone, String question, String answer) {
        if (0 < repository.countByPhoneAndQuestionAndAnswer(phone, question, answer)) {
            // 使用UUID生成一个token
            String token = UUID.randomUUID().toString();
            // 设置本地缓存
            TokenCache.setKey(Constant.TOKEN + phone, token);
            return ApiResult.createBySuccessData(token);
        }
        return ApiResult.createByErrorMsg("密保答案错误");
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> restPassword(String phone, String newPassword, String token) {
        User user = repository.findByPhone(phone);
        if (StringUtils.isBlank(token)) {
            return ApiResult.createByErrorMsg("参数错误,token需要传递");
        }
        if (null == user) {
            return ApiResult.createByErrorMsg("该用户不存在");
        }
        // 从本地缓存中获取token
        String localToken = TokenCache.getKey(Constant.TOKEN + phone);
        if (StringUtils.isBlank(localToken)) {
            return ApiResult.createByErrorMsg("token已过期");
        }
        String nPsd = Md5Util.md5EncodeUtf8(newPassword);
        if (StringUtils.equals(localToken, token) && !nPsd.equals(user.getPassword())) {
            if (0 < repository.updatePasswordByPhone(nPsd, phone)) {
                return ApiResult.createBySuccessMsg("密码修改成功");
            }
        }
        return ApiResult.createByErrorMsg("新旧token不同或新旧密码相同");
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> restPassword(User user, String oldPassword, String newPassword) {
        // 防止横向越权 所以要根据id和原密码查询该数据是否存在
        if (0 == repository.countByIdAndPassword(user.getId(), Md5Util.md5EncodeUtf8(oldPassword))) {
            return ApiResult.createByErrorMsg("原密码错误");
        }
        user.setPassword(Md5Util.md5EncodeUtf8(newPassword));
        if (0 < repository.updatePasswordByUserId(user.getPassword(), user.getId())) {
            return ApiResult.createBySuccessMsg("密码修改成功");
        }
        return ApiResult.createByErrorMsg("密码修改失败");
    }

    @Override
    public ApiResult<?> updateInfo(User currentUser, User user) {
        // 保证邮箱和手机是用户系统唯一的
        if (0 < repository.countByEmailIsOccupy(user.getEmail(), user.getId())) {
            return ApiResult.createByErrorMsg("该邮箱已被占用");
        }
        if (0 < repository.countByPhoneIsOccupy(user.getPhone(), user.getId())) {
            return ApiResult.createByErrorMsg("该手机号已被占用");
        }
        // 自动忽略为空的属性
        UpdateUtil.copyNullProperties(currentUser, user);
        if (null != repository.save(user)) {
            // 密码制空
            user.setPassword(StringUtils.EMPTY);
            return ApiResult.createBySuccessMsgAndData("更新个人信息成功", user);
        }
        return ApiResult.createByErrorMsg("更新个人信息失败");
    }

    @Override
    public User getInfoById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public ApiResult<?> checkUserRole(User user) {
        if (null != user && (user.getRole() == Constant.Role.ROLE_ADMIN)) {
            return ApiResult.createBySuccess();
        }
        return ApiResult.createByError();
    }
}
