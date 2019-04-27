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
            console.log('index page');
            break;
        case 1:
            $.pjax({
                url: "./page/living-room.html",
                container: "#m-body",
                push: false,
                fragment: "body"
            });
            break;
        case 2:
            $.pjax({
                url: "./page/tip.html",
                container: "#m-body",
                push: false,
                fragment: "body"
            });
            break;
        case 3:
            $.pjax({
                url: "./page/tip.html",
                container: "#m-body",
                push: false,
                fragment: "body"
            });
            break;
        case 4:
            $.pjax({
                url: "./page/tip.html",
                container: "#m-body",
                push: false,
                fragment: "body"
            });
            break;
        case 5:
            $.pjax({
                url: "./page/tip.html",
                container: "#m-body",
                push: false,
                fragment: "body"
            });
            break;
    }
});