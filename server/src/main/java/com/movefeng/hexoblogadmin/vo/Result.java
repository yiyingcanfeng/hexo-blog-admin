package com.movefeng.hexoblogadmin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;

    public Result(int code) {
        this.code = code;
        this.message = "";
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, T data) {
        this.code = code;
        this.message = "";
        this.data = data;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public interface Code {
        /**
         * 请求成功
         */
        int SUCCESS = 0;
        /**
         * 系统出现异常
         */
        int ERROR = -1;
        /**
         * 登录信息过期
         */
        int LOGIN_INFO_EXPIRE = -2;
        /**
         * 登录信息不正确
         */
        int LOGIN_INFO_INCORRECT = -3;
    }

}
