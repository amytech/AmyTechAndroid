package com.amytech.android.framework.view;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.amytech.android.framework.R;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public abstract class BaseActivity extends SherlockActivity {

    protected abstract int getLayoutID();

    protected abstract void initActionBar(ActionBar actionBar);

    protected int getDefaultTheme() {
        return R.style.Theme_Sherlock;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getDefaultTheme());
        super.onCreate(savedInstanceState);

        setContentView(getLayoutID());

        initActionBar(getSupportActionBar());
    }
}
