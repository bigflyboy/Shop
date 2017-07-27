package com.visionin.shop.activity;

import android.content.Intent;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.visionin.shop.Adapter.Good;
import com.visionin.shop.Adapter.GoodAdapter;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodListActivity extends BaseActivity {

    @BindView(R.id.recycler_good) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list);
        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String str = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN+"");
        request(API_ENUM.GOOD_BY_SCAN, new CallbackForRequest<GoodsBean>() {
            @Override
            public void doSuccess(GoodsBean bean) {
                if(bean.getCode()==200){

                }
            }

            @Override
            public void doError(Object object) {

            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("goods_number", str);
                return map;
            }

            @Override
            public API_ENUM getApiEnum() {
                return API_ENUM.GOOD_BY_SCAN;
            }
        });
    }
}
