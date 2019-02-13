package com.example.help.search;

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
import com.example.help.main.HelpActivity;
import com.example.market.search.MarketEmptyActivity;
import com.example.market.search.MarketHistory;
import com.example.market.search.MarketHistoryAdapter;
import com.example.market.search.MarketHistoryArray;
import com.example.market.search.MarketResultActivity;
import com.example.market.search.MarketSearchActivity;
import com.example.vo.Goods;
import com.example.vo.Task;
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

public class HelpSearchActivity extends AppCompatActivity {
    private static final String TAG = "HelpSearchActivity";

    private SearchView searchView;

    private Button button_back;

    private Button button_delete;

    private ListView listView_helpHistory;

    private HelpHistory helpHistory;

    private HelpHistoryAdapter helpHistoryAdapter;

    private List<HelpHistory> helpHistoryData = new ArrayList<>();

    public List<HelpHistory> getData() {
        return helpHistoryData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_search);
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
                helpHistoryData.clear();
                HelpHistoryArray.helpHistoryArray.clear();
                addData();
                helpHistoryAdapter.notifyDataSetChanged();
                listView_helpHistory.setAdapter(helpHistoryAdapter);
            }
        });
        /*3.加载历史记录*/
        listView_helpHistory = findViewById(R.id.history_list);
        addData();
        helpHistoryAdapter = new HelpHistoryAdapter(HelpSearchActivity.this, R.layout.market_search_history_list_item, helpHistoryData);
        listView_helpHistory.setAdapter(helpHistoryAdapter);
        /*4.搜索时追加历史记录*/
        setSearch();

    }

    /*SupportMethod*/
    /*1.加载数据源*/
    private void addData(){
        for (int i = 0; i < HelpHistoryArray.helpHistoryArray.size(); i++) {
            helpHistory=new HelpHistory();
            helpHistory.setHelp_history_text(HelpHistoryArray.helpHistoryArray.get(i));
            helpHistoryData.add(helpHistory);
        }
    }

    /*2.搜索操作*/
    private void setSearch() {//搜索监听
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /* 添加历史记录*/
                helpHistoryData.clear();//清空data
                HelpHistoryArray.helpHistoryArray.add(query);//向historyArray里追加记录
                addData();//重新装填数据源data
                helpHistoryAdapter.notifyDataSetChanged();//适配器刷新
                listView_helpHistory.setAdapter(helpHistoryAdapter);//listView加载适配器
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

    /*3.连接后台获取搜索数据*/
    public void doHttpGetResult(String text){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("text", text).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+ "selectTask").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Log.i(TAG, "onResponse: "+result);
                if(result.equals("false")){
                    Intent intent=new Intent(HelpSearchActivity.this,MarketEmptyActivity.class);
                    startActivity(intent);
                }else {
                    List<Task> taskList=new Gson().fromJson(result,new TypeToken<List<Task>>(){}.getType());
                    String taskList_json=new Gson().toJson(taskList);
                    Intent intent = new Intent(HelpSearchActivity.this,HelpResultActivity.class);
                    intent.putExtra("taskList_json",taskList_json);
                    startActivity(intent);
                }

            }
        });

    }
}
