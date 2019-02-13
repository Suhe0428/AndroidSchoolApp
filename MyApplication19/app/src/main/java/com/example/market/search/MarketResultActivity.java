package com.example.market.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.asus.myapplication.R;
import com.example.market.main.GoodsAdapter;
import com.example.vo.Goods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MarketResultActivity extends AppCompatActivity {
    private static final String TAG = "MarketResultActivity";

    private RecyclerView recyclerView;

    private List<Goods> goodsList=new ArrayList<>();

    private GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        /*标题栏*/
        setTitle("搜索结果");

        /*从Intent中获取启动数据*/
        Intent intent=getIntent();
        String goodsList_json=intent.getStringExtra("goodsList_json");
        Log.i(TAG, "onCreate: "+goodsList_json);
        goodsList=new Gson().fromJson(goodsList_json,new TypeToken<List<Goods>>(){}.getType());

        /*布局数据添加*/
        recyclerView=findViewById(R.id.recyclerView_result);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        goodsAdapter=new GoodsAdapter(goodsList);
        recyclerView.setAdapter(goodsAdapter);
    }
}
