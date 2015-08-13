package com.amytech.android.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import com.loopj.android.http.HttpGet;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by marktlzhai on 2015/8/13.
 */
public class ShareUtils {

    private static Tencent tencent;

    private static IWXAPI weixin;

    private static Activity activity;

    public static void init(Activity activity, String qqAppID, String wxAppID) {
        ShareUtils.activity = activity;
        tencent = Tencent.createInstance(qqAppID, activity);
        weixin = WXAPIFactory.createWXAPI(activity, wxAppID);
        weixin.registerApp(wxAppID);
    }

    private static IUiListener getListener() {
        IUiListener listener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                NLog.d(getClass(), "share complete::" + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                NLog.d(getClass(), "share error::" + uiError.errorDetail);
            }

            @Override
            public void onCancel() {
                NLog.d(getClass(), "share cancel");
            }
        };
        return listener;
    }

    public static void share2QQ(String title, String summary, String url, String imageURL, String appName, Activity activity, IUiListener listener) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        if (StringUtils.isNotEmpty(imageURL)) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageURL);
        }
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        if (listener == null) {
            listener = getListener();
        }
        tencent.shareToQQ(activity, params, listener);
    }

    public static void share2QQZone(String title, String summary, String url, ArrayList<String> imageURLs, Activity activity, IUiListener listener) {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
        if (CollectionUtils.notEmpty(imageURLs)) {
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageURLs);
        }
        if (listener == null) {
            listener = getListener();
        }
        tencent.shareToQzone(activity, params, listener);
    }

    public static void share2WX(String title, String summary, String url, Bitmap sharedBitmap, boolean isTimeLine) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = summary;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(sharedBitmap, 150, 150, true);
        sharedBitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        weixin.sendReq(req);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private Bitmap getBitMap(String strUrl) {
        HttpGet httpRequest = new HttpGet(strUrl);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        Bitmap bitmap = null;
        try {
            response = (HttpResponse) httpclient.execute(httpRequest);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
