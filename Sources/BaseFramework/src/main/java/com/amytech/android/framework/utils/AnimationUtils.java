package com.amytech.android.framework.utils;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class AnimationUtils {
    public static void showAnimation(Techniques anim, View targetView) {
        showAnimation(anim, targetView, 0, null);
    }

    public static void showAnimation(Techniques anim, View targetView, long duration) {
        showAnimation(anim, targetView, duration, null);
    }

    public static void showAnimation(Techniques anim, View targetView, long duration, Animator.AnimatorListener listener) {
        YoYo.AnimationComposer animationComposer = YoYo.with(anim);

        if (duration > 0) {
            animationComposer = animationComposer.duration(duration);
        }

        if (listener != null) {
            animationComposer = animationComposer.withListener(listener);
        }

        animationComposer.playOn(targetView);
    }
}
