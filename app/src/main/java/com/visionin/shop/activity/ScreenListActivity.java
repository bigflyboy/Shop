package com.visionin.shop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScreenListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview_ip) ListView mListView;

    private List<GetDNSBean.ModelBean> mBeanList;

    private IpAdapter mIpAdapter;

    OkHttpClient mOkHttpClient = new OkHttpClient();

    private String mGoodNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_list);
        ButterKnife.bind(this);
//        mListView.setAdapter();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mGoodNumber = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final String url = "http://"+mBeanList.get(position).getIp()+":5005/lockscreen?goods_number="+mGoodNumber;


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
//                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
}
