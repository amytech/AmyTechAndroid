package com.amytech.torrenthome.core.view;

import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.amytech.android.framework.view.BaseActivity;
import com.amytech.torrenthome.R;
import com.amytech.torrenthome.core.controller.SearchTopController;
import com.amytech.torrenthome.core.model.TorrentHomeSearchTop;

import java.util.List;
import java.util.Map;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class HomeActivity extends BaseActivity implements SearchTopController.SearchTopListener {

    private TextView homeTest;

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

    }

    @Override
    protected void initViews() {
        homeTest = (TextView) findViewById(R.id.home_test);
    }

    @Override
    protected void onActionBarClicked(MenuItem item) {

    }

    @Override
    protected void onActionBarClickBack() {

    }

    @Override
    public void onTopGetSuccess(Map<String, List<TorrentHomeSearchTop>> topData) {
        for (String tab : topData.keySet()) {
            homeTest.append(tab + "\n");
            for (TorrentHomeSearchTop topItem : topData.get(tab)) {
                homeTest.append(topItem.toString() + "\n");
            }
            homeTest.append("\n");
        }
    }

    @Override
    public void onTopGetFailure(int errorCode, String errorString) {
        homeTest.setText("get failure\n" + errorString);
    }
}
