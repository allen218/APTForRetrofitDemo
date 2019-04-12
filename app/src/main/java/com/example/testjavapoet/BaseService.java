package com.example.testjavapoet;

import com.example.annotation.Inter;

import io.reactivex.FlowableTransformer;

/**
 * 描述:
 * 作者:王聪 001928
 * 创建日期：2019/4/11 on 15:27
 */
@Inter(type = "BaseService", value = "com.example.testjavapoet.BaseService")
public class BaseService {
    public IApiService getApiService() {
        return null;
    }

    public <T> FlowableTransformer<T, T> apply(Request request) {
        return (flowable) -> {
            return null;
        };
    }

    protected <T> Request<T> genRequest(T t) {
        Request<T> request = new Request();
        return request;
    }
}
