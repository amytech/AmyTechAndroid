package com.amytech.android.framework.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;

import com.amytech.android.framework.BaseApplication;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月6日 上午11:41:07 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月6日 <br>
 *
 * @author marktlzhai
 */
public class AppUtils {

    public static void exit(Context context) {
        NLog.d("Application will exit.");
        System.exit(0);
        Process.killProcess(Process.myPid());
    }

    /**
     * 判断程序是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取ApplicationInfo
     */
    public static final ApplicationInfo getApplicationInfo(Application app) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = app.getPackageManager().getApplicationInfo(app.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    /**
     * 获取MateData
     */
    public static final Bundle getMetaData(Application app) {
        return getApplicationInfo(app).metaData;
    }

    /**
     * 获取Version Code
     *
     * @return
     */
    public static int getVersionCode() {
        PackageManager pm = BaseApplication.getInstance().getPackageManager();
        int appVersion = 0;
        try {
            appVersion = pm.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    /**
     * 获取Version Name
     *
     * @return
     */
    public static String getVersionName() {
        PackageManager pm = BaseApplication.getInstance().getPackageManager();
        String appVersion = "";
        try {
            appVersion = pm.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    public static String getCurrentDeviceFullModel() {
        String deviceFullModel = "";
        if (Build.MANUFACTURER == null || Build.MODEL == null)
            return deviceFullModel;
        deviceFullModel = Build.MANUFACTURER + "(" + Build.MODEL + ")";
        return deviceFullModel;
    }

    public static void openMarket(String packageName) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getInstance().startActivity(intent);
    }

    public static boolean isInstallApp(Context context, String appPackage) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(appPackage, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
