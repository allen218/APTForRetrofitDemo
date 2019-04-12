//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.testjavapoet;


public class Request<T> {
    private String mId;
    private String mLockerCode;
    private String mCustomerCode;
    private String mRequestTime;
    private String mSmartName;
    private int mLogType;
    private T mData;

    public Request() {
    }

    public String getId() {
        return this.mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getLockerCode() {
        return this.mLockerCode;
    }

    public void setLockerCode(String lockerCode) {
        this.mLockerCode = lockerCode;
    }

    public String getCustomerCode() {
        return this.mCustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.mCustomerCode = customerCode;
    }

    public String getRequestTime() {
        return this.mRequestTime;
    }

    public void setRequestTime(String requestTime) {
        this.mRequestTime = requestTime;
    }

    public String getSmartName() {
        return this.mSmartName;
    }

    public void setSmartName(String smartName) {
        this.mSmartName = smartName;
    }

    public int getLogType() {
        return this.mLogType;
    }

    public void setLogType(int logType) {
        this.mLogType = logType;
    }

    public T getData() {
        return this.mData;
    }

    public void setData(T data) {
        this.mData = data;
    }
}
