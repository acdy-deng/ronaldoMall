package com.cheery.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @className: ControllerAdvice
 * @author: RONALDO
 * @date: 2019-03-09 17:51
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

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
        Map<String, Object> map = new HashMap<>(3);
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
