package com.amytech.android.framework.api;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class APIList {

    static{
        System.loadLibrary("BaseFramework");
    }

    //淘女郎风格列表
    public native String TMallStyleURL();

    //淘女郎列表
    public native String TMallURL();
}
