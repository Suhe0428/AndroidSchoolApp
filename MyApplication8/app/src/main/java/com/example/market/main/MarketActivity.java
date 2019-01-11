package com.example.market.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarketActivity extends AppCompatActivity {
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private DrawerLayout drawerLayout;
    private Goods[] goods ={
            new Goods(1,"二手包","生活用品","￥25","10010","文理大楼",R.mipmap.img_bag,
                    "专为时装而设计的，具有极强的流行性和时效性，在造型和材料的应用上，强调新潮和时髦，有时就因为材料的缘故而流行，一般情况下时装包造型与材料选用和时装服饰风格相一致。"),
            new Goods(2,"自拍杆","生活用品","￥5","10010","文理大楼",R.mipmap.img_takephoto,
                    "自拍杆是风靡世界的自拍神器，它能够在20厘米到120厘米长度间任意伸缩，使用者只需将手机或者傻瓜相机固定在伸缩杆上，通过遥控器就能实现多角度自拍。"),
            new Goods(3,"耳机","电子设备","￥30","10010","文理大楼",R.mipmap.img_erji,
                    "在不影响旁人的情况下，可独自聆听音响；亦可隔开周围环境的声响，对在录音室、DJ、旅途、运动等在噪吵环境下使用的人很有帮助。"),
            new Goods(4,"16K笔记本","学习用品","￥5","10010","文理大楼",R.mipmap.img_notebook,
                    "人类在没有发明文字时，常用绳索打结的方法来记录数字或事件，表达某种意思，用以传达信息。"),
            new Goods(5,"水杯","生活用品","￥15","10010","文理大楼",R.mipmap.img_cup,
                    "在各国的不同领域和文化中，杯子的形状有个不一样，可以说文化决定形状。水杯也有很多种类，如保温杯，开口杯，环行杯，智能水杯等等"),
            new Goods(6,"二手书","学习用品","￥30","10010","文理大楼",R.mipmap.img_book,
                    "从古到今市场中都不缺二手书的身影，尤其以近年来为最多。因为随着图书印刷出版事业的大踏步的发展，图书面世量越来越大，有好多不合市场销路的或者是图书主人觉得没有价值的图书通过各种渠道流入了市场。"),
            new Goods(7,"手办","其他","￥30","10010","文理大楼",R.mipmap.img_shouban,
                    "特指未上色组装的模型套件，需要玩家自己动手打磨、拼装、上色等一系列复杂的工艺，而且难度远大于一般模型制作，主要材料为树脂。"),};
    private List<Goods> goodsList =new ArrayList<>();
    private GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        /*0.标题栏导航*/
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_market_toolbar_left_white_24dp);
        }

        /*1.搜索*/
        searchView=findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(MainActivity.this, SearchActivity.class);
                //startActivity(intent);
            }
        });

        /*2.布局：卡片布局展示*/
        initFruits();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        goodsAdapter=new GoodsAdapter(goodsList);
        recyclerView.setAdapter(goodsAdapter);

        /*3.下拉刷新：需要函数refreshFruits()*/
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

        /*4.悬浮按钮：跳转到发布页面*/
        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*5.侧滑栏*/
            /*5.1侧滑栏下拉选项*/
            Spinner spinner_classification=findViewById(R.id.spinner_classification);
            spinner_classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String value=parent.getItemAtPosition(position).toString();
                    if(value.equals("请选择分类")==false){
                        Toast.makeText(MarketActivity.this,value,Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            /*5.2侧滑栏提交按钮*/
            Button button=(Button)findViewById(R.id.commit);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MarketActivity.this,"提交",Toast.LENGTH_SHORT).show();
                }
            });
    }

    //辅助方法：用于初始化内容布局展示
    private void initFruits(){
        goodsList.clear();
        for (int i = 0; i < 50; i++) {
            Random random=new Random();
            int index=random.nextInt(goods.length);
            goodsList.add(goods[index]);
        }
    }

    //辅助方法：用于刷新内容布局展示
    private void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        goodsAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    //重写方法：用于按钮启动侧滑栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
}
