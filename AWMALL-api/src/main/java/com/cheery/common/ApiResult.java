package com.cheery.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.io.Serializable;

/**
 * @desc: 用来格式化返回的json数据
 * @className: ApiResult
 * @author: RONALDO
 * @date: 2019-02-23 15:08
 */
@Getter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApiResult<T> implements Serializable {

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

    private ApiResult(Integer status) {
        this.status = status;
    }

    private ApiResult(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private ApiResult(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private ApiResult(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 序列化之后不会显示在返回的数据中
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.status.equals(ApiStatus.SUCCESS.getCode());
    }

    /**
     * 定义成功状态下的不同返回
     */
    public static <T> ApiResult<T> createBySuccess() {
        return new ApiResult<T>(ApiStatus.SUCCESS.getCode());
    }

    public static <T> ApiResult<T> createBySuccessMsg(String message) {
        return new ApiResult<T>(ApiStatus.SUCCESS.getCode(), message);
    }

    public static <T> ApiResult<T> createBySuccessData(T data) {
        return new ApiResult<T>(ApiStatus.SUCCESS.getCode(), data);
    }

    public static <T> ApiResult<T> createBySuccessMsgAndData(String message, T data) {
        return new ApiResult<T>(ApiStatus.SUCCESS.getCode(), message, data);
    }

    /**
     * 定义异常状态下的不同返回
     */
    public static <T> ApiResult<T> createByError() {
        return new ApiResult<T>(ApiStatus.ERROR.getCode(), ApiStatus.ERROR.getDesc());
    }

    public static <T> ApiResult<T> createByErrorMsg(String errorMsg) {
        return new ApiResult<T>(ApiStatus.ERROR.getCode(), errorMsg);
    }

    /**
     * 未登录状态下的返回
     */
    public static <T> ApiResult<T> createByErrorCodeMsg(Integer errorCode, String errorMsg) {
        return new ApiResult<>(errorCode, errorMsg);
    }

}
