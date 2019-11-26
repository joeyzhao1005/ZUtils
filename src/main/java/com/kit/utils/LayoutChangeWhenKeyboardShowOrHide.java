package com.kit.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.kit.utils.log.Zog;

public class LayoutChangeWhenKeyboardShowOrHide {

    /**
     *
     * @param content
     * @param navigaionBarHeight 假如包含navigation高度 那么就传非0的真实导航栏数值
     */
    public static void assistContainer(View content, int navigaionBarHeight) {
        new LayoutChangeWhenKeyboardShowOrHide(content, navigaionBarHeight);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private ViewGroup.LayoutParams frameLayoutParams;
    private int navigaionBarHeight;

    private LayoutChangeWhenKeyboardShowOrHide(View content, int navigaionBarHeight) {
        if (content != null) {
            mChildOfContent = content;
            this.navigaionBarHeight = navigaionBarHeight;
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    possiblyResizeChildOfContent();
                }
            });
            frameLayoutParams = mChildOfContent.getLayoutParams();
        }
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致 //将计算的可视高度设置成视图的高度
            frameLayoutParams.height = usableHeightNow;
            mChildOfContent.requestLayout();
            //请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        //计算视图可视高度
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom + navigaionBarHeight);
    }
}