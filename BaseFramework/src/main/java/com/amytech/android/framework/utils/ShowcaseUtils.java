package com.amytech.android.framework.utils;

import android.app.Activity;
import android.view.View;

import com.amytech.android.framework.R;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionItemTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

/**
 * Created by marktlzhai on 2015/8/13.
 */
public class ShowcaseUtils {
    public static void showcaseForActionItem(Activity activity, int actionItemID, int titleRes, int textRes, OnShowcaseEventListener listener) {
        showcaseForActionItem(activity, actionItemID, activity.getString(titleRes), activity.getString(textRes), listener);
    }

    public static void showcaseForActionItem(Activity activity, int actionItemID, String title, String text, OnShowcaseEventListener listener) {
        new ShowcaseView.Builder(activity, true).setStyle(R.style.Default_Showcase_Style).setTarget(new ActionItemTarget(activity, actionItemID)).setContentTitle(title).setContentText(text).setShowcaseEventListener(listener).hideOnTouchOutside().build();
    }

    public static void showcaseForView(Activity activity, View view, int titleRes, int textRes, OnShowcaseEventListener listener) {
        showcaseForView(activity, view, activity.getString(titleRes), activity.getString(textRes), listener);
    }

    public static void showcaseForView(Activity activity, View view, String title, String text, OnShowcaseEventListener listener) {
        new ShowcaseView.Builder(activity, true).setStyle(R.style.Default_Showcase_Style).setTarget(new ViewTarget(view)).setContentTitle(title).setContentText(text).setShowcaseEventListener(listener).hideOnTouchOutside().build();
    }
}
