package com.heartblood.nucdemo.common.ui;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

/**
 * Created by heartblood on 16/5/14.
 */

public abstract class HidingScrollListener {
    private int mToolbarOffset = 0;
    private int mToolbarHeight;
    public HidingScrollListener(Activity context, Toolbar toolbar) {
        mToolbarHeight = toolbar.getHeight();
    }
    private void clipToolbarOffset() {
        if(mToolbarOffset > mToolbarHeight) {
            mToolbarOffset = mToolbarHeight;
        } else if(mToolbarOffset < 0) {
            mToolbarOffset = 0;
        }
    }
    public void Scrolled(int dx, int dy) {
        clipToolbarOffset();
        if((mToolbarOffset < mToolbarHeight && dy>0) || (mToolbarOffset >0 && dy<0)) {
            mToolbarOffset += dy;
        }
    }
}