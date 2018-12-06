package com.example.asus.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.album.AlbumActivity;
import com.example.asus.test.Goods;
import com.example.asus.test.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import cn.carbs.android.expandabletextview.library.ExpandableTextView;

public class ItemActivity extends AppCompatActivity{
    MenuItem menuItem=null;

    private static final String TAG = "ItemActivity";

    private ImageView imageView_goods;
    private TextView textView_name;
    private TextView textView_price;
    private TextView textView_place;
    private TextView textView_seller_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // 隐藏系统标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Intent intent=getIntent();
        if(menuItem!=null){
            menuItem.setTitle(intent.getStringExtra("fruitName"));
        }

        //导航项响应
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                NavigationView navigationView=findViewById(R.id.nav_view);
//                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.album:
//                                Intent intent = new Intent(ItemActivity.this, AlbumActivity.class);
//                                startActivity(intent);
//                                break;
//                            default:
//                                break;
//                        }
//                        return true;
//                    }
//                });
//            }
//        }).start();


        //goods_item
        String json=intent.getStringExtra("goods");
        Gson gson =new Gson();
        Goods goods= gson.fromJson(json,Goods.class);

        imageView_goods=findViewById(R.id.imageView_goods);
        imageView_goods.setImageResource(goods.getImageId());

        textView_name=findViewById(R.id.textView_name);
        textView_name.setText(goods.getName());
        textView_price=findViewById(R.id.textView_price);
        textView_price.setText(goods.getPrice());
        textView_place=findViewById(R.id.textView_place);
        textView_place.setText(goods.getPlace());
        textView_seller_phone=findViewById(R.id.textView_seller_phone);
        textView_seller_phone.setText(goods.getSeller());
        //折叠文本框
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
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu){
        getMenuInflater().inflate(R.menu.item_nav_menu, menu);
        menuItem= menu.findItem(R.id.name);
        return true;
    }

}
