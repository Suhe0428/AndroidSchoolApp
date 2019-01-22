package com.example.help.mytask;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.help.main.HelpActivity;
import com.example.help.main.TaskAdapter;
import com.example.help.publish.TaskPublishActivity;
import com.example.market.search.SearchActivity;
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

public class TaskMyActivity extends AppCompatActivity {
    private static final String TAG = "TaskMyActivity";

    private RecyclerView recyclerView;

    private List<Task> taskList=new ArrayList<>();;

    private TaskMyAdapter taskMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_my);
        // 0.标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_help_back);
        }
        setTitle("我的任务");
        // 1.生成布局
        doHttpGetTask();
        recyclerView=findViewById(R.id.recyclerView_my_task);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        taskMyAdapter=new TaskMyAdapter(taskList);
        recyclerView.setAdapter(taskMyAdapter);
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
                Log.i(TAG, "onResponse: "+result);
                List<Task> taskListFromJson=new Gson().fromJson(result,new TypeToken<List<Task>>(){}.getType());
                for (int i=0;i<taskListFromJson.size();i++){
                    Task task=taskListFromJson.get(i);
                    if(task.getTask_state()!=0){
                        taskListFromJson.remove(task);
                        i--;
                    }
                }
                taskList.addAll(taskListFromJson);
            }
        });

    }


    /*重写*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
