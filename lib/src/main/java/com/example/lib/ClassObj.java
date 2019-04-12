package com.example.lib;

/**
 * 描述:
 * 作者:王聪 001928
 * 创建日期：2019/4/12 on 15:56
 */
public class ClassObj {
    private String mPackageName;
    private String mClassName;

    public ClassObj(String packageName, String className) {
        mPackageName = packageName;
        mClassName = className;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }
}
