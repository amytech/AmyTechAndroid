package com.amytech.android.framework.api;

import com.amytech.android.framework.utils.TimeUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class APIRequest implements Serializable {

    public String appID = "";

    public String appSecret = "";

    public String timestamp = "";

    public APIRequest(String appID, String appSecret) {
        this.appID = appID;
        this.appSecret = appSecret;
        this.timestamp = TimeUtils.FORMAT_YYYYMMDDHHMMSS.format(new Date());
    }
}
