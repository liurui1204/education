package com.mohe.nanjinghaiguaneducation.common.result;


import com.alibaba.fastjson2.JSON;

import java.io.Serializable;


public class ResultData<T> implements Serializable {

    private boolean success;

    private String code;

    private String message;

    private T data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
