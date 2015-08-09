package com.amytech.android.framework.api;

import com.amytech.android.framework.utils.TimeUtils;
import com.loopj.android.http.RequestParams;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class APIRequest implements Serializable {

    public String appID = "";

    public String appSecret = "";

    public String timestamp = "";

    public Map<String, String> params;

    public APIRequest(String appID, String appSecret) {
        this.appID = appID;
        this.appSecret = appSecret;
        this.timestamp = TimeUtils.FORMAT_YYYYMMDDHHMMSS.format(new Date());
        this.params = new HashMap<String, String>();
        this.params.put("showapi_appid", appID);
        this.params.put("showapi_sign", appSecret);
        this.params.put("showapi_timestamp", timestamp);
    }

    public void setParams(Map<String, String> params) {
        this.params.putAll(params);
    }

    public RequestParams getRequestParams() {
        if (params == null || params.size() == 0) {
            return null;
        }

        RequestParams requestParams = new RequestParams(params);
        return requestParams;
    }
}
