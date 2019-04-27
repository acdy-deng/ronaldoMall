package com.cheery.common;

import com.cheery.pojo.User;
import com.cheery.service.*;
import com.cheery.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 控制层公共类 用于注入service层和异常拦截
 * @className: BaseController
 * @author: RONALDO
 * @date: 2019-04-11 21:31
 */
@Component
@ControllerAdvice
public abstract class BaseController {

    @Autowired
    protected UserServiceImpl userService;

    @Autowired
    protected IMailService mailService;

    @Autowired
    protected TemplateEngine templateEngine;

    @Autowired
    protected IShippingService shippingService;

    @Autowired
    protected IProductService productService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected ICommentService commentService;

    @Autowired
    protected ICategoryService categoryService;

    @Autowired
    protected ICartService cartService;

    @Autowired
    protected HttpSession session;

    protected User user() {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == user) {
            throw new GlobalException(ApiStatus.NEED_LOGIN.getCode(), ApiStatus.NEED_LOGIN.getDesc());
        }
        return user;
    }

    /**
     * desc: 处理全部未知异常
     *
     * @param e       Exception
     * @param request HttpServletRequest
     * @return Object
     * @auther RONALDO
     * @date: 2019-03-09 18:37
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handleException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", ApiStatus.ERROR_SERVER.getCode());
        map.put("msg", ApiStatus.ERROR_SERVER.getDesc());
        map.put("error", e);
        map.put("url", request.getRequestURL());
        return map;
    }

    /**
     * desc: 处理已知异常
     *
     * @param e GlobalException
     * @return Object
     * @auther RONALDO
     * @date: 2019-03-09 18:38
     */
    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public Object handleMyException(GlobalException e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", e.getCode());
        map.put("msg", e.getDesc());
        return map;
    }

}
