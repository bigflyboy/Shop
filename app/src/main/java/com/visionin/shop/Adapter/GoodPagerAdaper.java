package com.visionin.shop.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.visionin.shop.Beans.GoodsBean;

import java.util.List;

/**
 * Created by wangzhiyuan on 2017/7/31.
 */

public class GoodPagerAdaper extends PagerAdapter {

    private List<View> mViewList;

    public GoodPagerAdaper(List<View> views){
        mViewList = views;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }


}
