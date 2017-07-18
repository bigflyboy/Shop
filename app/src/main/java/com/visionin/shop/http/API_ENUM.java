package com.visionin.shop.http;

import com.visionin.shop.Beans.DNSBean;
import com.visionin.shop.Beans.LoginBean;

/**
 * Created by wangzhiyuan on 2017/7/12.
 */

public enum API_ENUM {
    LOGIN("/ApiLogin", HTTP_METHOD.POST, LoginBean.class),
    DNS("/ApiDNS?action=report_ip", HTTP_METHOD.POST, DNSBean.class),
    GOODS_LIST("/goods_list", HTTP_METHOD.GET);


    public String mPath;
    public HTTP_METHOD mRequestMethod;
    public Class mClass;

    private static final String TAG = "API_ENUM";

    API_ENUM(String path, HTTP_METHOD requestMethod){
        this(path, requestMethod, null);
    }

    API_ENUM(String path, HTTP_METHOD requestMethod, Class clasz){
        this.mPath = path;
        this.mRequestMethod = requestMethod;
        mClass = clasz;
    }

    public Class _getClass() {
        return mClass == null ? Object.class : mClass;
    }

    String getValue() {
        return mPath;
    }

    public enum HTTP_METHOD {
        GET("GET"), POST("POST");

        String mMethod;

        HTTP_METHOD(String method) {
            mMethod = method;
        }

        @Override
        public String toString() {
            return mMethod;
        }
    }
}
