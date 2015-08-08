package com.amytech.findtmallmm.view;

import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.amytech.android.framework.api.API;
import com.amytech.android.framework.utils.AnimationUtils;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.findtmallmm.R;
import com.daimajia.androidanimations.library.Techniques;
import com.nineoldandroids.animation.Animator;

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

    @Override
    protected void initViews() {
        API.getInstance().post(null);

        final View logoView = findViewById(R.id.splash_logo);
        final View appNameView = findViewById(R.id.splash_name);
        final View enterView = findViewById(R.id.splash_enter);
        enterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HomeActivity.class);
                finish();
            }
        });

        AnimationUtils.showAnimation(Techniques.RubberBand, logoView, 1000, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                appNameView.setVisibility(View.VISIBLE);
                AnimationUtils.showAnimation(Techniques.Flash, appNameView);

                enterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
