package com.example.market.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.myapplication.R;

public class MarketEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_empty);
        /*标题栏*/
        setTitle("搜索结果");
    }
}
