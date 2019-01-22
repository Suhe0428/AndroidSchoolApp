package com.example.asus.my.mymarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.myapplication.R;

public class MyMarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_market);
        setTitle("我的交易");
    }
}
