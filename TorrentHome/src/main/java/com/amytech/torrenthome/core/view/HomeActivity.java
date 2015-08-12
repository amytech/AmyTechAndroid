package com.amytech.torrenthome.core.view;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amytech.android.framework.utils.CollectionUtils;
import com.amytech.android.framework.utils.ImageUtils;
import com.amytech.android.framework.utils.StringUtils;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.torrenthome.R;
import com.amytech.torrenthome.core.controller.SearchTopController;
import com.amytech.torrenthome.core.model.TorrentHomeSearchTop;
import com.amytech.torrenthome.core.view.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class HomeActivity extends BaseActivity implements SearchTopController.SearchTopListener, ActionBar.TabListener, SearchView.OnSearchListener {

    private static final int MENU_ITEM_SHARE = 1;

    private static final String SP_STR_LAST_SELECT_TAB = "LAST_SELECT_TAB";

    private Map<String, List<TorrentHomeSearchTop>> topData;
    private TextView emptyView;
    private ListView homeList;
    private HomeListAdapter homeAdapter;

    private SearchView searchView;

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
        MenuItem searchItem = menu.add(0, MENU_ITEM_SHARE, 0, "");
        searchItem.setIcon(R.drawable.topbar_share);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    @Override
    protected void initViews() {
        this.searchView = (SearchView) findViewById(R.id.home_search_view);
        this.searchView.setOnSearchListener(this);

        this.emptyView = (TextView) findViewById(R.id.home_page_list_empty);
        this.homeList = (ListView) findViewById(R.id.home_page_list);
        this.homeAdapter = new HomeListAdapter();
        this.homeList.setAdapter(homeAdapter);
        this.homeList.setEmptyView(emptyView);
    }

    @Override
    protected void onActionBarClicked(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == MENU_ITEM_SHARE) {
            showToast("share app");
        }
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

        if (actionBar.getSelectedTab() == null || StringUtils.isEmpty(actionBar.getSelectedTab().getText())) {
            actionBar.setSelectedNavigationItem(0);
        }
    }

    @Override
    public void onTopGetFailure(int errorCode, String errorString) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
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

    @Override
    public void doSearch(String query) {
        if (StringUtils.isNotEmpty(query)) {
            toSearchResultActivity(query);
        }
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
            final TorrentHomeSearchTop item = getItem(position);
            convertView = inflater.inflate(R.layout.item_hometop, parent, false);

            ImageView itemImage = (ImageView) convertView.findViewById(R.id.home_item_image);
            TextView itemName = (TextView) convertView.findViewById(R.id.home_item_name);
            TextView itemType = (TextView) convertView.findViewById(R.id.home_item_type);
            TextView itemArea = (TextView) convertView.findViewById(R.id.home_item_area);
            TextView itemDirector = (TextView) convertView.findViewById(R.id.home_item_director);
            TextView itemStarring = (TextView) convertView.findViewById(R.id.home_item_starring);
            TextView itemSource = (TextView) convertView.findViewById(R.id.home_item_source);
            TextView itemCompere = (TextView) convertView.findViewById(R.id.home_item_compere);
            TextView itemDescription = (TextView) convertView.findViewById(R.id.home_item_description);
            View itemDownload = convertView.findViewById(R.id.home_start_download);

            View itemTypeView = convertView.findViewById(R.id.item_type_layout);
            itemTypeView.setVisibility(View.GONE);
            View itemAreaView = convertView.findViewById(R.id.item_area_layout);
            itemAreaView.setVisibility(View.GONE);
            View itemDirectorView = convertView.findViewById(R.id.item_director_layout);
            itemDirectorView.setVisibility(View.GONE);
            View itemStarringView = convertView.findViewById(R.id.item_starring_layout);
            itemStarringView.setVisibility(View.GONE);
            View itemSourceView = convertView.findViewById(R.id.item_source_layout);
            itemSourceView.setVisibility(View.GONE);
            View itemCompereView = convertView.findViewById(R.id.item_compere_layout);
            itemCompereView.setVisibility(View.GONE);
            View itemDescriptionView = convertView.findViewById(R.id.item_description_layout);
            itemDescriptionView.setVisibility(View.GONE);

            if (StringUtils.isNotEmpty(item.getImageURL())) {
                ImageUtils.displayCenterInside(HomeActivity.this, item.getImageURL(), itemImage, R.dimen.home_item_image_width, R.dimen.home_item_image_height);
            }

            if (StringUtils.isNotEmpty(item.getName())) {
                itemName.setText(item.getName());
            }

            if (!StringUtils.isEquals(item.getDescription(), "N/A")) {
                itemDescription.setText(item.getDescription());
                itemDescriptionView.setVisibility(View.VISIBLE);
            } else {
                itemDescriptionView.setVisibility(View.GONE);
            }

            if (!StringUtils.isEquals(item.getType(), "N/A")) {
                itemType.setText(item.getType());
                itemTypeView.setVisibility(View.VISIBLE);
            } else {
                itemTypeView.setVisibility(View.GONE);
            }

            if (!StringUtils.isEquals(item.getArea(), "N/A")) {
                itemArea.setText(item.getArea());
                itemAreaView.setVisibility(View.VISIBLE);
            } else {
                itemAreaView.setVisibility(View.GONE);
            }

            if (!StringUtils.isEquals(item.getDirector(), "N/A")) {
                itemDirector.setText(item.getDirector());
                itemDirectorView.setVisibility(View.VISIBLE);
            } else {
                itemDirectorView.setVisibility(View.GONE);
            }

            if (!StringUtils.isEquals(item.getStarring(), "N/A")) {
                itemStarring.setText(item.getStarring());
                itemStarringView.setVisibility(View.VISIBLE);
            } else {
                itemStarringView.setVisibility(View.GONE);
            }

            if (!StringUtils.isEquals(item.getSource(), "N/A")) {
                itemSource.setText(item.getSource());
                itemSourceView.setVisibility(View.VISIBLE);
            } else {
                itemSourceView.setVisibility(View.GONE);
            }

            if (!StringUtils.isEquals(item.getCompere(), "N/A")) {
                itemCompere.setText(item.getCompere());
                itemCompereView.setVisibility(View.VISIBLE);
            } else {
                itemCompereView.setVisibility(View.GONE);
            }

            itemDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toSearchResultActivity(item.getName());
                }
            });

            return convertView;
        }
    }

    private void toSearchResultActivity(String keyWorld) {
        Bundle data = new Bundle();
        data.putString(SearchResultActivity.DATAKEY_SEARCH_KEYWORLD, keyWorld);
        startActivity(SearchResultActivity.class, data);
    }
}
