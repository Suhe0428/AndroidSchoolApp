package com.example.market.search;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.asus.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private Button button_back;
    private Button button_delete;
    private ListView listView;
    private List<History> data = new ArrayList<>();
    private HistoryAdapter historyAdapter;

    public List<History> getData() {
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
                HistoryArray.historyArray.clear();//清空记录
                addData();//加载空数据，获取新的数据源list
                historyAdapter.notifyDataSetChanged();//适配器刷新
                listView.setAdapter(historyAdapter);//lisgtview加载空适配器

            }
        });

        /*3.加载历史记录*/
        listView = findViewById(R.id.history_list);
        addData();
        historyAdapter = new HistoryAdapter(SearchActivity.this, R.layout.market_search_history_list_item, data);
        listView.setAdapter(historyAdapter);

        /*4.搜索时追加历史记录*/
        setSearch();



    }

    //辅助方法：用于添加数据
    private void addData() {//准备数据源
        for (int i = 0; i < HistoryArray.historyArray.size() / 2; i++) {
            History history = new History(HistoryArray.historyArray.get(2 * i));
            data.add(history);
        }
    }

    //辅助方法：用于处理搜索后的记录
    private void setSearch() {//搜索监听
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /**
                 * 添加历史记录
                 */
                data.clear();//清空data
                HistoryArray.historyArray.add(query);//向historyArray里追加记录
                addData();//重新装填数据源data
                historyAdapter.notifyDataSetChanged();//适配器刷新
                listView.setAdapter(historyAdapter);//listview加载适配器
                /**
                 * 传值搜索
                 */

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
