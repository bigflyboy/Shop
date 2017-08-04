package com.visionin.shop.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.ApiHttp;
import com.visionin.shop.http.CallbackForRequest;

/**
 * Created by wangzhiyuan on 2017/7/17.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private boolean toastFlag = true;

    protected void request(API_ENUM api_enum, CallbackForRequest callback){
        new ApiHttp(api_enum, callback);
    }

    protected void toastDebug(String str){
        if (toastFlag){
            Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
        }
    }

    protected void toast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }

}
