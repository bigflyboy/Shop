package com.visionin.shop.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.Logger;
import com.visionin.shop.Adapter.FruitAdapter;
import com.visionin.shop.Adapter.GoodAdapter;
import com.visionin.shop.Beans.GetDNSBean;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;
import com.visionin.shop.utils.Config;
import com.visionin.shop.utils.SharedPreferencesUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopperSetActivity extends BaseActivity{


    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;

    private boolean lockScreenFlag = false;

    OkHttpClient mOkHttpClient = new OkHttpClient();

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
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        final String str = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN+"");
        if(requestCode == REQUEST_CODE&&resultCode==RESULT_OK){
            request(API_ENUM.GOOD_BY_SCAN, new CallbackForRequest<GoodsBean>() {
                @Override
                public void doSuccess(GoodsBean bean) {
                    if(bean.getCode()==200){
                        Toast.makeText(getApplicationContext(),bean.getModel().get(0).getGoods_name()+"",Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.shopper_get_goods)
    public void getGoods(View v){
        Intent intent = new Intent(this, ShopperActivity.class);
        startActivity(intent);

    }

    //锁定／解锁大屏
    @OnClick(R.id.shopper_set_lock)
    public void lockScreen(View v){
        if(lockScreenFlag){
            ((TextView)v).setText("锁定大屏");
            sendHTTPToScreen("lockscreen", "true");
        }else {
            ((TextView)v).setText("解锁大屏");
            sendHTTPToScreen("lockscreen", "false");
        }
        lockScreenFlag = !lockScreenFlag;
    }

    private void sendHTTPToScreen(final String target, final String param){
        request(API_ENUM.GET_DNS, new CallbackForRequest<GetDNSBean>() {
            @Override
            public void doSuccess(GetDNSBean bean){
                String ip = bean.getModel().get(0).getIp();
                final String url = "http://"+ip+":5005/"+target;
                Logger.e(url);
                if(target.equals("lockscreen")){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Request request = new Request.Builder()
                                    .url(url)
                                    .header("status",param)
                                    .build();
                            Call call = mOkHttpClient.newCall(request);
                            try {
                                Response response = call.execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }else if(target.equals("")){

                }
            }

            @Override
            public void doError(Object object) {

            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("token",  (String) SharedPreferencesUtils.getParam(getApplicationContext(), Config.TOKEN, ""));
                return map;
            }

            @Override
            public API_ENUM getApiEnum() {
                return API_ENUM.GET_DNS;
            }
        });

    }

}
