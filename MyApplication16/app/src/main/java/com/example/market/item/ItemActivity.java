package com.example.market.item;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.myapplication.R;
import com.example.market.main.Goods;
import com.google.gson.Gson;

import cn.carbs.android.expandabletextview.library.ExpandableTextView;

public class ItemActivity extends AppCompatActivity {
    private ImageView imageView_goods;
    private TextView textView_name;
    private TextView textView_price;
    private TextView textView_place;
    private TextView textView_seller_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item);
        /*1.获取数据*/
        Intent intent=getIntent();
        String json=intent.getStringExtra("goods");
        Gson gson =new Gson();
        Goods goods= gson.fromJson(json,Goods.class);
        /*2.设置标题*/
        setTitle(goods.getName());
        /*3.设置内容*/
            /*3.1 图片*/
            imageView_goods=findViewById(R.id.imageView_goods);
            imageView_goods.setImageResource(goods.getImageId());
            /*3.2 名称*/
            textView_name=findViewById(R.id.textView_name);
            textView_name.setText(goods.getName());
            /*3.2 价格*/
            textView_price=findViewById(R.id.textView_price);
            textView_price.setText(goods.getPrice());
            /*3.4 交易地点*/
            textView_place=findViewById(R.id.textView_place);
            textView_place.setText(goods.getPlace());
            /*3.5 联系方式*/
            textView_seller_phone=findViewById(R.id.textView_seller_phone);
            textView_seller_phone.setText(goods.getSeller());
            /**3.6 简介*/
            ExpandableTextView expandableTextView = findViewById(R.id.etv);
            expandableTextView.setText(goods.getInfo());
            expandableTextView.setExpandListener(new ExpandableTextView.OnExpandListener() { //展开收起监听
                @Override
                public void onExpand(ExpandableTextView view) {//展开
                    Log.i("", "展开") ;
                }
                @Override
                public void onShrink(ExpandableTextView view) {//收起
                    Log.i("", "收起") ;
                }
            });
    }

}
