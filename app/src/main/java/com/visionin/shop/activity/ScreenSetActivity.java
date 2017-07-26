package com.visionin.shop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.visionin.shop.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenSetActivity extends AppCompatActivity {

    @OnClick(R.id.bigset_start)
    public void startBigScreen(View v){
        Intent intent = new Intent(this, BigScreenActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_set);
        ButterKnife.bind(this);
    }
}
