package com.amytech.android.framework.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.amytech.android.framework.R;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public abstract class BaseActivity extends SherlockActivity {

    protected abstract int getLayoutID();

    protected abstract void initActionBar(ActionBar actionBar);

    protected abstract void initViews();

    protected int getDefaultTheme() {
        return R.style.Theme_Sherlock;
    }

    protected String TAG = getClass().getSimpleName();

    protected Handler handler = new Handler();

    protected ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getDefaultTheme());
        super.onCreate(savedInstanceState);

        setContentView(getLayoutID());

        actionBar = getSupportActionBar();
        initActionBar(actionBar);

        initViews();
    }

    public void startActivity(Class<? extends BaseActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void startActivity(Class<? extends BaseActivity> activityClass, Bundle data) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(data);
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends BaseActivity> activityClass, int requestCode) {
        Intent intent = new Intent(this, activityClass);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class<? extends BaseActivity> activityClass, int requestCode, Bundle data) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(data);
        startActivityForResult(intent, requestCode);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int messageRes) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show();
    }
}
