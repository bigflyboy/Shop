package com.visionin.shop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.visionin.shop.Adapter.Fruit;
import com.visionin.shop.Adapter.FruitAdapter;
import com.visionin.shop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopperActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView mRecyclerView;

    private List<Fruit> mFruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper);
        ButterKnife.bind(this);
        initFruits();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(mFruitList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initFruits(){
        for(int i = 0; i< 2;i++){
            Fruit apple = new Fruit("Apple", R.drawable.ic_launcher);
            mFruitList.add(apple);
            Fruit banana = new Fruit("banana", R.drawable.ic_launcher);
            mFruitList.add(banana);
            Fruit orange = new Fruit("orange", R.drawable.ic_launcher);
            mFruitList.add(orange);
            Fruit pear = new Fruit("pear", R.drawable.ic_launcher);
            mFruitList.add(pear);
            Fruit grape = new Fruit("grape", R.drawable.ic_launcher);
            mFruitList.add(grape);
            mFruitList.add(grape);
            mFruitList.add(grape);
        }
    }
}
