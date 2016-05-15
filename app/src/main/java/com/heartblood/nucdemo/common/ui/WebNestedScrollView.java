package com.heartblood.nucdemo.common.ui;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;

/**
 * Created by heartblood on 16/5/15.
 */
public class WebNestedScrollView extends NestedScrollView {
    public WebNestedScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(0, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, 0, dyUnconsumed, offsetInWindow);
    }
}
