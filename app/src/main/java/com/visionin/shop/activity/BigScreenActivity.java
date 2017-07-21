package com.visionin.shop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.visionin.shop.Beans.DNSBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;
import com.visionin.shop.utils.Config;
import com.visionin.shop.utils.NetWorkUtils;
import com.visionin.shop.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigScreenActivity extends BaseActivity {

    private static final String TAG = "BigScreenActivity";

    private AsyncHttpServer server;

    @BindView(R.id.web_view) WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_screen);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //getActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        request(API_ENUM.DNS, new CallbackForRequest<DNSBean>() {
            @Override
            public void doSuccess(DNSBean bean) {
                Toast.makeText(getApplicationContext(), bean.getCode() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doError(Object object) {

            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                Logger.addLogAdapter(new AndroidLogAdapter());
                String ip = NetWorkUtils.getLocalIpAddress(getApplicationContext());
                map.put("ip", ip);
                String token = (String)SharedPreferencesUtils.getParam(BigScreenActivity.this, Config.TOKEN, "");
                if (token == null){

                }
                Logger.i(token + "");
                map.put("token", token);
                return map;
            }

            @Override
            public API_ENUM getApiEnum() {
                return API_ENUM.DNS;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //server.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        server = new AsyncHttpServer();
//
//        List<WebSocket> _sockets = new ArrayList<WebSocket>();
//
//        server.get("/", new HttpServerRequestCallback() {
//            @Override
//            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                response.send("Hello!!!");
//            }
//        });
//
//// listen on port 5000
//        server.listen(5005);
//// browsing http://localhost:5000 will return Hello!!!
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(Config.HOST + "BigScreen/BigScreen/");

    }
}
