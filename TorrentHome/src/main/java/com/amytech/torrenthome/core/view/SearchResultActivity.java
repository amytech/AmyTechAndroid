package com.amytech.torrenthome.core.view;

import android.app.ActionBar;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amytech.android.framework.utils.AppUtils;
import com.amytech.android.framework.utils.UMengUtils;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.torrenthome.R;
import com.amytech.torrenthome.core.TorrentApp;
import com.amytech.torrenthome.core.controller.SearchController;
import com.amytech.torrenthome.core.model.SearchResultData;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by marktlzhai on 2015/8/12.
 */
public class SearchResultActivity extends BaseActivity implements SearchController.SearchListener {

    private static final String XUNLEI_PACKAGE = "com.xunlei.downloadprovider";

    public static final String DATAKEY_SEARCH_KEYWORLD = "DATAKEY_SEARCH_KEYWORLD";

    private PullToRefreshListView searchResultList;

    private SearchResultAdapter searchResultAdapter;

    private View emptyView;

    private int currentPage = 1;

    private static long startTime;

    @Override
    protected void loadData() {
        requestSearch();
    }

    private void requestSearch() {
        startTime = System.currentTimeMillis();
        String keyWorld = getIntent().getStringExtra(DATAKEY_SEARCH_KEYWORLD);
        actionBar.setTitle(keyWorld);

        SearchController.getInstance(this).search(keyWorld, currentPage, this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initActionBar(ActionBar actionBar, Menu menu) {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initViews() {
        emptyView = findViewById(R.id.search_result_list_empty);
        searchResultList = (PullToRefreshListView) findViewById(R.id.search_result_list);
        searchResultAdapter = new SearchResultAdapter();
        searchResultList.setAdapter(searchResultAdapter);
        searchResultList.setEmptyView(emptyView);
        searchResultList.setMode(PullToRefreshBase.Mode.BOTH);
        searchResultList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                searchResultAdapter.clear();
                currentPage = 1;
                requestSearch();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                currentPage++;
                requestSearch();
            }
        });
    }

    @Override
    protected void onActionBarClicked(MenuItem item) {

    }

    @Override
    protected void onActionBarClickBack() {
        finish();
    }

    @Override
    public void onSearchSuccess(int currentPage, List<SearchResultData> result) {

        long endTime = System.currentTimeMillis();
        UMengUtils.onEventValue(this, TorrentApp.UMENG_EVENT_SEARCH_TIME, null, new Long(endTime).intValue());

        searchResultList.onRefreshComplete();

        if (currentPage == 1) {
            searchResultAdapter.setData(result, false);
        } else {
            searchResultAdapter.setData(result, true);
        }
    }

    @Override
    public void onSearchFailure() {
        showToast("服务器连接失败，请返回重试");
        finish();
    }

    class SearchResultAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        private List<SearchResultData> searchResultDatas;

        private SearchResultAdapter() {
            layoutInflater = LayoutInflater.from(SearchResultActivity.this);
            searchResultDatas = new ArrayList<SearchResultData>();
        }

        private void clear() {
            this.searchResultDatas.clear();
            notifyDataSetChanged();
        }

        private void setData(List<SearchResultData> searchResultDatas, boolean append) {
            if (searchResultDatas != null) {
                if (!append) {
                    this.searchResultDatas.clear();
                }
                this.searchResultDatas.addAll(searchResultDatas);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return searchResultDatas.size();
        }

        @Override
        public SearchResultData getItem(int position) {
            return searchResultDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final SearchResultData item = getItem(position);
            convertView = layoutInflater.inflate(R.layout.item_search_result, parent, false);

            TextView nameView = (TextView) convertView.findViewById(R.id.item_name);
            TextView updateTimeView = (TextView) convertView.findViewById(R.id.item_update_time);
            TextView sizeView = (TextView) convertView.findViewById(R.id.item_size);

            nameView.setText(item.name);
            updateTimeView.setText(item.updateTime);
            sizeView.setText(item.fileSize);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        AppUtils.openURI(Uri.parse(item.downloadURL));
                    } catch (Exception e) {
                        final SweetAlertDialog dialog = new SweetAlertDialog(SearchResultActivity.this);
                        dialog.setTitleText(getString(R.string.no_downloader_title));
                        dialog.setContentText(getString(R.string.no_downloader));
                        dialog.setConfirmText(getString(R.string.no_downloader_xunlei));
                        dialog.setCancelText(getString(R.string.no_downloader_cancel));
                        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismissWithAnimation();
                                }
                            }
                        });
                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                AppUtils.openMarket(XUNLEI_PACKAGE);
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismissWithAnimation();
                                }
                            }
                        });
                        dialog.show();
                    }
                }
            });

            return convertView;
        }
    }
}
