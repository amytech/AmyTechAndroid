package com.amytech.torrenthome.core.controller;

import android.content.Context;

import com.amytech.android.framework.utils.CollectionUtils;
import com.amytech.android.framework.utils.SPUtils;
import com.amytech.torrenthome.core.model.TorrentHomeConfig;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class ConfigController {

    public interface ConfigLoadListener {
        void loadConfSuccess();

        void loadConfFailure(int errorCode, String error);
    }

    public enum ConfigKey {
        TORRENT_HOME_PAGE,
        TORRENT_LIST_PAGE,
        TORRENT_SEARCH_PAGE
    }

    private static ConfigController instance;

    private Context context;

    private SPUtils spUtils;

    private ConfigController(Context context) {
        this.context = context;
        this.spUtils = new SPUtils("TorrentHomeConfig");
    }

    public static ConfigController getInstance(Context context) {
        if (instance == null) {
            instance = new ConfigController(context);
        }
        return instance;
    }

    public void loadConfig(final ConfigLoadListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("The ConfigLoadListener is null.");
        }

        BmobQuery<TorrentHomeConfig> query = new BmobQuery<TorrentHomeConfig>();
        query.findObjects(context, new FindListener<TorrentHomeConfig>() {
            @Override
            public void onSuccess(List<TorrentHomeConfig> list) {
                if (CollectionUtils.notEmpty(list)) {
                    for (TorrentHomeConfig config : list) {
                        spUtils.putString(config.getConfigKey(), config.getConfigValue());
                    }
                }
                listener.loadConfSuccess();
            }

            @Override
            public void onError(int errorCode, String errorString) {
                listener.loadConfFailure(errorCode, errorString);
            }
        });
    }

    public String getConfig(ConfigKey configKey) {
        return spUtils.getString(configKey.name(), "");
    }
}
