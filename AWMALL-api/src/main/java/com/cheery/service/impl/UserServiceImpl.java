package com.cheery.service.impl;

import com.cheery.common.ApiResult;
import com.cheery.pojo.User;
import com.cheery.repository.UserRepository;
import com.cheery.service.IUserService;
import com.cheery.common.Constant;
import com.cheery.util.Md5Util;
import com.cheery.util.UpdateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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

    @Autowired
    private HttpServletRequest request;

    @Override
    public ApiResult<User> login(String email, String password) {
        // 将文本密码二次加密
        String md5Password = Md5Util.md5EncodeUtf8(password);
        User user = repository.findByEmailAndPassword(email, md5Password);
        return ApiResult.createBySuccessData(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> register(User user, String otp) {
        String otpCode = (String) request.getSession().getAttribute("otp");
        // 设置权限为0
        user.setRole(Constant.Role.ROLE_CUSTOMER);
        // 保证邮箱和手机是用户系统唯一的
        if (0 < repository.countByEmail(user.getEmail())) {
            return ApiResult.createByErrorMsg("该邮箱已被占用");
        }
        if (!otp.equals(otpCode)) {
            return ApiResult.createByErrorMsg("验证码错误");
        }
        // MD5加密用户密码
        user.setPassword(Md5Util.md5EncodeUtf8(user.getPassword()));
        user.setHeadPic("default.jpg");
        if (null == repository.save(user)) {
            return ApiResult.createByErrorMsg("注册异常请稍候再试");
        }
        return ApiResult.createBySuccessMsg("注册成功");
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> restPassword(String email, String newPassword, String otp) {
        User user = repository.findByEmail(email);
        String otpCode = (String) request.getSession().getAttribute(email);
        String nPsd = Md5Util.md5EncodeUtf8(newPassword);
        if (null == user) {
            return ApiResult.createByErrorMsg("该用户不存在");
        }
        if (StringUtils.isBlank(otpCode)) {
            return ApiResult.createByErrorMsg("验证码已过期");
        }
        if (otp.equals(otpCode) && !nPsd.equals(user.getPassword()) && 0 < repository.updatePasswordByEmail(nPsd, email)) {
            return ApiResult.createBySuccessMsg("密码修改成功");
        }
        return ApiResult.createByErrorMsg("验证码不存在或新旧密码相同");
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
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> updateInfo(User currentUser, User user) {
        // 保证邮箱是用户系统唯一的
        if (0 < repository.countByEmailIsOccupy(user.getEmail(), user.getId())) {
            return ApiResult.createByErrorMsg("该邮箱已被占用");
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
    public User isExistByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public ApiResult<?> checkUserRole(User user) {
        if (null != user && (user.getRole() == Constant.Role.ROLE_ADMIN)) {
            return ApiResult.createBySuccess();
        }
        return ApiResult.createByError();
    }


}
