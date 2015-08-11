package com.amytech.torrenthome.core.controller;

import android.content.Context;

import com.amytech.android.framework.utils.CollectionUtils;
import com.amytech.android.framework.utils.FileUtils;
import com.amytech.torrenthome.R;
import com.amytech.torrenthome.core.model.TorrentHomeSearchTop;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class SearchTopCommiter {

    public interface SearchTopCommitListener {
        void onCommitSuccess();

        void onCommitFailure(String error);
    }

    public static void commit(Context context, final SearchTopCommitListener listener) {

        List<BmobObject> commitData = new ArrayList<BmobObject>();
        List<String> data = FileUtils.readFileToList(context.getResources().openRawResource(R.raw.debug_data), "UTF-8");
        if (CollectionUtils.notEmpty(data)) {
            for (String metaData : data) {
                String[] dataSplit = metaData.split(",");
                if (dataSplit != null && dataSplit.length == 11) {
                    commitData.add(new TorrentHomeSearchTop(Integer.valueOf(dataSplit[0]), dataSplit[1], dataSplit[2], dataSplit[3], dataSplit[4], dataSplit[5], dataSplit[6], dataSplit[7], dataSplit[8], dataSplit[9], dataSplit[10]));
                }
            }
        }

        new BmobObject().insertBatch(context, commitData, new SaveListener() {
            @Override
            public void onSuccess() {
                listener.onCommitSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onCommitFailure(s);
            }
        });
    }
}
