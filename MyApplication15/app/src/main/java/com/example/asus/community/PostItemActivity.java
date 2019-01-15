package com.example.asus.community;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostItemActivity extends AppCompatActivity {
    private static final String TAG = "PostItemActivity";
    private Post post;
    private User user;
    private TextView textView_post_source_name;
    private TextView textView_post_content;
    private ImageView imageView;
    private Handler textHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String post_source_name= (String)msg.obj;
            textView_post_source_name.setText(post_source_name);
        }
    };
    private Bitmap bitmap;
    private Handler imgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            bitmap = (Bitmap) msg.obj;
            imageView.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);
        Intent intent=getIntent();
        int post_id= intent.getIntExtra("post_id",1);
        doHttpGetPostById(Integer.toString(post_id));

    }

    //辅助方法：根据ID获取post
    public void doHttpGetPostById(String post_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("post_id", post_id).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "findPostById").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                post=gson.fromJson(result,Post.class);
                //设置发帖人
                textView_post_source_name=findViewById(R.id.textView_post_source_name);
                doHttpGetPostSourceName(post.getPost_source_phone());

                //贴文
                textView_post_content=findViewById(R.id.textView__post_content);
                textView_post_content.setText(post.getPost_content());

                //贴图
                imageView=findViewById(R.id.imageView_post_content_img);
                doHttpGetPostImg(post.getPost_content_img());
            }
        });
    }

    //辅助方法：获取图片资源
    public void doHttpGetPostImg(String post_content_img) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(BaseUrl.IMG_BASE_URL + post_content_img).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message message = new Message();
                message.obj = bitmap;
                imgHandler.sendMessage(message);
            }
        });
    }

    //辅助方法：获取post所属用户昵称
    public void doHttpGetPostSourceName(String post_source_phone) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("user_phone", post_source_phone).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "findUserByPhone").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Gson gson=new Gson();
                user=gson.fromJson(result,User.class);
                Log.i(TAG, "onResponse: "+user.getUser_name());
                Message message=new Message();
                message.obj=user.getUser_name();
                textHandler.sendMessage(message);
            }
        });
    }
}
