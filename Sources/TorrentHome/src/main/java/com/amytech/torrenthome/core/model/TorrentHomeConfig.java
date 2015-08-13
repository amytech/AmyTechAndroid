package com.amytech.torrenthome.core.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class TorrentHomeConfig extends BmobObject {

    private String configKey = "";
    private String configValue = "";

    public TorrentHomeConfig() {
        super();
    }

    public TorrentHomeConfig(String configKey, String configValue) {
        super();
        this.configKey = configKey;
        this.configValue = configValue;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
