package com.amytech.android.framework.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * Created by marktlzhai on 2015/8/13.
 */
public class UMengUtils {
    public static void onEvent(Context context, String eventName) {
        MobclickAgent.onEvent(context, eventName);
    }

    public static void onEvent(Context context, String eventName, String eventData) {
        MobclickAgent.onEvent(context, eventName, eventData);
    }

    public static void onEvent(Context context, String eventName, Map<String, String> eventData) {
        MobclickAgent.onEvent(context, eventName, eventData);
    }

    public static void onEventValue(Context context, String eventName, Map<String, String> eventData, int value) {
        MobclickAgent.onEventValue(context, eventName, eventData, value);
    }

    public static void reportError(Context context, String errorMessage) {
        MobclickAgent.reportError(context, errorMessage);
    }

    public static void reportError(Context context, Throwable error) {
        MobclickAgent.reportError(context, error);
    }
}
