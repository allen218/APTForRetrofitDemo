package com.example.testjavapoet;


public class Response<T> {
    private int mCode;
    private String mMsg;
    private T mData;

    public Response() {
    }

    public int getCode() {
        return this.mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    public String getMsg() {
        return this.mMsg;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }

    public T getData() {
        return this.mData;
    }

    public void setData(T data) {
        this.mData = data;
    }
}