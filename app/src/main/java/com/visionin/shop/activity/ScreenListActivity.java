package com.visionin.shop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.activity.CaptureActivity;
import com.orhanobut.logger.Logger;
import com.visionin.shop.Beans.GetDNSBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;
import com.visionin.shop.utils.Config;
import com.visionin.shop.utils.SharedPreferencesUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScreenListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview_ip) ListView mListView;
    @BindView(R.id.linear_control) LinearLayout mLinearLayout;
    @BindView(R.id.control_pre) Button mButtonPre;
    @BindView(R.id.control_next) Button mButtonNext;
    @BindView(R.id.control_exit) Button mButtonExit;

    private List<GetDNSBean.ModelBean> mBeanList;

    private IpAdapter mIpAdapter;

    OkHttpClient mOkHttpClient = new OkHttpClient();

//    private String mGoodNumber;

    private String mIp;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                mListView.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_list);
        ButterKnife.bind(this);
//        mListView.setAdapter();
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        mGoodNumber = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
        request(API_ENUM.GET_DNS, new CallbackForRequest<GetDNSBean>() {
            @Override
            public void doSuccess(GetDNSBean bean){
                mBeanList= bean.getModel();
                mIpAdapter = new IpAdapter(mBeanList);
                mListView.setAdapter(mIpAdapter);
                mListView.setOnItemClickListener(ScreenListActivity.this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

//        final String url = "http://"+mBeanList.get(position).getIp()+":5005/lockscreen?goods_number="+mGoodNumber;

        mIp = mBeanList.get(position).getIp();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
////                Logger.e(mGoodNumber);
//                Call call = mOkHttpClient.newCall(request);
//                try {
//                    Response response = call.execute();
//                    String status = response.body().string();
//                    Logger.e(status);
//                    if(status.equals("ok")){
//                        Logger.e("response.body().string()==\"ok\"");
//                        Message msg = new Message();
//                        msg.what = 1;
//                        mHandler.sendMessage(msg);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
        Bundle bundle = new Bundle();
        bundle.putString("ip", mIp);
        Intent mIntent = new Intent();
        mIntent.putExtras(bundle);
        setResult(RESULT_OK, mIntent);
        finish();

    }

    public static class IpAdapter extends BaseAdapter{

        private List<GetDNSBean.ModelBean> mBeanList;

        public IpAdapter(List<GetDNSBean.ModelBean> list){
            mBeanList = list;
        }

        @Override
        public int getCount() {
            return mBeanList.size();
        }

        @Override
        public GetDNSBean.ModelBean getItem(int position) {
            return mBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);

            }else{
                view = convertView;
            }
            TextView textView = (TextView) view.findViewById(R.id.item_id);
            textView.setText(getItem(position).getIp());
            return view;
        }
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
        mLinearLayout.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
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
