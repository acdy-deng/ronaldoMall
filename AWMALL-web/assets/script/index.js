/**
 * @desc: 跳转至登录
 * @author: RONALDO
 * @date: 2019-03-17 : 19:48
 */
$('.login').on('click', function () {
    window.location.href = 'login.html';
});

/**
 * @desc: tab栏的切换
 * @author: RONALDO
 * @date: 2019-03-15 : 15:16
 */
$('.topbar-wrap .nav ul li').on('click', function () {
    $('.topbar-wrap .nav ul li').attr('class', '');
    $(this).attr('class', 'active');
    switch ($(this).index()) {
        case 0:
            break;
        case 1:
            $.pjax({
                url: "./2.html",
                container: "#m-body",
                push: false,
                fragment: "body"
            });
            break;
    }
});