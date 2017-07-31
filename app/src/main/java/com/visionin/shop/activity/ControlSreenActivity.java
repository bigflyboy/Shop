package com.visionin.shop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.visionin.shop.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ControlSreenActivity extends BaseActivity {

    @BindView(R.id.control_pre) Button mButtonPre;
    @BindView(R.id.control_next) Button mButtonNext;
    @BindView(R.id.control_exit) Button mButtonExit;

    OkHttpClient mOkHttpClient = new OkHttpClient();

    private String mIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_sreen);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        mIp = bundle.getString("ip");
    }

    @OnClick(R.id.control_pre)
    public void controlPre(View v){
        String url = "http://"+mIp+":5005/lockscreen?goods_number=" + "control_pre";
        sendMsg(url);
    }

    @OnClick(R.id.control_next)
    public void controlNext(View v){
        String url = "http://"+mIp+":5005/lockscreen?goods_number=" + "control_next";
        sendMsg(url);
    }

    @OnClick(R.id.control_exit)
    public void controlExit(View v){
        String url = "http://"+mIp+":5005/lockscreen?goods_number=" + "control_exit";
        sendMsg(url);
    }

    public void sendMsg(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
//                Logger.e(mGoodNumber);
                Call call = mOkHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Logger.e(response.body().string());
//                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
