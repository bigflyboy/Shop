package com.visionin.shop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.activity.CaptureActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.visionin.shop.Beans.GoodsBean;
import com.visionin.shop.R;
import com.visionin.shop.activity.GoodListActivity;
import com.visionin.shop.callback.ItemClickListener;
import com.visionin.shop.utils.Config;

import java.util.List;

/**
 * Created by wangzhiyuan on 2017/7/26.
 */

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> implements ItemClickListener{

    private List<GoodsBean.ModelBean> mGoodList;
    private ImageSize mImageSize;
    private DisplayImageOptions mOptions;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goot_list, parent,false);

        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        cardView.setRadius(5);
        cardView.setCardElevation(5);

        cardView.setContentPadding(2,2,2,2);

        GoodAdapter.ViewHolder holder = new GoodAdapter.ViewHolder(view, this);

        mContext = parent.getContext();


        mImageSize = new ImageSize(100, 100);

        //显示图片的配置
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return holder;
    }

    public GoodAdapter(List<GoodsBean.ModelBean> list){
        mGoodList = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsBean.ModelBean good = mGoodList.get(position);
        if(good.getImage()!=null&&good.getImage().size()>0){
            GoodsBean.ModelBean.ImageBean imageUrl= good.getImage().get(0);
            ImageLoader.getInstance().displayImage(Config.ImageUrl+imageUrl.getImage_url(), holder.goodImage, mOptions);
        }

        holder.goodName.setText(""+good.getGoods_name());
        holder.goodPrice.setText("商品价格："+good.getGoods_c_price());
        holder.goodManu.setText("商品厂商："+good.getGoods_manufacturer());
        holder.goodRemark.setText("商品备注："+good.getGoods_remark());

    }

    @Override
    public int getItemCount() {
        return mGoodList.size();
    }

    @Override
    public void onItemClick(View view, int position) {
        if(mGoodList.get(position)!=null){
            String resultString = mGoodList.get(position).getGoods_number();
            Intent resultIntent = new Intent(mContext, GoodListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN, resultString);

            resultIntent.putExtras(bundle);
            mContext.startActivity(resultIntent);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView goodName,goodPrice,goodManu,goodRemark;
        ImageView goodImage;

        private ItemClickListener mItemClickListener;

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            mItemClickListener = itemClickListener;
            goodImage = (ImageView) view.findViewById(R.id.good_image);
            goodName = (TextView) view.findViewById(R.id.good_name);
            goodPrice = (TextView) view.findViewById(R.id.good_price);
            goodManu = (TextView) view.findViewById(R.id.good_manufacturer);
            goodRemark = (TextView) view.findViewById(R.id.good_remark);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getPosition());
        }
    }
}
