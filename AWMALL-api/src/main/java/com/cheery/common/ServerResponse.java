package com.cheery.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.io.Serializable;

/**
 * @desc: 用来格式化返回的json数据
 * @className: ServerResponse
 * @author: RONALDO
 * @date: 2019-02-23 15:08
 */
@Getter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {

    /**
     * 返回状态
     */
    private Integer status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private ServerResponse(Integer status) {
        this.status = status;
    }

    private ServerResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private ServerResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 序列化之后不会显示在返回的数据中
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.status.equals(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 定义成功状态下的不同返回
     */
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String message) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), message);
    }

    public static <T> ServerResponse<T> createBySuccessData(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccessMsgAndData(String message, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 定义异常状态下的不同返回
     */
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMsg(String errorMsg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMsg);
    }

    /**
     * 未登录状态下的返回
     */
    public static <T> ServerResponse<T> createByErrorCodeMsg(Integer errorCode, String errorMsg) {
        return new ServerResponse<>(errorCode, errorMsg);
    }

}
