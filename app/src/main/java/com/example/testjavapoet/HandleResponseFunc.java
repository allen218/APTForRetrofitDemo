package com.example.testjavapoet;

import com.example.annotation.Inter;

import io.reactivex.functions.Function;

@Inter(type = "HandleResponseFunc", value = "com.example.testjavapoet.HandleResponseFunc")
public class HandleResponseFunc<T> implements Function<Response<T>, T> {
    public HandleResponseFunc() {
    }

    @Override
    public T apply(Response<T> response) throws Exception {
        return null;
    }
}