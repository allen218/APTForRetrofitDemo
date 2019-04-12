package com.example.lib;

import java.util.Arrays;
import java.util.List;

public class MethodObj {
    private String mMethodName;
//    private String mParamsType;
//    private String mReturnType;

    private List<ClassObj> mReturnType;
    private List<ClassObj> mMethodParamsType;

    public List<ClassObj> getMethodParamsType() {
        return mMethodParamsType;
    }

    public void setMethodParamsType(List<ClassObj> methodParamsType) {
        mMethodParamsType = methodParamsType;
    }

    public List<ClassObj> getReturnType() {
        return mReturnType;
    }

    public void setReturnType(List<ClassObj> returnType) {
        mReturnType = returnType;
    }

//    public String getParamsType() {
//        return mParamsType;
//    }
//
//    public void setParamsType(String paramsType) {
//        mParamsType = paramsType;
//    }

//    public String getReturnType() {
//        return mReturnType;
//    }
//
//    public void setReturnType(String returnType) {
//        mReturnType = returnType;
//    }

    public String getMethodName() {
        return mMethodName;
    }

    public void setMethodName(String methodName) {
        mMethodName = methodName;
    }

    @Override
    public String toString() {
        return "MethodObj{" +
                "mMethodName='" + mMethodName + '\'' +
                ", mReturnType=" + mReturnType +
                ", mMethodParamsType=" + mMethodParamsType +
                '}';
    }
}
