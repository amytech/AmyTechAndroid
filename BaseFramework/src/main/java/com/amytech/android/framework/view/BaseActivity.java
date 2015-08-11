package com.amytech.android.framework.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public abstract class BaseActivity extends Activity {

    protected abstract void loadData();

    protected abstract int getLayoutID();

    protected abstract void initActionBar(ActionBar actionBar, Menu menu);

    protected abstract void initViews();

    protected abstract void onActionBarClicked(MenuItem item);

    protected abstract void onActionBarClickBack();

    protected String TAG = getClass().getSimpleName();

    protected Handler handler = new Handler();

    protected ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutID());

        actionBar = getActionBar();
        if (actionBar != null) {
            setOverflowShowingAlways();
        }

        initViews();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 200);
    }

    protected void requestActionBarCanBack() {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        initActionBar(actionBar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onActionBarClickBack();
        } else {
            onActionBarClicked(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
