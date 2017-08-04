package com.visionin.shop.activity2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.visionin.shop.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LabelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.la_tv_shot,R.id.la_tv_niuzai,R.id.la_tv_pibao,R.id.la_tv_shangyi,R.id.la_tv_xiuxian,R.id.la_tv_yundong})
    public void swicthLabel(View v){
        finish();
    }

}
