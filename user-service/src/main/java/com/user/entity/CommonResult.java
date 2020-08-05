package com.user.entity;

/**
 * @program:springcloudzyz
 * @description:
 * @author: Mr.zyz
 * @create: 2020-08-05 14:23
 */
public class CommonResult<T> {

    private Integer code;

    private String message;

    private T data;

    public CommonResult(T data) {
        this(200,"操作成功",data);
    }

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResult() {
    }

    public CommonResult(Integer code, String message) {
        this(code,message,null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
