package com.cheery.common;

import com.cheery.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 控制层父类
 * @className: BaseController
 * @author: RONALDO
 * @date: 2019-03-12 12:01
 */
@Component
@org.springframework.web.bind.annotation.ControllerAdvice
public class BaseController {

    protected ApiResult<?> BaseController(User user, ApiResult result) {
        if (null == user) {
            throw new GlobalException(ApiCode.NEED_LOGIN.getCode(), ApiCode.NEED_LOGIN.getDesc());
        }
        return result;
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
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", ApiCode.ERROR_SERVER.getCode());
        map.put("msg", ApiCode.ERROR_SERVER.getDesc());
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
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", e.getCode());
        map.put("msg", e.getDesc());
        return map;
    }

}
