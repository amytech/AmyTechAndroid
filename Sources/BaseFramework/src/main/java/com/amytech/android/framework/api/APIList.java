package com.amytech.android.framework.api;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class APIList {

    public static final int TMALL_STYLE = 1261;
    public static final int TMALL_LIST = 1262;

    static {
        System.loadLibrary("BaseFramework");
    }

    //获取APPID
    public native String getAPPID();

    //获取APP Secret
    public native String getAppSecret();

    //淘女郎风格列表
    public native String URL_TMALL_STYLE();

    //淘女郎列表
    public native String URL_TMALL_LIST();
}
