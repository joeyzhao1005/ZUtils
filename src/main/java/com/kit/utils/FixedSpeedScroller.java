package com.kit.utils;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 利用这个类来修正ViewPager的滑动速度
 * 我们重写 startScroll方法，忽略传过来的 duration 属性
 * 而是采用我们自己设置的时间
 */
public class FixedSpeedScroller extends Scroller {

  public int mDuration=1500;
  public FixedSpeedScroller(Context context) {
    super(context);
  }

  public FixedSpeedScroller(Context context, Interpolator interpolator) {
    super(context, interpolator);
  }

  public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
    super(context, interpolator, flywheel);
  }

  @Override public void startScroll(int startX, int startY, int dx, int dy) {
    startScroll(startX,startY,dx,dy,mDuration);
  }

  @Override public void startScroll(int startX, int startY, int dx, int dy, int duration) {
    //管你 ViewPager 传来什么时间，我完全不鸟你
    super.startScroll(startX, startY, dx, dy, mDuration);
  }

  public int getmDuration() {
    return mDuration;
  }

  public void setmDuration(int duration) {
    mDuration = duration;
  }
}
