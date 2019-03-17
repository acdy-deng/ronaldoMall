/**
 * @desc: 用户登录
 * @author: RONALDO
 * @date: 2019-03-15 : 15:00
 */
$("#login").on("click", function () {
    if (0 < $('#email').val().length || 0 < $('#password').val().length) {
        common.request({
            url: 'http://127.0.0.1:666/login',
            data: $('form').serializeArray(),
            success: function (res) {
                if (200 === res.status) {
                    // 将用户基本数据保存在cookie中有效期为1天
                    $.cookie('userInfo', res.data, {expires: 1});
                    $('#tip').prepend('<div class="alert alert-success"><span>登录成功 3s后即将跳转...</span></div>');
                    setTimeout(function () {
                        window.location.href = 'index.html';
                    }, 2500);
                }
                if (10001 === res) {
                    $(".alert-danger").remove();
                    $('#tip').prepend('<div class="alert alert-danger"><span>用户名或密码错误</span></div>');
                    $('.alert-danger').fadeToggle(2800);
                }
            },
            error: function (err) {
                $('#tip').prepend('<div class="alert alert-danger"><span>服务器未知异常 请稍候再试</span></div>');
                console.log(err);
            }
        });
    } else {
        return false;
    }
});
