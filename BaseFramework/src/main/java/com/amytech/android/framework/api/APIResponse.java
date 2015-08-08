package com.amytech.android.framework.api;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class APIResponse implements Serializable {
    public int errorCode = 0;
    public String errorMessage = "";
    public JSONObject result;

    public APIResponse(int errorCode, String errorMessage, JSONObject result) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.result = result;
    }

    public boolean isSuccess() {
        return errorCode == 0 && result != null;
    }
}
