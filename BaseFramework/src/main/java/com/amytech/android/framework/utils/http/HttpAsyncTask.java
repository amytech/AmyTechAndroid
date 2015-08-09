package com.amytech.android.framework.utils.http;

import android.os.AsyncTask;

import com.amytech.android.framework.utils.NLog;
import com.amytech.android.framework.utils.StringUtils;

/**
 * Created by zhaitianlong on 15/8/9.
 */
class HttpAsyncTask extends AsyncTask<HttpRequest, HttpRequest, HttpResponse> {

    private boolean isRunning = false;

    private HttpUtils.HttpRequestListener listener;

    public void setRequestListener(HttpUtils.HttpRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        isRunning = true;
    }

    @Override
    protected HttpResponse doInBackground(HttpRequest... params) {
        HttpRequest request = params[0];
        if (request != null) {
            publishProgress(params[0]);

            String requestMethod = request.requestMethod;
            if (StringUtils.isEquals(requestMethod, "get")) {
                try {
                } catch (Exception e) {
                    NLog.e(getClass(), "http get cacth exception", e);
                }
            }

            if (StringUtils.isEquals(requestMethod, "post")) {
                try {
                } catch (Exception e) {
                    NLog.e(getClass(), "http get cacth exception", e);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(HttpResponse httpResponse) {
        super.onPostExecute(httpResponse);
        isRunning = false;
    }

    @Override
    protected void onProgressUpdate(HttpRequest... values) {
        super.onProgressUpdate(values);
        HttpRequest request = values[0];
        if (listener != null && request != null) {
            listener.onRequestStart(request);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        isRunning = false;
        if(listener!=null) {
            listener.onRequestCancel();
        }
    }

    @Override
    protected void onCancelled(HttpResponse httpResponse) {
        super.onCancelled(httpResponse);
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
