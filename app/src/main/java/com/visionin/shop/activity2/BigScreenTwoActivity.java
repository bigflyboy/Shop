package com.visionin.shop.activity2;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;

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
}
