package com.amytech.torrenthome.core.view;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amytech.android.framework.utils.AppUtils;
import com.amytech.android.framework.utils.CollectionUtils;
import com.amytech.android.framework.utils.ImageUtils;
import com.amytech.android.framework.utils.ShareUtils;
import com.amytech.android.framework.utils.ShowcaseUtils;
import com.amytech.android.framework.utils.StringUtils;
import com.amytech.android.framework.utils.UMengUtils;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.torrenthome.R;
import com.amytech.torrenthome.core.TorrentApp;
import com.amytech.torrenthome.core.controller.SearchTopController;
import com.amytech.torrenthome.core.model.TorrentHomeSearchTop;
import com.amytech.torrenthome.core.view.widget.SearchView;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.update.UpdateResponse;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class HomeActivity extends BaseActivity implements SearchTopController.SearchTopListener, ActionBar.TabListener, SearchView.OnSearchListener {

    private static final String SOHU_VIDEO = "com.sohu.sohuvideo";
    private static final String SOHU_TV = "com.sohu.tv";

    private static final String SP_STR_LAST_SELECT_TAB = "LAST_SELECT_TAB";

    private Map<String, List<TorrentHomeSearchTop>> topData;
    private TextView emptyView;
    private ListView homeList;
    private HomeListAdapter homeAdapter;

    private SearchView searchView;

    @Override
    protected void loadData() {
        SearchTopController.getInstance(this).getTopData(this);
        //检查更新
        UMengUtils.checkUpdate(this, true, new UMengUtils.CheckUpdateCallback() {
            @Override
            public void hasUpdate(final UpdateResponse updateResponse) {
                final SweetAlertDialog dialog = new SweetAlertDialog(HomeActivity.this);
                dialog.setTitleText(MessageFormat.format(getString(R.string.check_update_new_title), updateResponse.version));
                dialog.setContentText(MessageFormat.format(getString(R.string.check_update_new_context), updateResponse.updateLog));
                dialog.setCancelText(getString(R.string.check_update_cancel));
                dialog.setConfirmText(getString(R.string.check_update_download));
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
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismissWithAnimation();
                        }

                        File downloadAPK = UMengUtils.getDownloadedFile(HomeActivity.this, updateResponse);
                        if (downloadAPK != null && downloadAPK.exists() && downloadAPK.isFile()) {
                            UMengUtils.installDownload(HomeActivity.this, downloadAPK);
                        } else {
                            UMengUtils.startDownloadUpdate(HomeActivity.this, updateResponse);
                        }
                    }
                });
                dialog.show();
            }

            @Override
            public void noUpdate() {

            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initActionBar(ActionBar actionBar, Menu menu) {
        MenuItem searchItem = menu.add(0, R.id.menu_share, 0, R.string.share_to);
        searchItem.setIcon(R.drawable.topbar_share);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        searchItem.setActionProvider(new ActionProvider(this) {
            @Override
            public View onCreateActionView() {
                return null;
            }

            @Override
            public void onPrepareSubMenu(SubMenu subMenu) {
                subMenu.clear();

                subMenu.add(0, R.id.submenu_qq, 0, R.string.share_to_qq).setIcon(R.drawable.selector_share_qq);
                subMenu.add(0, R.id.submenu_wx, 0, R.string.share_to_wx).setIcon(R.drawable.selector_share_wx);
                subMenu.add(0, R.id.submenu_wxf, 0, R.string.share_to_wx_timeline).setIcon(R.drawable.selector_share_wx_f);
            }

            @Override
            public boolean hasSubMenu() {
                return true;
            }
        });

        MenuItem feedbackItem = menu.add(0, R.id.menu_feedback, 1, R.string.umeng_fb_feedback);
        feedbackItem.setIcon(R.drawable.topbar_rss);
        feedbackItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

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
    public void onResume() {
        super.onResume();
        if (!StringUtils.isEquals(spUtils.getString(TorrentApp.SP_STRING_SHOWCASE_HOME_VERSION, ""), AppUtils.getVersionName())) {
            ShowcaseUtils.showcaseForActionItem(this, R.id.menu_share, R.string.showcase_share_title, R.string.showcase_share, new OnShowcaseEventListener() {
                @Override
                public void onShowcaseViewHide(ShowcaseView showcaseView) {
                    spUtils.putString(TorrentApp.SP_STRING_SHOWCASE_HOME_VERSION, AppUtils.getVersionName());
                    ShowcaseUtils.showcaseForActionItem(HomeActivity.this, R.id.menu_feedback, R.string.showcase_more_title, R.string.showcase_more, new OnShowcaseEventListener() {
                        @Override
                        public void onShowcaseViewHide(ShowcaseView showcaseView) {
                            ShowcaseUtils.showcaseForView(HomeActivity.this, searchView, R.string.showcase_search_title, R.string.showcase_search, null);
                        }

                        @Override
                        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                        }

                        @Override
                        public void onShowcaseViewShow(ShowcaseView showcaseView) {

                        }
                    });
                }

                @Override
                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                }

                @Override
                public void onShowcaseViewShow(ShowcaseView showcaseView) {

                }
            });
        }
    }

    @Override
    protected void onActionBarClicked(MenuItem item) {
        int itemID = item.getItemId();

        //用户反馈
        if (itemID == R.id.menu_feedback) {
            UMengUtils.openFeedback(this);
        }

        //分享到QQ
        if (itemID == R.id.submenu_qq) {
            ShareUtils.share2QQ(getString(R.string.share_title), getString(R.string.share_summary), getString(R.string.share_target_url), getString(R.string.share_image_url), getString(R.string.app_name), this, null);
            UMengUtils.onEvent(this, TorrentApp.UMENG_EVENT_SHARE, "QQ");
        }

        //分享到微信
        if (itemID == R.id.submenu_wx) {
            ShareUtils.share2WX(getString(R.string.share_title), getString(R.string.share_summary), getString(R.string.share_target_url), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), false);
            UMengUtils.onEvent(this, TorrentApp.UMENG_EVENT_SHARE, "WX");
        }

        //分享到朋友圈
        if (itemID == R.id.submenu_wxf) {
            ShareUtils.share2WX(getString(R.string.share_title), getString(R.string.share_summary), getString(R.string.share_target_url), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), true);
            UMengUtils.onEvent(this, TorrentApp.UMENG_EVENT_SHARE, "WX_LINE");
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
            final TextView itemWatch = (TextView) convertView.findViewById(R.id.home_watch_online);

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

            if (!StringUtils.isEquals(item.getWatchURL(), "N/A")) {
                itemWatch.setText(R.string.item_watch_online);
                itemWatch.setEnabled(true);
                itemWatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Map<String, String> mobclickData = new HashMap<String, String>();
                        if (AppUtils.isInstallApp(HomeActivity.this, SOHU_VIDEO) || AppUtils.isInstallApp(HomeActivity.this, SOHU_TV)) {
                            mobclickData.put("SOHU_INSTALL", "true");
                            try {
                                AppUtils.openURI(Uri.parse(item.getWatchURL()));
                                UMengUtils.onEvent(HomeActivity.this, TorrentApp.UMENG_EVENT_TOP_ITEM_PLAY, mobclickData);
                            } catch (Exception e) {
                                showToast("无法打开播放器");
                            }
                        } else {
                            mobclickData.put("SOHU_INSTALL", "false");
                            final SweetAlertDialog dialog = new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.NORMAL_TYPE);
                            dialog.setTitleText(getString(R.string.tip));
                            dialog.setContentText(getString(R.string.check_no_sohu));
                            dialog.setConfirmText(getString(R.string.sohu_goto));
                            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    try {
                                        AppUtils.openURI(Uri.parse(item.getWatchURL()));
                                        UMengUtils.onEvent(HomeActivity.this, TorrentApp.UMENG_EVENT_TOP_ITEM_PLAY, mobclickData);
                                    } catch (Exception e) {
                                        showToast("无法打开播放器");
                                    }
                                }
                            });
                            dialog.setCancelText(getString(R.string.sohu_cancel));
                            dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if (dialog != null && dialog.isShowing()) {
                                        dialog.dismissWithAnimation();
                                    }
                                }
                            });
                            dialog.show();
                        }
                    }
                });
            } else {
                itemWatch.setText(R.string.item_watch_no);
                itemWatch.setEnabled(false);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UMengUtils.onEvent(HomeActivity.this, TorrentApp.UMENG_EVENT_TOP_ITEM_CLICK, item.getName());
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
