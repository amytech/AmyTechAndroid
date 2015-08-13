package com.amytech.torrenthome.core;

import com.amytech.android.framework.BaseApplication;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class TorrentApp extends BaseApplication {

    @Override
    protected String getServerAPPID() {
        return "19de0356e063fadefb5d12121f738e7e";
    }

    public static final String UMENG_EVENT_SEARCH_TIME = "SEARCH_TIME";
    public static final String UMENG_EVENT_SERVER_ERROR = "SERVER_ERROR";
    public static final String UMENG_EVENT_SHARE = "SHARE";
    public static final String UMENG_EVENT_TOP_ITEM_CLICK = "TOP_ITEM_CLICK";
    public static final String UMENG_EVENT_TOP_ITEM_PLAY = "TOP_ITEM_PLAY";
}
