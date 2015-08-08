package com.amytech.android.framework.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class API {

    public interface APIListener {
        void onAPIStart();

        void onAPIFinish();

        void onAPISuccess(APIResponse response);

        void onAPIFailure(int errorCode, String errorMessage);
    }

    private static API instance;

    private static final APIList API_LIST = new APIList();

    private static final AsyncHttpClient CLIENT = new AsyncHttpClient();

    private API() {
        CLIENT.setTimeout(3000);
        CLIENT.setMaxRetriesAndTimeout(0, 0);
    }

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    public void post(int api, Map<String, String> params, final APIListener listener) {
        APIRequest request = new APIRequest(API_LIST.getAPPID(), API_LIST.getAppSecret());
        if (params != null) {
            request.setParams(params);
        }

        String url = "";
        switch (api) {
            case APIList.TMALL_STYLE:
                url = API_LIST.URL_TMALL_STYLE();
                break;
            case APIList.TMALL_LIST:
                url = API_LIST.URL_TMALL_LIST();
                break;
        }

        CLIENT.post(url, request.getRequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (listener != null) {
                    listener.onAPIStart();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (listener != null) {
                    listener.onAPIFinish();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (listener != null) {
                    if (response != null) {
                        int errorCode = response.optInt("showapi_res_code");
                        String errorMessage = response.optString("showapi_res_error");
                        JSONObject result = response.optJSONObject("showapi_res_body");
                        if (errorCode == 0) {
                            listener.onAPISuccess(new APIResponse(errorCode, errorMessage, result));
                        } else {
                            listener.onAPIFailure(errorCode, errorMessage);
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                if (listener != null) {
                    listener.onAPIFailure(-1, responseString);
                }
            }
        });
    }
}
