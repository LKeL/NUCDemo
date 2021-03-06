package com.heartblood.nucdemo.common;

import org.json.JSONObject;

/**
 * Created by heartblood on 16/4/17.
 * 保存公共的全局方法和变量
 */
public class Global {
    public static JSONObject newsData;
    /**
     *  coding host
     */
    public static final String HOST_CODING_DEFAULT = "https://coding.net";
    public static final String HOST_CODING_MOBILE = "https://m.coding.net";
    public static final String HOST_CODING = HOST_CODING_DEFAULT;
    public static String HOST_CODING_API = HOST_CODING_DEFAULT + "/api";
    public static void setNewsData(JSONObject Data) {
        newsData = Data;
    }
    public static JSONObject getNewsData() {
        return newsData;
    }
}

