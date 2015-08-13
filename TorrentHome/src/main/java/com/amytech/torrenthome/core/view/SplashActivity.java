package com.amytech.torrenthome.core.view;

import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.amytech.android.framework.utils.ShareUtils;
import com.amytech.android.framework.utils.UMengUtils;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.torrenthome.BuildConfig;
import com.amytech.torrenthome.R;
import com.amytech.torrenthome.core.TorrentApp;
import com.amytech.torrenthome.core.controller.ConfigController;
import com.amytech.torrenthome.core.controller.SearchTopCommiter;

public class SplashActivity extends BaseActivity implements ConfigController.ConfigLoadListener, SearchTopCommiter.SearchTopCommitListener {

    @Override
    protected void loadData() {
        ConfigController.getInstance(this).loadConfig(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initActionBar(ActionBar actionBar, Menu menu) {

    }

    @Override
    protected void initViews() {
        ShareUtils.init(this, "1104812862", "1104812862");
    }

    @Override
    protected void onActionBarClicked(MenuItem item) {

    }

    @Override
    protected void onActionBarClickBack() {

    }

    @Override
    public void loadConfSuccess() {
        if (BuildConfig.DEBUG) {
//            SearchTopCommiter.commit(this, this);
            doLoadSuccess();
        } else {
            doLoadSuccess();
        }
    }

    private void doLoadSuccess() {
        startActivity(HomeActivity.class);
        finish();
    }

    @Override
    public void loadConfFailure(int errorCode, String error) {
        UMengUtils.onEvent(this, TorrentApp.UMENG_EVENT_SERVER_ERROR, error);
        showToast(R.string.connect_server_failure);
    }

    @Override
    public void onCommitSuccess() {
        showToast("提交测试数据成功");
    }

    @Override
    public void onCommitFailure(String error) {
        showToast("提交测试数据失败\n" + error);
    }
}
