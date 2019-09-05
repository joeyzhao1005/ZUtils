package com.kit.app;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.kit.utils.DeviceUtils;

/**
 * 可以自动监听软键盘的弹出，并自动 resize
 */
public class KeyboardPatch {
    private Activity activity;
    private View decorView;
    private View contentView;
    private boolean isNeedPaddingNavigationBar;


    public void destory() {
        disable();
        isNeedPaddingNavigationBar = false;
        contentView = null;
        decorView = null;
        activity = null;
    }

    public boolean isNeedPaddingNavigationBar() {
        return isNeedPaddingNavigationBar;
    }


    /**
     * 是否需要强制性的padding导航栏
     *
     * @param needPaddingNavigationBar
     */
    public void setNeedPaddingNavigationBar(boolean needPaddingNavigationBar) {
        isNeedPaddingNavigationBar = needPaddingNavigationBar;
    }

    /**
     * 构造函数
     *
     * @param act         需要解决bug的activity
     * @param contentView 界面容器，activity中一般是R.id.content，也可能是Fragment的容器，根据个人需要传递
     */
    public KeyboardPatch(Activity act, View contentView) {
        this.activity = act;
        this.decorView = act.getWindow().getDecorView();
        this.contentView = contentView;
    }


    /**
     * 构造函数
     *
     * @param fragment    需要解决bug的fragment
     * @param contentView 界面容器，activity中一般是R.id.content，也可能是Fragment的容器，根据个人需要传递
     */
    public KeyboardPatch(Fragment fragment, View contentView) {
        this.activity = fragment.getActivity();
        this.decorView = fragment.getActivity().getWindow().getDecorView();
        this.contentView = contentView;
    }


    /**
     * 监听layout变化
     */
    public void enable() {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= 19) {
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    /**
     * 取消监听
     */
    private void disable() {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= 19) {
            decorView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();

            decorView.getWindowVisibleDisplayFrame(r);
            int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
            int diff = height - r.bottom;
            if (diff < 0) {
                diff = 0;
            }


            if (diff != 0) {

                int apply = diff;
                if (isNeedPaddingNavigationBar) {
                    apply = diff + DeviceUtils.getNavigationBarHeight(activity);
                }

                if (contentView.getPaddingBottom() != apply) {
                    contentView.setPadding(0, 0, 0, apply);
                }
            } else {
                if (contentView.getPaddingBottom() != 0) {
                    contentView.setPadding(0, 0, 0, 0);
                }
            }
        }
    };
}