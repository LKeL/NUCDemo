package com.heartblood.nucdemo.common.ui;

import android.content.Context;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by heartblood on 16/5/15.
 */
public class NestedWebView extends WebView{
    public NestedWebView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
