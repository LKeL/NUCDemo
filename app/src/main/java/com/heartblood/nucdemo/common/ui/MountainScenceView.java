package com.heartblood.nucdemo.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by heartblood on 16/5/17.
 */
public class MountainScenceView extends View{

    protected int COLOR_BACKGROUND;
    protected int COLOR_MOUNTAIN_1;
    protected int COLOR_MOUNTAIN_2;
    protected int COLOR_MOUNTAIN_3;
    protected int COLOR_TREE_1_BRANCH;
    protected int COLOR_TREE_1_BTRUNK;
    protected int COLOR_TREE_2_BRANCH;
    protected int COLOR_TREE_2_BTRUNK;
    protected int COLOR_TREE_3_BRANCH;
    protected int COLOR_TREE_3_BTRUNK;
    protected float WIDTH;
    protected float HEIGHT;
    protected int TREE_WIDTH;
    protected int TREE_HEIGHT;
    private float mTreeBendFactor;
    private Paint mMountPaint;
    private Paint mTrunkPaint;
    private Paint mBranchPaint;
    private Paint mBoarderPaint;

    private float mWith;
    private float mHeight;
    private float mScaleX;
    private float mScaleY;
    private float mMoveFactor;
    private float mBounceMax;

    private Path mMount1;
    private Path mMount2;
    private Path mMount3;
    private Path mTrunk;
    private Path mBranch;
    private Matrix mTransMatrix;

    public MountainScenceView(Context context) {
        super(context);
        init();
    }
    public MountainScenceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    protected void init() {
        COLOR_BACKGROUND = Color.parseColor("#7ECEC9");
        COLOR_MOUNTAIN_1 = Color.parseColor("#86DAD7");
        COLOR_MOUNTAIN_2 = Color.parseColor("#3C929C");
        COLOR_MOUNTAIN_3 = Color.parseColor("#3E5F73");
        COLOR_TREE_1_BRANCH = Color.parseColor("#1F7177");
        COLOR_TREE_1_BTRUNK = Color.parseColor("#0C3E48");
        COLOR_TREE_2_BRANCH = Color.parseColor("#34888F");
        COLOR_TREE_2_BTRUNK = Color.parseColor("#1B6169");
        COLOR_TREE_3_BRANCH = Color.parseColor("#57B1AE");
        COLOR_TREE_3_BTRUNK = Color.parseColor("#62A4AD");
        mMountPaint = new Paint();
        mMountPaint.setAntiAlias(true);
        mMountPaint.setStyle(Paint.Style.FILL);
        mTrunkPaint = new Paint();
        mTrunkPaint.setAntiAlias(true);
        mBranchPaint = new Paint();
        mBranchPaint.setAntiAlias(true);
        mBoarderPaint = new Paint();
        mBoarderPaint.setAntiAlias(true);
        mBoarderPaint.setStyle(Paint.Style.STROKE);
        mBoarderPaint.setStrokeWidth(2);
        mBoarderPaint.setStrokeJoin(Paint.Join.ROUND);

        mMount1 = new Path();
        mMount2 = new Path();
        mMount3 = new Path();
        mTrunk = new Path();
        mBranch = new Path();
        mTransMatrix = new Matrix();
        WIDTH = 240;
        HEIGHT = 180;
        TREE_WIDTH = 100;
        TREE_HEIGHT = 200;
        mMoveFactor = 0;
        mBounceMax=  1;
        mTreeBendFactor = Float.MAX_VALUE;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWith = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mScaleX = mWith / WIDTH;
        mScaleY = mHeight / HEIGHT;
        updateMountainPath(mMoveFactor);
        updateTreePath(mMoveFactor, true);
    }
    private void updateTreePath(float factor, boolean force)  {
        if (factor == mTreeBendFactor && !force) {
            return;
        }
        final Interpolator interpolator = PathInterpolatorCompat.create(0.8f, -0.2f * factor);

        final float width = TREE_WIDTH;
        final float height = TREE_HEIGHT;

        final float maxMove = width * 0.2f * factor;
        final float trunkSize = width * 0.05f;
        final float branchSize = width * 0.2f;
        final float x0 = width / 2;
        final float y0 = height;

        final int N = 25;
        final float dp = 1f / N;
        final float dy = -dp * height;
        float y = y0;
        float p = 0;
        float[] xx = new float[N + 1];
        float[] yy = new float[N + 1];
        for (int i = 0; i <= N; i++) {
            xx[i] = interpolator.getInterpolation(p) * maxMove + x0;
            yy[i] = y;

            y += dy;
            p += dp;
        }

        mTrunk.reset();
        mTrunk.moveTo(x0 - trunkSize, y0);
        int max = (int) (N * 0.7f);
        int max1 = (int) (max * 0.5f);
        float diff = max - max1;
        for (int i = 0; i < max; i++) {
            if (i < max1) {
                mTrunk.lineTo(xx[i] - trunkSize, yy[i]);
            } else {
                mTrunk.lineTo(xx[i] - trunkSize * (max - i) / diff, yy[i]);
            }
        }

        for (int i = max - 1; i >= 0; i--) {
            if (i < max1) {
                mTrunk.lineTo(xx[i] + trunkSize, yy[i]);
            } else {
                mTrunk.lineTo(xx[i] + trunkSize * (max - i) / diff, yy[i]);
            }
        }
        mTrunk.close();

        mBranch.reset();
        int min = (int) (N * 0.4f);
        diff = N - min;

        mBranch.moveTo(xx[min] - branchSize, yy[min]);
        mBranch.addArc(new RectF(xx[min] - branchSize, yy[min] - branchSize, xx[min] + branchSize, yy[min] + branchSize), 0f, 180f);
        for (int i = min; i <= N; i++) {
            float f = (i - min) / diff;
            mBranch.lineTo(xx[i] - branchSize + f * f * branchSize, yy[i]);
        }
        for (int i = N; i >= min; i--) {
            float f = (i - min) / diff;
            mBranch.lineTo(xx[i] + branchSize - f * f * branchSize, yy[i]);
        }

    }
    public void backToInit() {
        Timer time=new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("test", String.valueOf(mMoveFactor));
                if (mMoveFactor <= 0) {
                    cancel();
                }
                mMoveFactor = mMoveFactor -(float)0.001;
                updateMountainPath(mMoveFactor);
                updateTreePath(mMoveFactor,false);
                postInvalidate();
            }
        }, 0, 5/* 表示0毫秒之後，每隔15毫秒執行一次 */);
    }
    public void updateFactor(float scrollY) {

        mMoveFactor = Math.max(0, (float) (scrollY*(0.004)));
        Log.e("test", String.valueOf(mMoveFactor));
        updateMountainPath(mMoveFactor);
        updateTreePath(mMoveFactor,false);
        invalidate();
    }
    private void updateMountainPath(float factor) {
        mTransMatrix.reset();
        mTransMatrix.setScale(mScaleX, mScaleY);
        int offset1 = (int) (10 * factor);
        mMount1.reset();
        mMount1.moveTo(0, 95 + offset1);
        mMount1.lineTo(51, 54 + offset1);
        mMount1.lineTo(146, 104 + offset1);
        mMount1.lineTo(227, 55 + offset1);
        mMount1.lineTo(WIDTH, 70 + offset1);
        mMount1.lineTo(WIDTH, HEIGHT);
        mMount1.lineTo(0, HEIGHT);
        mMount1.close();
        mMount1.transform(mTransMatrix);

        int offset2 = (int) (20 * factor);
        mMount2.reset();
        mMount2.moveTo(0, 103 + offset2);
        mMount2.lineTo(60, 80 + offset2);
        mMount2.lineTo(165, 115 + offset2);
        mMount2.lineTo(221, 77 + offset2);
        mMount2.lineTo(WIDTH, 90 + offset2);
        mMount2.lineTo(WIDTH, HEIGHT);
        mMount2.lineTo(0, HEIGHT);
        mMount2.close();
        mMount2.transform(mTransMatrix);

        int offset3 = (int) (30 * factor);
        mMount3.reset();
        mMount3.moveTo(0, 114 + offset3);
        mMount3.cubicTo(30, 106 + offset3, 196, 87 + offset3, WIDTH, 104 + offset3);
        mMount3.lineTo(WIDTH, HEIGHT);
        mMount3.lineTo(0, HEIGHT);
        mMount3.close();
        mMount3.transform(mTransMatrix);
    }
    private void drawTree(Canvas canvas, float scale, float baseX, float baseY,
                          int colorTrunk, int colorBranch) {
        canvas.save();

        final float dx = baseX - TREE_WIDTH * scale / 2;
        final float dy = baseY - TREE_HEIGHT * scale;
        canvas.translate(dx, dy);
        canvas.scale(scale, scale);

        mBranchPaint.setColor(colorBranch);
        canvas.drawPath(mBranch, mBranchPaint);
        mTrunkPaint.setColor(colorTrunk);
        canvas.drawPath(mTrunk, mTrunkPaint);
        mBoarderPaint.setColor(colorTrunk);
        canvas.drawPath(mBranch, mBoarderPaint);

        canvas.restore();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(COLOR_BACKGROUND);

        mMountPaint.setColor(COLOR_MOUNTAIN_1);
        canvas.drawPath(mMount1, mMountPaint);

        canvas.save();
        canvas.scale(-1, 1, getWidth() / 2, 0);
        drawTree(canvas, 0.12f * mScaleX, 160 * mScaleX, (93 + 20 * mMoveFactor) * mScaleY,
                COLOR_TREE_3_BTRUNK, COLOR_TREE_3_BRANCH);
        drawTree(canvas, 0.1f * mScaleX, 200 * mScaleX, (90 + 20 * mMoveFactor) * mScaleY,
                COLOR_TREE_3_BTRUNK, COLOR_TREE_3_BRANCH);
        canvas.restore();
        mMountPaint.setColor(COLOR_MOUNTAIN_2);
        canvas.drawPath(mMount2, mMountPaint);

        drawTree(canvas, 0.2f * mScaleX, 190 * mScaleX, (105 + 30 * mMoveFactor) * mScaleY,
                COLOR_TREE_1_BTRUNK, COLOR_TREE_1_BRANCH);

        drawTree(canvas, 0.14f * mScaleX, 180 * mScaleX, (105 + 30 * mMoveFactor) * mScaleY,
                COLOR_TREE_2_BTRUNK ,COLOR_TREE_2_BRANCH);

        drawTree(canvas, 0.16f * mScaleX, 140 * mScaleX, (105 + 30 * mMoveFactor) * mScaleY,
                COLOR_TREE_2_BTRUNK ,COLOR_TREE_2_BRANCH);

        mMountPaint.setColor(COLOR_MOUNTAIN_3);
        canvas.drawPath(mMount3, mMountPaint);


    }
}
