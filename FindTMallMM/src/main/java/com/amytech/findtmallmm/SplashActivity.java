package com.amytech.findtmallmm;

import com.actionbarsherlock.app.ActionBar;
import com.amytech.android.framework.view.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getDefaultTheme() {
        return R.style.Theme_Sherlock_NoActionBar;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initActionBar(ActionBar actionBar) {

    }
}
