package com.amytech.findtmallmm.view;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.amytech.android.framework.api.API;
import com.amytech.android.framework.api.APIList;
import com.amytech.android.framework.api.APIResponse;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.findtmallmm.R;

import org.json.JSONArray;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class HomeActivity extends BaseActivity implements API.APIListener, ActionBar.TabListener {
    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initActionBar(ActionBar actionBar) {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    @Override
    protected void initViews() {
        API.getInstance().post(APIList.TMALL_STYLE, null, this);
    }

    @Override
    public void onAPIStart() {

    }

    @Override
    public void onAPIFinish() {

    }

    @Override
    public void onAPISuccess(APIResponse response) {
        if (response.isSuccess()) {
            JSONArray styleArry = response.result.optJSONArray("allTypeList");
            for (int i = 0; i < styleArry.length(); i++) {
                ActionBar.Tab tab = getSupportActionBar().newTab();
                tab.setText(styleArry.optString(i));
                tab.setTabListener(this);
                actionBar.addTab(tab);
            }
        }
    }

    @Override
    public void onAPIFailure(int errorCode, String errorMessage) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
