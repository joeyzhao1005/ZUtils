package com.kit.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimUtils {


    /**
     * 伴随动画显示控件
     *
     * @param context
     * @param view
     * @param resAnimId
     */
    public static void show(Context context, final View view, int resAnimId) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            view.startAnimation(loadAnimation(context, resAnimId, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            }));
        }
    }

    /**
     * 伴随动画隐藏控件
     *
     * @param context
     * @param view
     * @param resAnimId
     */
    public static void hidden(Context context, final View view, int resAnimId) {
        if (view.getVisibility() == View.VISIBLE) {
            view.startAnimation(loadAnimation(context, resAnimId, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            }));
        }
    }

    /**
     * 开始动画
     *
     * @param context
     * @param viewId
     * @param resAnimId
     */
    public static void startAnim(Context context, int viewId, int resAnimId) {
//		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
//		findViewById(R.id.xxx).startAnimation(shake);

        Animation shake = AnimationUtils.loadAnimation(context, resAnimId);
        ((Activity) context).findViewById(viewId).startAnimation(shake);
    }


    /**
     * 加载动画
     *
     * @param context
     * @param id
     * @return
     */
    public static Animation loadAnimation(Context context, int id, Animation.AnimationListener animationListener) {

        Animation myAnimation = AnimationUtils.loadAnimation(context, id);
        myAnimation.setAnimationListener(animationListener);
        return myAnimation;
    }
}
