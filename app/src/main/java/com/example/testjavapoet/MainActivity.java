package com.example.testjavapoet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.annotation.Inter;

/**
 * 描述:
 * 作者:王聪 001928
 * 创建日期：2019/4/11 on 11:36
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("com.example.testjavapoet.IApiService");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
