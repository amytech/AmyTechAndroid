package com.amytech.android.framework.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.io.File;
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

    public static void openFeedback(Context context) {
        FeedbackAgent agent = new FeedbackAgent(context);
        agent.removeWelcomeInfo();
        agent.startFeedbackActivity();
    }

    public static void syncFeedback(Context context) {
        FeedbackAgent agent = new FeedbackAgent(context);
        agent.sync();
    }

    public static void checkUpdate(Context context, boolean wifiOnly, final CheckUpdateCallback listener) {
        UmengUpdateAgent.setDefault();
        UmengUpdateAgent.setUpdateOnlyWifi(wifiOnly);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.update(context);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        if (listener != null) {
                            listener.hasUpdate(updateResponse);
                        }
                        break;
                    case UpdateStatus.No: // has no update
                        if (listener != null) {
                            listener.noUpdate();
                        }
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        break;
                    case UpdateStatus.Timeout: // time out
                        break;
                }
            }
        });
    }

    public static File getDownloadedFile(Context context, UpdateResponse response) {
        return UmengUpdateAgent.downloadedFile(context, response);
    }

    public static void startDownloadUpdate(Context context, UpdateResponse response) {
        UmengUpdateAgent.startDownload(context, response);
    }

    public static void installDownload(Context context, File file) {
        UmengUpdateAgent.startInstall(context, file);
    }

    public interface CheckUpdateCallback {
        void hasUpdate(UpdateResponse updateResponse);

        void noUpdate();
    }
}
