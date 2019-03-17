/**
 * @desc: 全局通用工具类
 * @author: RONALDO
 * @date: 2019-03-14 : 15:33
 */
const common = {
    /* 网络请求 200=成功 500=服务器内部错误 10001=异常 10002=非法参数 10003=未登录 */
    request: function (param) {
        $.ajax({
            method: 'POST',
            url: param.url || '',
            dataType: param.type || 'json',
            data: param.data || '',
            xhrFields: {withCredentials: true},
            success: function (data) {
                switch (data.status) {
                    case 200:
                        typeof param.success === 'function' && param.success(data);
                        break;
                    case 500:
                        console.log('内部服务器错误');
                        break;
                    case 10001:
                        typeof param.success === 'function' && param.success(data.status);
                        break;
                    case 10003:
                        this.doLogin();
                        break;
                }
            },
            error: function (err) {
                typeof param.error === 'function' && param.error(err.statusText);
            }
        });
    },
    /* 获取服获取url参数务器地址 */
    getUrlParam: function (name) {
        let reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
        let result = window.location.search.substr(1).match(reg);
        return result ? decodeURIComponent(result[2]) : null;
    },
    /* 跳转到登录页 */
    doLogin: function () {
        window.location.href = 'https://www.cronaldo7.cn/';
    },
    /* 回到首页 */
    goHome: function () {
        window.location.href = 'https://www.cronaldo7.cn/';
    }
};