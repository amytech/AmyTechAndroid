package com.amytech.torrenthome.core.view;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amytech.android.framework.utils.CollectionUtils;
import com.amytech.android.framework.utils.StringUtils;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.torrenthome.R;
import com.amytech.torrenthome.core.controller.SearchTopController;
import com.amytech.torrenthome.core.model.TorrentHomeSearchTop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class HomeActivity extends BaseActivity implements SearchTopController.SearchTopListener, ActionBar.TabListener {

    private static final String SP_STR_LAST_SELECT_TAB = "LAST_SELECT_TAB";

    private Map<String, List<TorrentHomeSearchTop>> topData;
    private TextView emptyView;
    private ListView homeList;
    private HomeListAdapter homeAdapter;

    @Override
    protected void loadData() {
        SearchTopController.getInstance(this).getTopData(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initActionBar(ActionBar actionBar, Menu menu) {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    @Override
    protected void initViews() {
        this.emptyView = (TextView) findViewById(R.id.home_page_list_empty);
        this.homeList = (ListView) findViewById(R.id.home_page_list);
        this.homeAdapter = new HomeListAdapter();
        this.homeList.setAdapter(homeAdapter);
        this.homeList.setEmptyView(emptyView);
    }

    @Override
    protected void onActionBarClicked(MenuItem item) {

    }

    @Override
    protected void onActionBarClickBack() {

    }

    @Override
    public void onTopGetSuccess(Map<String, List<TorrentHomeSearchTop>> topData) {
        this.topData = topData;
        String lastSelectedTab = spUtils.getString(SP_STR_LAST_SELECT_TAB, "");
        Set<String> tabSet = topData.keySet();
        if (CollectionUtils.notEmpty(tabSet)) {
            for (String tabName : tabSet) {
                ActionBar.Tab tab = actionBar.newTab();
                tab.setText(tabName);
                tab.setTabListener(this);
                actionBar.addTab(tab, tabName.equals(lastSelectedTab));
            }
        }

        if (StringUtils.isEmpty(lastSelectedTab)) {
            actionBar.setSelectedNavigationItem(0);
        }
    }

    @Override
    public void onTopGetFailure(int errorCode, String errorString) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        showToast("selected");
        String tabName = tab.getText().toString();
        List<TorrentHomeSearchTop> topList = topData.get(tabName);
        if (CollectionUtils.notEmpty(topList)) {
            homeAdapter.setData(topList);
        }
        spUtils.putString(SP_STR_LAST_SELECT_TAB, tabName);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    private class HomeListAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        private List<TorrentHomeSearchTop> adapterDatas;

        private HomeListAdapter() {
            inflater = LayoutInflater.from(HomeActivity.this);
            adapterDatas = new ArrayList<TorrentHomeSearchTop>();
        }

        private void setData(List<TorrentHomeSearchTop> adapterDatas) {
            this.adapterDatas.clear();
            this.adapterDatas.addAll(adapterDatas);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.adapterDatas.size();
        }

        @Override
        public TorrentHomeSearchTop getItem(int position) {
            return this.adapterDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TorrentHomeSearchTop item = getItem(position);
            TextView tv = new TextView(HomeActivity.this);
            tv.setTextColor(Color.WHITE);
            tv.setText(item.getTabName() + "::" + item.getName());
            return tv;
        }
    }
}
