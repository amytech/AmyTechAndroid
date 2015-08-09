package com.amytech.android.framework.utils.http;

import com.amytech.android.framework.utils.NLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaitianlong on 15/8/9.
 */
public class HttpUtils {

    public class HttpRequestListener {
        public void onRequestStart(HttpRequest request) {
            NLog.d(getClass(), "start request" + request);
        }

        public void onRequestSuccess(HttpRequest request, HttpResponse response) {
            NLog.d(getClass(), "Request success.\n" + response);
        }

        public void onRequestFailure(HttpRequest request, HttpResponse response) {
            NLog.d(getClass(), "Request failure.\n" + response);
        }

        public void onRequestCancel(HttpRequest request, HttpResponse response) {
            NLog.d(getClass(), "Request cancel.");
        }
    }

    private static final Map<HttpRequest, HttpAsyncTask> REQUEST_MAP = new HashMap<HttpRequest, HttpAsyncTask>();

    public static void get(HttpRequest request, HttpRequestListener listener) {
        HttpAsyncTask task = checkTask(request);
        task.setRequestListener(listener);
        task.execute(request);
    }

    public static void post(HttpRequest request, HttpRequestListener listener) {
        HttpAsyncTask task = checkTask(request);
        task.setRequestListener(listener);
        task.execute(request);
    }

    private static HttpAsyncTask checkTask(HttpRequest request) {
        HttpAsyncTask task = REQUEST_MAP.get(request);

        //如果该请求正在运行，忽略
        if (task != null && !task.isRunning()) {
            REQUEST_MAP.remove(request);
            return null;
        }

        task = new HttpAsyncTask();
        REQUEST_MAP.put(request, task);

        return task;
    }
}
