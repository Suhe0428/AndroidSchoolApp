package com.example.help.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.Task;
import com.example.vo.TaskKeyWord;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private static final String TAG = "TaskAdapter";
    //    成员变量
    private Context context;
    private List<Task> taskList;
    private Map<Integer, String> colorMap;

    //    构造函数
    public TaskAdapter(List<Task> taskList, Map<Integer, String> colorMap) {
        this.taskList = taskList;
        this.colorMap = colorMap;
    }

    //    内部静态持有类
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        public TextView textView_taskTitle;

        public TextView textView_taskKeyWord;
        public Handler keyWordHandler;

        public LinearLayout content;

        public TextView textView_taskContent;
        public Handler contentHandler;

        public ImageView imageView_taskImg;

        public TextView textView_taskTime;
        public Handler timeHandler;

        public TextView textView_taskPlace;
        public Handler placeHandler;

        public TextView textView_taskPublishTime;
        public Handler publishTimeHandler;

        public Button button_getTask;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;

            textView_taskTitle = itemView.findViewById(R.id.textView_taskTitle);

            textView_taskKeyWord = itemView.findViewById(R.id.textView_taskKeyWord);
            keyWordHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String keyWord = (String) msg.obj;
                    textView_taskKeyWord.setText(keyWord);
                }
            };

            content = itemView.findViewById(R.id.content);

            textView_taskContent = itemView.findViewById(R.id.textView_taskContent);
            contentHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    String taskContent= (String) msg.obj;
                    textView_taskContent.setText(taskContent);
                }
            };

            textView_taskPlace=itemView.findViewById(R.id.textView_taskPlace);
            placeHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    String taskPlace= (String) msg.obj;
                    textView_taskPlace.setText(taskPlace);
                }
            };

            textView_taskTime=itemView.findViewById(R.id.textView_taskTime);
            timeHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    String taskTime= (String) msg.obj;
                    textView_taskTime.setText(taskTime);
                }
            };

            imageView_taskImg = itemView.findViewById(R.id.imageView_taskImg);

            textView_taskPublishTime = itemView.findViewById(R.id.textView_taskPublishTime);
            publishTimeHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    String publishTime= (String) msg.obj;
                    textView_taskPublishTime.setText(publishTime);
                }
            };

            button_getTask = itemView.findViewById(R.id.button_getTask);
        }
    }

    //    重写
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//加载布局
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.help_main_task, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Task task = taskList.get(position);
        String json = new Gson().toJson(task);
        //1.设置随机背景颜色
        Random random = new Random();
        viewHolder.content.setBackgroundColor(Color.parseColor(colorMap.get(random.nextInt(colorMap.size()))));
        //2.设置内容
        viewHolder.textView_taskTitle.setText(task.getTask_title());//标题

        doHttpGetTaskKeyWord(viewHolder.keyWordHandler,json);//标签

        Message contentMessage=new Message();
        contentMessage.obj=task.getTask_content();
        viewHolder.contentHandler.sendMessage(contentMessage);//内容

        Message placeMessage=new Message();
        placeMessage.obj=task.getTask_place();
        viewHolder.placeHandler.sendMessage(placeMessage);//地点

        Message timeMessage=new Message();
        timeMessage.obj=task.getTask_time();
        viewHolder.timeHandler.sendMessage(timeMessage);//时间

        Message publishTimeMessage=new Message();
        publishTimeMessage.obj=task.getTask_publish_time();
        viewHolder.publishTimeHandler.sendMessage(publishTimeMessage);//发布时间

        //3.设置监听
        viewHolder.button_getTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json=new Gson().toJson(task);
                doHttpUpdateTaskState(json);
                taskList.remove(position);
                TaskAdapter.this.notifyItemRemoved(position);
                notifyDataSetChanged();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(context, "已领取", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    //    Support方法
    //1.获取标签
    public void doHttpGetTaskKeyWord(final Handler handler,String json) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("json", json).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "findTaskKeyWordById").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "onResponse: " + result);
                TaskKeyWord taskKeyWord = new Gson().fromJson(result, TaskKeyWord.class);
                Message message = new Message();
                message.obj = taskKeyWord.getTask_keyword_name();
                handler.sendMessage(message);
            }
        });
    }

    //2.修改task_state
    public void doHttpUpdateTaskState(String json){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("json",json).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"updateTask").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
