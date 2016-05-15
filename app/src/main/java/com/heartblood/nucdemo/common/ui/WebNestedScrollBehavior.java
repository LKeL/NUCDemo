package com.heartblood.nucdemo.common.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.heartblood.nucdemo.R;

/**
 * Created by heartblood on 16/5/15.
 */
public class WebNestedScrollBehavior extends AppBarLayout.ScrollingViewBehavior {
    public int startX;
    public WebNestedScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        MotionEvent moveXEvent = ev;
        final int action = MotionEventCompat.getActionMasked(ev);
//
//        // Make sure we reset in case we had missed a previous important event.
//        if (action == MotionEvent.ACTION_DOWN) {
//            startX = (int) ev.getX();
//        }
//        if(action == MotionEvent.ACTION_MOVE) {
//            WebView webView = (WebView) child.findViewById(R.id.news_card_detail);
//
//            webView.requestDisallowInterceptTouchEvent(false);
//
//        }
        return false;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;//这里返回true，才会接受到后续滑动事件。
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        //当进行快速滑动
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
