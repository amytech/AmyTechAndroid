package com.amytech.android.framework.api;

import com.amytech.android.framework.utils.NLog;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class API {

    private static API instance;

    private API() {

    }

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    public void post(APIRequest request) {
        NLog.e(new APIList().TMallStyleURL());
    }
}
