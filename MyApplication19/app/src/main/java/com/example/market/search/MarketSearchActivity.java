package com.example.market.search;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.Goods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MarketSearchActivity extends AppCompatActivity {
    private static final String TAG = "MarketSearchActivity";

    private SearchView searchView;
    private Button button_back;
    private Button button_delete;
    private ListView listView;
    private MarketHistory history;
    private List<MarketHistory> data = new ArrayList<>();
    private MarketHistoryAdapter historyAdapter;

    public List<MarketHistory> getData() {
        return data;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_search);
        /*0.隐藏标题栏*/
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        /*1.返回键取消搜索*/
        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*2.清除历史记录*/
        button_delete = findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();//清空数据源
                MarketHistoryArray.marketHistoryArray.clear();//清空记录
                addData();//加载空数据，获取新的数据源list
                historyAdapter.notifyDataSetChanged();//适配器刷新
                listView.setAdapter(historyAdapter);//listView加载空适配器

            }
        });
        /*3.加载历史记录*/
        listView = findViewById(R.id.history_list);
        addData();
        historyAdapter = new MarketHistoryAdapter(MarketSearchActivity.this, R.layout.market_search_history_list_item, data);
        listView.setAdapter(historyAdapter);
        /*4.搜索时追加历史记录*/
        setSearch();
    }

    //辅助方法：用于添加数据
    private void addData() {//准备数据源
        for (int i = 0; i < MarketHistoryArray.marketHistoryArray.size(); i++) {
            history=new MarketHistory();
            history.setHistoryText(MarketHistoryArray.marketHistoryArray.get(i));
            data.add(history);
        }
    }

    //辅助方法：用于处理搜索后的记录
    private void setSearch() {//搜索监听
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /* 添加历史记录*/
                data.clear();//清空data
                MarketHistoryArray.marketHistoryArray.add(query);//向historyArray里追加记录
                addData();//重新装填数据源data
                historyAdapter.notifyDataSetChanged();//适配器刷新
                listView.setAdapter(historyAdapter);//listView加载适配器
                /* 传值搜索*/
                doHttpGetResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //支撑函数：连接后台获取搜索内容
    public void doHttpGetResult(String text){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("text", text).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+ "selectGoods").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Log.i(TAG, "onResponse: "+result);
                if(result.equals("false")){
                    Intent intent=new Intent(MarketSearchActivity.this,MarketEmptyActivity.class);
                    startActivity(intent);
                }else {
                    List<Goods> goodsList=new Gson().fromJson(result,new TypeToken<List<Goods>>(){}.getType());
                    for(Goods goods:goodsList){
                        Log.i(TAG, "onResponse: "+goods.getGoods_name());
                    }
                    String goodsList_json=new Gson().toJson(goodsList);
                    Intent intent = new Intent(MarketSearchActivity.this,MarketResultActivity.class);
                    intent.putExtra("goodsList_json",goodsList_json);
                    startActivity(intent);
                }

            }
        });

    }

}
