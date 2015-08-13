package com.amytech.torrenthome.core.controller;

import android.content.Context;

import com.amytech.android.framework.utils.StringUtils;
import com.amytech.torrenthome.core.model.SearchResultData;
import com.amytech.torrenthome.core.parse.SearchResultParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by marktlzhai on 2015/8/12.
 */
public class SearchController {
    public interface SearchListener {
        void onSearchSuccess(int currentPage, List<SearchResultData> result);

        void onSearchFailure();
    }

    private static final AsyncHttpClient CLIENT = new AsyncHttpClient();

    private static SearchController instance;

    private Context context;

    private SearchController(Context context) {
        this.context = context;

        CLIENT.setTimeout(3000);
        CLIENT.setMaxRetriesAndTimeout(0, 0);
    }

    public static SearchController getInstance(Context context) {
        if (instance == null) {
            instance = new SearchController(context);
        }
        return instance;
    }

    public void search(String keyWorld, final int currentPage, final SearchListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("The SearchListener is null.");
        }

        String searchURL = ConfigController.getInstance(context).getConfig(ConfigController.ConfigKey.TORRENT_SEARCH_PAGE);
        searchURL = MessageFormat.format(searchURL, URLDecoder.decode(keyWorld), String.valueOf(currentPage));
        CLIENT.get(searchURL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onSearchFailure();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (listener != null && StringUtils.isNotEmpty(responseString)) {
                    listener.onSearchSuccess(currentPage, SearchResultParser.parse(responseString));
                }
            }
        });
    }
}
