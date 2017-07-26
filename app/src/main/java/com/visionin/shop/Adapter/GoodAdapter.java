package com.visionin.shop.Adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;

import java.util.List;

/**
 * Created by wangzhiyuan on 2017/7/26.
 */

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {

    private GoodsBean mGoodList;
    ImageSize mImageSize;
    DisplayImageOptions mOptions;
    String mImageUrl = "http://t00.memoyun.com:8080/BigScreen/Upload/Image.jsp?image=";

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goot_list, parent,false);
        GoodAdapter.ViewHolder holder = new GoodAdapter.ViewHolder(view);
        mImageSize = new ImageSize(100, 100);

        //显示图片的配置
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return holder;
    }

    public GoodAdapter(GoodsBean list){
        mGoodList = list;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GoodsBean.ModelBean good = mGoodList.getModel().get(position);
        GoodsBean.ModelBean.ImageBean imageUrl= good.getImage().get(0);
        ImageLoader.getInstance().displayImage(mImageUrl+imageUrl.getImage_url(), holder.goodImage, mOptions);
        holder.goodName.setText(""+good.getGoods_name());
        holder.goodPrice.setText("商品价格："+good.getGoods_c_price());
        holder.goodManu.setText("商品厂商："+good.getGoods_manufacturer());
        holder.goodRemark.setText("商品备注："+good.getGoods_remark());

    }

    @Override
    public int getItemCount() {
        return mGoodList.getModel().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView goodName,goodPrice,goodManu,goodRemark;
        ImageView goodImage;

        public ViewHolder(View view) {
            super(view);
            goodImage = (ImageView) view.findViewById(R.id.good_image);
            goodName = (TextView) view.findViewById(R.id.good_name);
            goodPrice = (TextView) view.findViewById(R.id.good_price);
            goodManu = (TextView) view.findViewById(R.id.good_manufacturer);
            goodRemark = (TextView) view.findViewById(R.id.good_remark);
        }
    }
}
