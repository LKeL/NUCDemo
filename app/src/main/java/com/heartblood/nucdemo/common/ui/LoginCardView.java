package com.heartblood.nucdemo.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.heartblood.nucdemo.R;

import java.io.InputStream;

/**
 * Created by heartblood on 16/4/19.
 * 自定义登陆卡view
 */
public class LoginCardView extends View {

    private Paint mPaint;
    private int mWith;
    private int mHeight;
    private int mCornerRadius;
    private int mPortaritcornerRadius;
    private RectF mRectF;
    private Canvas mTransparentCanvas;
    private Paint mTransparentPaint;
    private TypedArray mTypedArray;
    private Bitmap mBitmap;
    private int mCircleheight;

    private float mOffsetX;
    private float mOffsetY;
    private int mShadowColor;
    private float mShadowRadius;



    public LoginCardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.loginCardView);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCornerRadius = mTypedArray.getDimensionPixelOffset(R.styleable.loginCardView_cornerRadius, 0);
        mPortaritcornerRadius = mTypedArray.getDimensionPixelOffset(R.styleable.loginCardView_portaritcornerRadius, 300);
        mCircleheight = mTypedArray.getDimensionPixelOffset(R.styleable.loginCardView_circleheight, 12);

        mOffsetX = mTypedArray.getDimensionPixelOffset(R.styleable.loginCardView_offsetX, 0);
        mOffsetY = mTypedArray.getDimensionPixelOffset(R.styleable.loginCardView_offsetY, 0);
        mShadowColor = mTypedArray.getColor(R.styleable.loginCardView_shadowColor, getResources().getColor(R.color.shadowColor));
        mShadowRadius = mTypedArray.getDimensionPixelOffset(R.styleable.loginCardView_shadowRadius, 0);

    }
    private void init() {
        mRectF = new RectF(mShadowRadius, mShadowRadius, mWith - mShadowRadius, mHeight - 2*mShadowRadius);
        mBitmap = Bitmap.createBitmap(mWith, mHeight, Bitmap.Config.ARGB_8888);
        mTransparentCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mTransparentPaint = new Paint();

        mPaint.setAntiAlias(true);
        mTransparentPaint.setAntiAlias(true);

        mPaint.setShadowLayer(mShadowRadius, mOffsetX, mOffsetY, mShadowColor);
        mTransparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        mTransparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWith = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTransparentCanvas.drawRoundRect(mRectF, mCornerRadius, mCornerRadius, mPaint);
        mTransparentCanvas.drawCircle(mWith / 2, mCircleheight, mPortaritcornerRadius, mTransparentPaint);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

    }
}
