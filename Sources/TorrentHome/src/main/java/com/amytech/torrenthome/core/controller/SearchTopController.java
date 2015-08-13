package com.amytech.torrenthome.core.controller;

import android.content.Context;

import com.amytech.android.framework.utils.CollectionUtils;
import com.amytech.android.framework.utils.SPUtils;
import com.amytech.torrenthome.core.model.TorrentHomeSearchTop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class SearchTopController {

    public interface SearchTopListener {
        void onTopGetSuccess(Map<String, List<TorrentHomeSearchTop>> topData);

        void onTopGetFailure(int errorCode, String errorString);
    }

    private Map<String, List<TorrentHomeSearchTop>> topData;

    private static SearchTopController instance;

    private Context context;

    private SPUtils spUtils;

    private SearchTopController(Context context) {
        this.context = context;
        this.spUtils = new SPUtils("TorrentHomeConfig");
    }

    public static SearchTopController getInstance(Context context) {
        if (instance == null) {
            instance = new SearchTopController(context);
        }
        return instance;
    }

    public void getTopData(final SearchTopListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("The SearchTopListener is null.");
        }

        if (topData != null && topData.size() > 0) {
            listener.onTopGetSuccess(topData);
        } else {
            BmobQuery<TorrentHomeSearchTop> query = new BmobQuery<TorrentHomeSearchTop>();
            query.findObjects(context, new FindListener<TorrentHomeSearchTop>() {
                @Override
                public void onSuccess(List<TorrentHomeSearchTop> list) {
                    topData = new HashMap<String, List<TorrentHomeSearchTop>>();
                    if (CollectionUtils.notEmpty(list)) {
                        for (TorrentHomeSearchTop item : list) {
                            List<TorrentHomeSearchTop> itemList = topData.get(item.getTabName());
                            if (itemList == null) {
                                itemList = new ArrayList<TorrentHomeSearchTop>();
                            }

                            itemList.add(item);
                            topData.put(item.getTabName(), itemList);
                        }
                    }
                    listener.onTopGetSuccess(topData);
                }

                @Override
                public void onError(int i, String s) {
                    listener.onTopGetFailure(i, s);
                }
            });
        }
    }
}
