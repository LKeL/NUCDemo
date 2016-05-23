package com.heartblood.nucdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.heartblood.nucdemo.common.Global;
import com.heartblood.nucdemo.common.ui.FlexibleSpaceToolbarWebViewActivity;
import com.heartblood.nucdemo.common.ui.MountainScenceView;

import org.json.JSONException;
import org.json.JSONObject;


public class NewsDetailActivity extends FlexibleSpaceToolbarWebViewActivity implements ObservableScrollViewCallbacks {
    private JSONObject mdataObject;
    private ObservableWebView mWebView;
    private MountainScenceView mFlexibleSpace;
    private SimpleDraweeView mFresco;
    Toolbar mToolbar;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        mdataObject = Global.getNewsData();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected Toolbar createActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.news_card_detail_toolbar);
        return mToolbar;
    }

    @Override
    protected ObservableWebView createScrollable() {

        mWebView = (ObservableWebView) findViewById(R.id.news_card_detail);
        mWebView.setScrollViewCallbacks(this);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.addJavascriptInterface(new JSInvokeClass(), "js2java");
        mWebView.loadUrl("file:///android_asset/index.html");

        return mWebView;
    }

    @Override
    protected MountainScenceView getFlexibleSpace() {
        mFlexibleSpace = (MountainScenceView) findViewById(R.id.news_card_detail_FlexibleSpace);
        return mFlexibleSpace;
    }

    @Override
    protected SimpleDraweeView getFresco() {
        mFresco = (SimpleDraweeView) findViewById(R.id.news_card_detail_logo);
        mFresco.bringToFront();
        return mFresco;
    }


    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("parent_activity_name");
        Intent newIntent = null;
        newIntent = new Intent(NewsDetailActivity.this, NewsActivity.class);
        return newIntent;
    }



    public class JSInvokeClass {
        @JavascriptInterface
        public String getWebSite() {
            return "本文章摘自SegmentFault";
        }
        @JavascriptInterface
        public String getTopMargin() {
            Resources r = getResources();
            return String.valueOf(10 + ((double)(mToolbar.getHeight()+r.getDimension(R.dimen.flexible_space_layout_height)+r.getDimension(R.dimen.flexible_space_logo_margin_bottom))*100)/(double)(getScreenHeight()-mToolbar.getHeight()));
        }
        @JavascriptInterface
        public String getTitle() {
            try {
                return mdataObject.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
                return "出错啦";
            }
        }
        @JavascriptInterface
        public String getAuthor() {
            try {
                return mdataObject.getString("author");
            } catch (JSONException e) {
                e.printStackTrace();
                return "出错啦";
            }
        }
        @JavascriptInterface
        public String getSummary() {
            try {
                return mdataObject.getString("summary");
            } catch (JSONException e) {
                e.printStackTrace();
                return "出错啦";
            }
        }
    }

    public static void actionStart(NewsActivity mContext) {
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        mContext.startActivity(intent);
    }
}
