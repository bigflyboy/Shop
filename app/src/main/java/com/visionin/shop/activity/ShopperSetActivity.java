package com.visionin.shop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.visionin.shop.Adapter.FruitAdapter;
import com.visionin.shop.Adapter.GoodAdapter;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopperSetActivity extends BaseActivity {

    @BindView(R.id.recycler_goods) RecyclerView mRecyclerView;

    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_set);

        ButterKnife.bind(this);
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bigscreenset, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_scan:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick(R.id.shopper_get_goods)
    public void getGoods(){
        request(API_ENUM.GOODS_LIST, new CallbackForRequest<GoodsBean>() {

            @Override
            public void doSuccess(GoodsBean bean) {
                Toast.makeText(getApplicationContext(),bean.getCode()+"",Toast.LENGTH_SHORT).show();
                if(bean.getCode()==200){
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ShopperSetActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    GoodAdapter adapter = new GoodAdapter(bean);
                    mRecyclerView.setAdapter(adapter);
                }

//                Toast.makeText(getApplicationContext(),bean.getModel().get(0).getGoods_name(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doError(Object object) {
                Toast.makeText(getApplicationContext(),"网络请求失败！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("offset", "0");
                map.put("limit", "16");
                return map;
            }

            @Override
            public API_ENUM getApiEnum() {
                return API_ENUM.GOODS_LIST;
            }
        });
    }

}
