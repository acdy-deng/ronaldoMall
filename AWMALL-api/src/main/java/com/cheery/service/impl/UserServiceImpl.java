package com.cheery.service.impl;

import com.cheery.common.ApiResult;
import com.cheery.pojo.User;
import com.cheery.repository.UserRepository;
import com.cheery.service.IUserService;
import com.cheery.common.Constant;
import com.cheery.util.FtpUtil;
import com.cheery.util.Md5Util;
import com.cheery.util.UpdateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
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

    @Autowired
    private HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        if (0 < repository.countByEmail(user.getEmail())) {
            return ApiResult.createByErrorMsg("该邮箱已存在");
        }
        if (0 < repository.countByUsername(user.getUsername())) {
            return ApiResult.createByErrorMsg("该用户名已存在");
        }
        if (!otp.equals(otpCode)) {
            return ApiResult.createByErrorMsg("验证码不正确");
        }
        user.setRole(Constant.Role.ROLE_CUSTOMER);
        user.setPassword(Md5Util.md5EncodeUtf8(user.getPassword()));
        user.setHeadPic("default.jpg");
        user.setStatus(0);
        if (null != repository.save(user)) {
            return ApiResult.createBySuccessMsg("注册成功");
        }
        return ApiResult.createByErrorMsg("注册异常请稍候再试");
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

    @Override
    public String uploadHeadPic(@RequestParam(value = "file", required = false) MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功了
            FtpUtil.uploadFile(targetFile);
            //已经上传到ftp服务器上
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ApiResult<?> updateUserState(String email, Integer state) {
        if (0 > repository.updateUserState(state, email)) {
            return ApiResult.createBySuccessMsg("激活失败");
        }
        return ApiResult.createBySuccessData("激活成功");
    }
}
