package com.heartblood.nucdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.heartblood.nucdemo.adapter.NewsListAdapter;
import com.heartblood.nucdemo.common.ui.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NewsActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private NewsListAdapter mNewsListAdapter;
    private JSONArray mJsonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getNewsJson("http://119.29.58.43/api/getSfBlog/getPage=1", this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void getNewsJson(String url, final NewsActivity newsActivity) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mJsonData = response;
                mRecyclerView.setAdapter(mNewsListAdapter = new NewsListAdapter(newsActivity, mJsonData));
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // TODO: 16/5/9 获取json失败
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("测试","报错报错啦");
            }
        });
    }
}
