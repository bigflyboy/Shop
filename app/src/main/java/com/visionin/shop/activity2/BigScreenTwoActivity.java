package com.visionin.shop.activity2;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.visionin.shop.Adapter.GoodPagerAdaper;
import com.visionin.shop.Adapter.GoodsLVAdapter;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;
import com.visionin.shop.activity.BaseActivity;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;
import com.visionin.shop.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigScreenTwoActivity extends BaseActivity {

    @BindView(R.id.bigscreen_vp) ViewPager mViewPager;
    @BindView(R.id.bigscreen_lv) ListView mListView;

    private List<View> mViewList;
    private GoodPagerAdaper mGoodPagerAdaper;
    private GoodsLVAdapter mGoodsLVAdapter;
    private AsyncHttpServer mServer;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_screen_two);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        mViewList = new ArrayList<>();
        for(int i = 0;i < 6;i++){
            View view = View.inflate(this, R.layout.bsa_vp_item, null);
            WebView webView = (WebView) view.findViewById(R.id.bsi_vp_wv);
            webView.loadUrl(Config.HOST + "BigScreen/BigScreen/");
            mViewList.add(view);
        }
        mGoodPagerAdaper = new GoodPagerAdaper(mViewList);
        mViewPager.setAdapter(mGoodPagerAdaper);

        request(API_ENUM.GOODS_LIST, new CallbackForRequest<GoodsBean>() {
            @Override
            public void doSuccess(GoodsBean bean) {
                if(bean.getCode()==200){
                    mGoodsLVAdapter = new GoodsLVAdapter(bean.getModel());
                    mListView.setAdapter(mGoodsLVAdapter);
                }
            }

            @Override
            public void doError(Object object) {

            }

            @Override
            public Map<String, String> getParams() {
                return null;
            }

            @Override
            public API_ENUM getApiEnum() {
                return API_ENUM.GOODS_LIST;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void startServer(){
        if(mServer==null){
            mServer = new AsyncHttpServer();
        }
        mServer.get("/lockscreen", new HttpServerRequestCallback() {
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

        mServer.listen(5005);
    }
}
