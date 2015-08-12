package com.amytech.android.framework;

import android.app.Application;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.amytech.android.framework.utils.AppUtils;
import com.amytech.android.framework.utils.ImageUtils;
import com.amytech.android.framework.utils.StringUtils;

import java.io.File;

import cn.bmob.v3.Bmob;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public abstract class BaseApplication extends Application {

    protected abstract String getServerAPPID();

    private static BaseApplication instance;

    /**
     * 屏幕宽度（像素）
     */
    public static int SCREEN_WIDTH;

    /**
     * 屏幕高度（像素）
     */
    public static int SCREEN_HEIGHT;

    /**
     * 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     */
    public static float DENSITY;

    /**
     * 屏幕密度（每寸像素：120/160/240/320）
     */
    public static int DENSITY_DPI;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // 初始化屏幕参数
        initDisplay();

        if (StringUtils.isNotEmpty(getServerAPPID())) {
            Bmob.initialize(this, getServerAPPID());
        }
    }

    /**
     * 初始化屏幕参数
     */
    private void initDisplay() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        DENSITY = dm.density;
        DENSITY_DPI = dm.densityDpi;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public File getAppFolder() {
        return new File(Environment.getExternalStorageDirectory(), "amytech/" + getPackageName());
    }
}
