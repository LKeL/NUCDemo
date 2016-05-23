package com.heartblood.nucdemo.common.ui;

/**
 * Created by heartblood on 16/5/15.
 */
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.heartblood.nucdemo.R;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
public abstract class FlexibleSpaceToolbarWebViewActivity<S extends Scrollable, V extends View> extends BaseActivity implements ObservableScrollViewCallbacks {

    private Toolbar mToolbar;
    private S mScrollable;
    private V mFlexibleSpace;
    private SimpleDraweeView mSimpleDraweeView;
    private Boolean mFlexibleSpaceIShide;
    private float FlexibleSpacescrollY;
    private float FlexibleSpaceMargin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mToolbar = createActionBar();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFlexibleSpace = getFlexibleSpace();
        mScrollable = createScrollable();
        mScrollable.setScrollViewCallbacks(this);
        mSimpleDraweeView = getFresco();
        mFlexibleSpaceIShide = false;
        Resources r = getResources();
        FlexibleSpaceMargin = r.getDimension(R.dimen.flexible_space_logo_margin_bottom);
        FlexibleSpacescrollY = 0;
    }

    protected abstract int getLayoutResId();
    protected abstract Toolbar createActionBar();
    protected abstract S createScrollable();
    protected abstract V getFlexibleSpace();
    protected abstract SimpleDraweeView getFresco();
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (FlexibleSpacescrollY >= mScrollable.getCurrentScrollY()) {
            mFlexibleSpace.setTranslationY(-scrollY);
            mSimpleDraweeView.setTranslationY(-scrollY);
        } else if (mSimpleDraweeView.getY()+FlexibleSpaceMargin <= -mToolbar.getHeight()) {
            mFlexibleSpaceIShide = true;
            if(FlexibleSpacescrollY == 0)
                FlexibleSpacescrollY = mScrollable.getCurrentScrollY();
        } else {
            mFlexibleSpace.setTranslationY(-scrollY);
            mSimpleDraweeView.setTranslationY(-scrollY);
        }
        if (mSimpleDraweeView.getY()+FlexibleSpaceMargin >= -mToolbar.getHeight()) {
            mFlexibleSpaceIShide = false;
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (mFlexibleSpaceIShide) {
            if (scrollState == ScrollState.UP) {
                if (toolbarIsShown()) {
                    hideToolbar();
                }
            } else if (scrollState == ScrollState.DOWN) {
                if (toolbarIsHidden()) {
                    showToolbar();
                }
            }
        } else {
            showToolbar();
        }

    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mToolbar) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mToolbar) == -mToolbar.getHeight();
    }
    private void hideFlexibleSpace() {
        moveFlexibleSpace(0-mFlexibleSpace.getHeight());
    }
    private void showToolbar() {
        moveToolbar(0);
    }

    private void hideToolbar() {
        moveToolbar(0-mToolbar.getHeight());
    }


    private void moveFlexibleSpace(float toTranslationY) {
        if (ViewHelper.getTranslationY(mFlexibleSpace) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mFlexibleSpace), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(mFlexibleSpace, translationY);
            }
        });

        animator.start();
    }

    private void moveToolbar(float toTranslationY) {
        if (ViewHelper.getTranslationY(mToolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mToolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(mToolbar, translationY);
            }
        });

        animator.start();
    }
}