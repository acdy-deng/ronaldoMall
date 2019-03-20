/**
 * @desc: 用户模块API请求
 * @author: RONALDO
 * @date: 2019-03-18 : 13:46
 */
const user = {
    // 用户登录
    login: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/login',
            data: data,
            method: 'POST',
            success: resolve,
            error: reject
        })
    },
    // 用户登出
    lagout: function (resolve, reject) {
        common.request({
            url: common.url() + '/usr/logout',
            data: null,
            method: 'POST',
            success: resolve,
            error: reject
        })
    },
    // 用户注册
    register: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/usr/register',
            data: data,
            method: 'POST',
            success: resolve,
            error: reject
        })
    },
    // 获取注册时的验证码
    getOtp: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/usr/register/otp',
            data: data,
            method: 'GET',
            success: resolve,
            error: reject
        })
    },
    // 获取用户信息
    getInfo: function (resolve, reject) {
        common.request({
            url: common.url() + '/usr/user',
            data: null,
            method: 'POST',
            success: resolve,
            error: reject
        })
    },
    // 登录状态下修改密码
    resPwd: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/usr/respwds',
            data: data,
            method: 'PUT',
            success: resolve,
            error: reject
        })
    },
    // 未登录状态下修改密码
    noLoginResPwd: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/usr/respwd',
            data: data,
            method: 'PUT',
            success: resolve,
            error: reject
        })
    },
    // 修改用户信息
    updateInfo: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/usr/update',
            data: data,
            method: 'PUT',
            success: resolve,
            error: reject
        })
    },
    // 查询用户全部评论
    getComment: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/u/comment/list',
            data: data,
            method: 'GET',
            success: resolve,
            error: reject
        })
    }
};