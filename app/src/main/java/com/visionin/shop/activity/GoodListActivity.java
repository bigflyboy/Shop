package com.visionin.shop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.visionin.shop.Adapter.Good;
import com.visionin.shop.Adapter.GoodAdapter;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;
import com.visionin.shop.utils.Config;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodListActivity extends BaseActivity {

    @BindView(R.id.image_good) ImageView mImageGood;
    @BindView(R.id.display_good) Button mButton;
    @BindView(R.id.image_list) LinearLayout mLinearImage;

    public DisplayImageOptions mOptions;

    public static final int REQUEST_CODE = 432;

    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        token = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
        final String str = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);


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
//                    goodBean = bean.getModel().get(0);
                    String url = Config.ImageUrl + bean.getModel().get(0).getImage().get(0).getImage_url();
                    ImageLoader.getInstance().displayImage(url, mImageGood, mOptions);

                    if(bean.getModel().get(0).getImage().size()>1){
                        for(int i = 1;i<bean.getModel().get(0).getImage().size();i++){
                            ImageView imageView = new ImageView(GoodListActivity.this);
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            mLinearImage.addView(imageView);
                            ImageLoader.getInstance().displayImage(Config.ImageUrl + bean.getModel().get(0).getImage().get(i).getImage_url(), imageView, mOptions);
                        }
                    }
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

    @OnClick(R.id.display_good)
    public void display(View v){
        Intent intent = new Intent(this, ScreenListActivity.class);
        Bundle bundle = new Bundle();
//        if(goodBean!=null){
        bundle.putString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN, token);
//        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
