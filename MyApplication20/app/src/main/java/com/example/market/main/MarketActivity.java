package com.example.market.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.market.search.MarketEmptyActivity;
import com.example.market.search.MarketResultActivity;
import com.example.market.search.MarketSearchActivity;
import com.example.market.upload.UploadActivity;
import com.example.vo.Goods;
import com.example.vo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MarketActivity extends AppCompatActivity {
    private static final String TAG = "MarketActivity";

    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private DrawerLayout drawerLayout;
    private List<Goods> goodsList = new ArrayList<>();
    private GoodsAdapter goodsAdapter;


    private String goods_classification;
    private String goods_price;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.market_main_title, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        /*0.标题栏导航*/
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_market_toolbar_left_white);
        }
        setTitle("二手市场");

        /*1.搜索
         * 利用菜单*/

        /*2.布局：卡片布局展示*/
        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        goodsAdapter = new GoodsAdapter(goodsList);
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
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        /*5.侧滑栏*/
        /*5.1侧滑栏下拉选项*/
        Spinner spinner_classification = findViewById(R.id.spinner_classification);
        Spinner spinner_price = findViewById(R.id.spinner_price);
        spinner_classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                goods_classification=value;
                if (!value.equals("请选择分类")) {
                    Toast.makeText(MarketActivity.this, value, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                goods_price=value;
                if (!value.equals("请选择价格")) {
                    Toast.makeText(MarketActivity.this, value, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*5.2侧滑栏提交按钮*/
        Button button = findViewById(R.id.commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                doHttpSelectGoodsByCondition(goods_classification,goods_price);
            }
        });
    }

    /*SupportMethod*/
    //1.获取所有Goods
    public void doHttpGetAllGoods() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final FormBody formBody = new FormBody.Builder().build();
        final Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "queryAllGoods").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    Gson gson = new Gson();
                    List<Goods> goodsList_fromJson = gson.fromJson(result, new TypeToken<List<Goods>>() {
                    }.getType());
                    for (Goods goods : goodsList_fromJson) {
                        doPostGetUserName(goods, Integer.toString(goods.getGoods_seller_id()));
                        goodsList.add(goods);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //2.用于获取指定卖家的昵称，并保存
    public void doPostGetUserName(final Goods goods, String goods_seller_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody requestBody = new FormBody.Builder()
                .add("user_id", goods_seller_id).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(BaseUrl.BASE_URL + "findUserById").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                User user = gson.fromJson(result, User.class);
                SharedPreferences sharedPreferences = getSharedPreferences("goods_seller_names", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("" + goods.getGoods_id(), user.getUser_name()).apply();
            }
        });

    }

    //3.用于初始化内容布局展示
    private void initFruits() {
        doHttpGetAllGoods();
    }

    //4.用于刷新内容布局展示
    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Collections.shuffle(goodsList);
                        goodsAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    //5.筛选查询
    public void doHttpSelectGoodsByCondition(String goods_classification,String goods_price){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("goods_classification",goods_classification)
                .add("goods_price",goods_price).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"selectGoodsByCondition").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                if(result.equals("[]")){
                    Intent intent=new Intent(MarketActivity.this,MarketEmptyActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MarketActivity.this, MarketResultActivity.class);
                    intent.putExtra("goodsList_json", result);
                    startActivity(intent);
                }
            }
        });
    }

    /*OverrideMethod*/
    //1.菜单点击响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Intent intent = new Intent(MarketActivity.this, MarketSearchActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

}
