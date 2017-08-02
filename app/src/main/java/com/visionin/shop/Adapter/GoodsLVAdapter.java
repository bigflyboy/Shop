package com.visionin.shop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;

import java.util.List;

/**
 * Created by wangzhiyuan on 2017/8/2.
 */

public class GoodsLVAdapter extends BaseAdapter {

    private List<GoodsBean.ModelBean> mModelBeanList;

    public GoodsLVAdapter(List<GoodsBean.ModelBean> list){
        mModelBeanList = list;
    }

    @Override
    public int getCount() {
        return mModelBeanList.size();
    }

    @Override
    public GoodsBean.ModelBean getItem(int position) {
        return mModelBeanList.get(position);
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
        textView.setText(getItem(position).getGoods_name());
        return view;
    }
}
