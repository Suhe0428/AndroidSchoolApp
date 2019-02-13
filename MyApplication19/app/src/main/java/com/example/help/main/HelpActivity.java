package com.example.help.main;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.help.mytask.TaskMyActivity;
import com.example.help.publish.TaskPublishActivity;
import com.example.help.search.HelpSearchActivity;
import com.example.market.search.MarketSearchActivity;
import com.example.vo.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HelpActivity extends AppCompatActivity {
    private static final String TAG = "HelpActivity";
    //成员变量
    private RecyclerView recyclerView;

    private List<Task> taskList=new ArrayList<>();;

    private TaskAdapter taskAdapter;

    private Map<Integer,String> colorMap=new HashMap<>();

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        // 0.标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_help_back);
        }
        // 1.生成布局
        doHttpGetTask();
        setRandomColor();
        recyclerView=findViewById(R.id.recyclerView_help);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        taskAdapter=new TaskAdapter(taskList,colorMap);
        recyclerView.setAdapter(taskAdapter);
        //2.下拉刷新
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_help);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTask();
            }
        });

    }

    /*Support方法*/
    //1.获取数据
    public void doHttpGetTask(){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"queryAllTask").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                List<Task> taskListFromJson=new Gson().fromJson(result,new TypeToken<List<Task>>(){}.getType());
                for (int i=0;i<taskListFromJson.size();i++){
                    Task task=taskListFromJson.get(i);
                    if(task.getTask_state()!=-1){
                        taskListFromJson.remove(task);
                        i--;
                    }
                }
                taskList.addAll(taskListFromJson);
            }
        });

    }

    //2.生成随机颜色
    private void setRandomColor(){
        colorMap.put(0,"#F0E68C");
        colorMap.put(1,"#fafafa");
        colorMap.put(2,"#EEE9BF");
        colorMap.put(3,"#CD7054");
        colorMap.put(4,"#CAE1FF");
    }

    //3.刷新
    private void refreshTask(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Collections.shuffle(taskList);
                        taskAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /*重写方法*/
    //1.加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_main_title,menu);
        return true;
    }
    //2.菜单响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.task_search:
                Intent intent0=new Intent(HelpActivity.this,HelpSearchActivity.class);
                startActivity(intent0);
                break;
            case R.id.task_myTask:
                Intent intent1=new Intent(HelpActivity.this,TaskMyActivity.class);
                startActivity(intent1);
                break;
            case R.id.task_publish:
                Intent intent2=new Intent(HelpActivity.this,TaskPublishActivity.class);
                startActivity(intent2);
                break;
            default:
        }
        return true;
    }
}
