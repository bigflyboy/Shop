package com.visionin.shop.http;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.visionin.shop.utils.Config;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangzhiyuan on 2017/7/17.
 */

public class ApiHttp {
    private CallbackForRequest mCallback;
    private API_ENUM mAPI_enum;
    private String url = "BigScreen/api";

    public ApiHttp(API_ENUM apiEnum, CallbackForRequest callback){
        mAPI_enum = apiEnum;
        mCallback = callback;
        if(mAPI_enum.mRequestMethod == API_ENUM.HTTP_METHOD.GET){
            doGet(mAPI_enum.mPath);
        }else if(mAPI_enum.mRequestMethod == API_ENUM.HTTP_METHOD.POST){
            doPost(mAPI_enum.mPath);
        }

    }

    protected void doGet(String path){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url(Config.HOST + url + path);
        if(mCallback.getParams()!=null) {
            Iterator<Map.Entry<String, String>> entries = mCallback.getParams().entrySet().iterator();

            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = requestBuilder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.doError();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.doSuccess();
                    }
                });
            }
        });

    }

    protected void doPost(String path){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if(mCallback.getParams()!=null) {
            Iterator<Map.Entry<String, String>> entries = mCallback.getParams().entrySet().iterator();

            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(Config.HOST + url + path)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.doError();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.doSuccess();
                    }
                });
            }
        });
    }
}