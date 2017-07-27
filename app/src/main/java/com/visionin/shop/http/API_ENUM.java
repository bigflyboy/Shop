package com.visionin.shop.http;

import com.visionin.shop.Beans.DNSBean;
import com.visionin.shop.Beans.GetDNSBean;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.Beans.LoginBean;

/**
 * Created by wangzhiyuan on 2017/7/12.
 */

public enum API_ENUM {
    LOGIN("api/ApiLogin", HTTP_METHOD.POST, LoginBean.class),
    GOODS_LIST("Admin/Goods/ServletGoods?action=list_goods_all", HTTP_METHOD.POST, GoodsBean.class),
    DNS("api/ApiDNS?action=report_ip", HTTP_METHOD.POST, DNSBean.class),
    GOOD_BY_SCAN("api/ApiGoods?action=list_goods_byNumber", HTTP_METHOD.POST, GoodsBean.class),
    GET_DNS("api/ApiDNS?action=get_ip", HTTP_METHOD.POST, GetDNSBean.class);


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
