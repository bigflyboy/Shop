package com.visionin.shop.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.visionin.shop.Adapter.EndlessRecyclerOnScrollListener;
import com.visionin.shop.Adapter.Fruit;
import com.visionin.shop.Adapter.GoodAdapter;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopperActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recycler_goods) RecyclerView mRecyclerView;

    @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    private List<GoodsBean.ModelBean> mGoodList = new ArrayList<>();

    private GoodAdapter mAdapter;

    private static final String TAG = "ShopperActivity";

    private int offset = 0;
    private int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ShopperActivity.this);
//                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new GoodAdapter(mGoodList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        request(API_ENUM.GOODS_LIST, new CallbackForRequest<GoodsBean>() {

            @Override
            public void doSuccess(GoodsBean bean) {
//                Toast.makeText(getApplicationContext(),bean.getCode()+"",Toast.LENGTH_SHORT).show();
                if(bean.getCode()==200){
                    mGoodList.addAll(bean.getModel());
                    mAdapter.notifyDataSetChanged();
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
                map.put("offset", offset+"");
                map.put("limit", limit+"");
                offset+=limit;
                return map;
            }

            @Override
            public API_ENUM getApiEnum() {
                return API_ENUM.GOODS_LIST;
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void loadMoreData(){
        request(API_ENUM.GOODS_LIST, new CallbackForRequest<GoodsBean>() {

            @Override
            public void doSuccess(GoodsBean bean) {
//                Toast.makeText(getApplicationContext(),bean.getCode()+"",Toast.LENGTH_SHORT).show();
                if(bean.getCode()==200){
                    mGoodList.addAll(bean.getModel());
                    mAdapter.notifyDataSetChanged();
                    Logger.e("loadMoreData()..doSuccess()");
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
                map.put("offset", offset+"");
                map.put("limit", limit+"");
                offset+=limit;
                return map;
            }

            @Override
            public API_ENUM getApiEnum() {
                return API_ENUM.GOODS_LIST;
            }
        });
    }


}
