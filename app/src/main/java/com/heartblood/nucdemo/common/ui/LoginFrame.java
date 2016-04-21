package com.heartblood.nucdemo.common.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by heartblood on 16/4/19.
 */
public class LoginFrame extends FrameLayout{

    private boolean mCompatPadding;

    private boolean mPreventCornerOverlap;

    private final Rect mContentPadding = new Rect();

    private final Rect mShadowBounds = new Rect();

    public LoginFrame(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
}
