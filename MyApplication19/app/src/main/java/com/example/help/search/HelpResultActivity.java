package com.example.help.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.asus.myapplication.R;
import com.example.help.main.TaskAdapter;
import com.example.market.main.GoodsAdapter;
import com.example.vo.Goods;
import com.example.vo.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpResultActivity extends AppCompatActivity {
    private static final String TAG = "HelpResultActivity";

    private RecyclerView recyclerView;

    private List<Task> taskList;

    private Map<Integer,String> colorMap=new HashMap<>();

    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_result);
        /*标题栏*/
        setTitle("搜索结果");

        /*从Intent中获取启动数据*/
        Intent intent=getIntent();
        String taskList_json=intent.getStringExtra("taskList_json");
        Log.i(TAG, "onCreate: "+taskList_json);
        taskList=new Gson().fromJson(taskList_json,new TypeToken<List<Task>>(){}.getType());

        /*布局数据添加*/
        setRandomColor();
        recyclerView=findViewById(R.id.recyclerView_result);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        taskAdapter=new TaskAdapter(taskList,colorMap);
        recyclerView.setAdapter(taskAdapter);
    }

    /*SupportMethod*/
    //生成随机颜色
    private void setRandomColor(){
        colorMap.put(0,"#F0E68C");
        colorMap.put(1,"#fafafa");
        colorMap.put(2,"#EEE9BF");
        colorMap.put(3,"#CD7054");
        colorMap.put(4,"#CAE1FF");
    }
}
