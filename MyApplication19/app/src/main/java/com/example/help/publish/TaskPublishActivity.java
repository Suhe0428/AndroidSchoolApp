package com.example.help.publish;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.Task;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskPublishActivity extends AppCompatActivity {
    private static final String TAG = "TaskPublishActivity";
    /*成员变量*/
    private EditText editText_taskTitle;
    private Spinner spinner_taskKeyword;
    private String task_keyword;
    private int keyword_id;
    private EditText editText_taskContent;
    private EditText editText_taskPlace;
    private EditText editText_taskTime;

    private Button button_publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_publish);
        //0.标题栏样式
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_help_back);
        }
        setTitle("发布");

        editText_taskTitle=findViewById(R.id.editText_taskTitle);
        spinner_taskKeyword=findViewById(R.id.spinner_taskKeyword);
        editText_taskContent=findViewById(R.id.editText_taskContent);
        editText_taskPlace=findViewById(R.id.editText_taskPlace);
        editText_taskTime=findViewById(R.id.editText_taskTime);
        button_publish=findViewById(R.id.button_publish);

        button_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task_title=editText_taskTitle.getText().toString();
                spinner_taskKeyword.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        task_keyword=parent.getItemAtPosition(position).toString();
                        Log.i(TAG, "onClick: "+task_keyword);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                String task_content=editText_taskContent.getText().toString();
                String task_place=editText_taskPlace.getText().toString();
                String task_time=editText_taskTime.getText().toString();

//                task.setTask_id(0);//ID
//                task.setTask_title(task_title);//标题
//                task.setTask_keyword_id(keyword_id);//标签
//                task.setTask_state(-1);//状态
//                task.setTask_user_id(1);//userID
//                task.setTask_user_phone("");
//                task.setTask_executor_id(2);//executorID
//                task.setTask_executor_phone("");
//                task.setTask_time(task_time);//时间
//                task.setTask_place(task_place);//地点
//                task.setTask_content(task_content);//内容
//                task.setTask_img("");
//                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                String task_publish_time=simpleDateFormat.format(new Date());
//                task.setTask_publish_time(task_publish_time);//发布时间


            }
        });

    }

    /*Support*/
    //添加新任务
    public void doHttpAddNewTask(String json){

    }

    /*重写*/
    //标题栏返回
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
