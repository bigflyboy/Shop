package com.visionin.shop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.visionin.shop.Adapter.GoodPagerAdaper;
import com.visionin.shop.Beans.DNSBean;
import com.visionin.shop.Beans.GoodsBean;
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

    @BindView(R.id.web_view) WebView mWebView;

    @BindView(R.id.viewpager_goods) ViewPager mViewPager;

    private List<View> mViewList;

    private GoodPagerAdaper mGoodPagerAdaper;

    private AsyncHttpServer server;

    private String mToken;

    public DisplayImageOptions mOptions;

    private int mImageSize;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.what == 1) {
                Bundle bundle = msg.getData();
                mToken = bundle.getString("goods_number");
                if(mToken.equals("control_pre")){
                    int currentIndex = mViewPager.getCurrentItem();
                    if(currentIndex>0){
                        mViewPager.setCurrentItem(currentIndex-1);
                    }else{
                        Toast.makeText(getApplicationContext(),"已经是第一页！",Toast.LENGTH_SHORT).show();
                    }
                }else if(mToken.equals("control_next")){
                    int currentIndex = mViewPager.getCurrentItem();
                    if(currentIndex<mImageSize){
                        mViewPager.setCurrentItem(currentIndex+1);
                    }else{
                        Toast.makeText(getApplicationContext(),"已经是最后一页！",Toast.LENGTH_SHORT).show();
                    }
                }else if(mToken.equals("control_exit")){
                    mViewPager.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                }else {
//
//                Intent intent = new Intent(BigScreenActivity.this,GoodPagerActivity.class);
//                intent.putExtras(bundle);
//                server.stop();
//                server=null;
//                startActivity(intent);
                    mOptions = new DisplayImageOptions.Builder()
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .showImageOnFail(R.mipmap.ic_launcher)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();

                    mWebView.setVisibility(View.GONE);
                    mViewPager.setVisibility(View.VISIBLE);

                    request(API_ENUM.GOOD_BY_SCAN, new CallbackForRequest<GoodsBean>() {
                        @Override
                        public void doSuccess(GoodsBean bean) {
                            if (bean.getCode() == 200) {
                                GoodsBean.ModelBean modelBean = bean.getModel().get(0);
                                mImageSize = modelBean.getImage().size();

                                mViewList = new ArrayList();
                                View view0 = View.inflate(BigScreenActivity.this, R.layout.goodpager0, null);

                                TextView goodName = (TextView) view0.findViewById(R.id.pager_goods_name);
                                TextView goodPrice = (TextView) view0.findViewById(R.id.pager_goods_price);
                                TextView goodManu = (TextView) view0.findViewById(R.id.pager_goods_manu);
                                TextView goodRemark = (TextView) view0.findViewById(R.id.pager_goods_remark);
                                goodName.setText(goodName.getText() + modelBean.getGoods_name());
                                goodManu.setText(goodManu.getText() + modelBean.getGoods_manufacturer());
                                goodPrice.setText(goodPrice.getText().toString() + modelBean.getGoods_b_price());
                                goodRemark.setText(goodRemark.getText() + modelBean.getGoods_remark());
                                mViewList.add(view0);

                                for (int i = 0; i < mImageSize; i++) {
                                    View view = View.inflate(BigScreenActivity.this, R.layout.image_pager, null);
                                    ImageView imageView = (ImageView) view.findViewById(R.id.image_pager_list);
                                    ImageLoader.getInstance().displayImage(Config.ImageUrl + bean.getModel().get(0).getImage().get(i).getImage_url(), imageView, mOptions);
                                    mViewList.add(view);
                                }

                                mGoodPagerAdaper = new GoodPagerAdaper(mViewList);
                                mViewPager.setAdapter(mGoodPagerAdaper);

                            }
                        }

                        @Override
                        public void doError(Object object) {

                        }

                        @Override
                        public Map<String, String> getParams() {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("goods_number", mToken);
                            return map;
                        }

                        @Override
                        public API_ENUM getApiEnum() {
                            return API_ENUM.GOOD_BY_SCAN;
                        }
                    });
                }
            } else if (msg.what == 0) {

            }
        }
    };

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
                String token = (String) SharedPreferencesUtils.getParam(BigScreenActivity.this, Config.TOKEN, "");

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
        if (server != null) {
            server.stop();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        startServer();
//// browsing http://localhost:5000 will return Hello!!!
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(Config.HOST + "BigScreen/BigScreen/");

    }

    protected void startServer() {
        server = new AsyncHttpServer();

        server.get("/lockscreen", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {

                String status = request.getQuery().getString("goods_number");
//                Logger.e(status);

                Message msg = Message.obtain();
                msg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("goods_number", status);
                msg.setData(bundle);
                mHandler.sendMessage(msg);

                response.send("ok");

            }
        });

// listen on port 5000
        server.listen(5005);
    }


}
