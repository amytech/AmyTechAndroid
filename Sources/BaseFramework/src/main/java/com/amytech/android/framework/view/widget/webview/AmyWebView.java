package com.amytech.android.framework.view.widget.webview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.amytech.android.framework.BaseApplication;
import com.amytech.android.framework.R;
import com.amytech.android.framework.utils.NLog;
import com.amytech.android.framework.view.widget.LoadingView;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月8日 下午5:16:48 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月8日 <br>
 *
 * @author marktlzhai
 */
public class AmyWebView extends RelativeLayout implements LoadingView.LoadingInterface {

    public static interface WebViewLoadListener {
        public void onWebViewInited(WebView webView);

        public void onWebPageLoading();

        public void onWebPageLoadProgressChanged(int progress);

        public void onWebPageLoadSucceed();

        public void onWebPageLoadFailed(int errCode, String errDescription);

        public void onUrlChanged(String url);

    }

    private WebView x5Webview;

    private WebViewLoadListener listener;

    private RelativeLayout rootView;

    private String url;

    private LoadingView loadingView;

    public AmyWebView(Context context) {
        super(context);
        initView();
    }

    public AmyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AmyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @SuppressLint("InflateParams")
    private void initView() {
        rootView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_amy_webview, null, false);

        x5Webview = (WebView) rootView.findViewById(R.id.webview);
        loadingView = (LoadingView) rootView.findViewById(R.id.x5loading);
        loadingView.setLoadingInterface(this);
        initWebview();

        addView(rootView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public void startLoadWebPage(String url) {
        this.url = url;
        if (!TextUtils.isEmpty(this.url)) {
            if (listener != null) {
                listener.onWebPageLoading();
            }
            x5Webview.loadUrl(this.url);
        }
    }

    public void startLoadData(String html, String encoding) {
        if (!TextUtils.isEmpty(html)) {
            if (listener != null) {
                listener.onWebPageLoading();
            }
            x5Webview.loadData(html, "", encoding);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebview() {
        WebSettings settings = x5Webview.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        String appCachPath = BaseApplication.getInstance().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachPath);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setBlockNetworkImage(false);
        setBackgroundResource(android.R.color.transparent);

        x5Webview.requestFocus();
        x5Webview.setWebChromeClient(new X5WebViewChromeClient());
        x5Webview.setWebViewClient(new EsWebViewClient());
        x5Webview.setLongClickable(true);

        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        // 解决加载图片闪烁问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            x5Webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setWebViewLoadListener(WebViewLoadListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("deprecation")
    public void destory() {
        if (x5Webview != null) {
            try {
                x5Webview.clearFormData();
                x5Webview.clearAnimation();
                x5Webview.clearDisappearingChildren();
                x5Webview.clearView();
                x5Webview.clearHistory();
                x5Webview.freeMemory();
                rootView.removeView(x5Webview);
                x5Webview.removeAllViews();
                x5Webview.destroyDrawingCache();
                x5Webview.destroy();
                x5Webview = null;
                System.gc();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean browserCanBack() {
        if (x5Webview != null && x5Webview.canGoBack()) {
            return true;
        } else
            return false;
    }

    public void navigateBack() {
        x5Webview.goBack();
    }

    private boolean loadingFinish = false;
    private boolean overrideURL = false;

    private class EsWebViewClient extends WebViewClient {

        private boolean loadingError = false;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            NLog.e("amywebview::shouldOverrideUrlLoading[" + url + "]");
            overrideURL = true;
            view.loadUrl(url);
            if (listener != null) {
                listener.onUrlChanged(url);
            }
            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingFinish = true;
            overrideURL = false;
            NLog.e("amywebview::onPageFinished[" + url + "]");
            // 优化性能，同时兼容19版本后不能正常显示的问题
            if (x5Webview != null && x5Webview.getSettings() != null && !x5Webview.getSettings().getLoadsImagesAutomatically()) {
                x5Webview.getSettings().setLoadsImagesAutomatically(true);
            }

            if (listener != null) {
                listener.onWebPageLoadSucceed();
            }

            if (loadingError) {
                x5Webview.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                loadingView.loadingError();
            } else {
                x5Webview.setVisibility(View.VISIBLE);
                loadingView.setVisibility(View.GONE);
            }

            view.clearAnimation();
            view.clearDisappearingChildren();
            view.destroyDrawingCache();
            view.freeMemory();
            System.gc();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingFinish = false;
            NLog.e("amywebview::onPageStarted[" + url + "]");

            if (overrideURL) {
                return;
            }

            x5Webview.setVisibility(View.GONE);
            loadingView.startLoading();
            loadingView.setVisibility(View.VISIBLE);

            loadingError = false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            NLog.e("amywebview::onReceivedError[" + failingUrl + "]");
            if (listener != null) {
                listener.onWebPageLoadFailed(errorCode, description);
            }

            x5Webview.setVisibility(View.GONE);
            loadingView.loadingError();
            loadingView.setVisibility(View.VISIBLE);

            loadingError = true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            NLog.e("amywebview::onReceivedSslError[" + error + "]");
        }
    }

    private class X5WebViewChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (loadingFinish || overrideURL) {
                loadingView.setVisibility(View.GONE);
                return;
            }
            NLog.e("amywebview::onProgressChanged[" + newProgress + "]");
            if (listener != null) {
                listener.onWebPageLoadProgressChanged(newProgress);
            }
            loadingView.loadingProgress(newProgress);
            loadingView.setVisibility(View.VISIBLE);
        }

        @Override
        public final void onReceivedTitle(WebView view, String title) {
            NLog.e("amywebview::onReceivedTitle[" + title + "]");
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            NLog.e("amywebview::onConsoleMessage[" + consoleMessage + "]");
            return true;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            NLog.e("amywebview::onJsAlert[" + url + "]");
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext()).setTitle(R.string.html_alert_title).setMessage(message)
                    .setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    });

            dialogBuilder.setCancelable(true);
            dialogBuilder.create();
            dialogBuilder.show();
            return true;
        }

    }

    @Override
    public void reload() {
        startLoadWebPage(url);
    }
}
