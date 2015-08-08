package com.amytech.findtmallmm.view;

import com.actionbarsherlock.app.ActionBar;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.findtmallmm.R;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class HomeActivity extends BaseActivity {
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

    }
}
