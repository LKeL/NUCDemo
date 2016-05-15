package com.heartblood.nucdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heartblood.nucdemo.NewsActivity;
import com.heartblood.nucdemo.R;
import com.heartblood.nucdemo.adapter.holder.NewsItemHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by heartblood on 16/5/9.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    TextView newsTitle;
    private NewsActivity mContext;
    //private List<NewsListEntity> mDataList;
    private LayoutInflater mLayoutInflater;
    private JSONArray mDatalist;


    public NewsListAdapter(NewsActivity mContext, JSONArray mDataList) {
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mDatalist = mDataList;
        this.mContext = mContext;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsItemHolder(mLayoutInflater.inflate(R.layout.fragment_news_card, parent, false));
    }

    /**
     * bind holder data
     * @param holder bind view holder
     * @param position data list position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO: 16/5/9 bind holder data
        JSONObject mdataObject = null;
        try {
            mdataObject = mDatalist.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NewsItemHolder newsHolder = (NewsItemHolder) holder;
        bindItem(newsHolder, mdataObject, position);
    }

    void bindItem(NewsItemHolder newsHolder, JSONObject mdataObject, int position) {
        newsHolder.initData(mContext, mdataObject);
        newsHolder.setItemPosition(position);
    }
    @Override
    public int getItemCount() {
        // TODO: 16/5/9 return holder data item count
        return mDatalist.length();
    }
}
