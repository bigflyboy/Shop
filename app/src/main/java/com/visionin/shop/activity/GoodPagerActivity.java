package com.visionin.shop.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.visionin.shop.Adapter.GoodPagerAdaper;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;
import com.visionin.shop.utils.Config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodPagerActivity extends BaseActivity {

    @BindView(R.id.viewpager_goods) ViewPager mViewPager;

    private List<View> mViewList;

    private GoodPagerAdaper mGoodPagerAdaper;

    private AsyncHttpServer server;

    private String mToken;

    public DisplayImageOptions mOptions;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Bundle bundle = msg.getData();
//                if(bundle.get("ip")=="contorl_pre"){
//
//                }
                Toast.makeText(getApplicationContext(), bundle.getString("ip"),Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_pager);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ButterKnife.bind(this);
        startServer();
        Bundle bundle = getIntent().getExtras();
        mToken = bundle.getString("goods_number");
//        Logger.e(mToken);
//显示图片的配置
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        request(API_ENUM.GOOD_BY_SCAN, new CallbackForRequest<GoodsBean>() {
            @Override
            public void doSuccess(GoodsBean bean) {
                if(bean.getCode()==200){
                    GoodsBean.ModelBean modelBean = bean.getModel().get(0);
                    int imageSize = modelBean.getImage().size();

                    mViewList = new ArrayList();
                    View view0 = View.inflate(GoodPagerActivity.this, R.layout.goodpager0,null);

                    TextView goodName = (TextView) view0.findViewById(R.id.pager_goods_name);
                    TextView goodPrice = (TextView) view0.findViewById(R.id.pager_goods_price);
                    TextView goodManu = (TextView) view0.findViewById(R.id.pager_goods_manu);
                    TextView goodRemark = (TextView) view0.findViewById(R.id.pager_goods_remark);
                    goodName.setText(goodName.getText()+modelBean.getGoods_name());
                    goodManu.setText(goodManu.getText()+modelBean.getGoods_manufacturer());
                    goodPrice.setText(goodPrice.getText().toString()+modelBean.getGoods_b_price());
                    goodRemark.setText(goodRemark.getText()+modelBean.getGoods_remark());
                    mViewList.add(view0);

                    for(int i =0;i<imageSize;i++){
                        View view = View.inflate(GoodPagerActivity.this, R.layout.image_pager,null);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.stop();
    }

    protected void startServer(){
        server = new AsyncHttpServer();

        server.get("/controlscreen", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                String status = request.getQuery().getString("goods_number");
                Logger.e(status);
                Message msg = Message.obtain();
                msg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("status", status);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
                response.send("ok");
            }
        });

// listen on port 5000
        server.listen(5006);
    }
}
