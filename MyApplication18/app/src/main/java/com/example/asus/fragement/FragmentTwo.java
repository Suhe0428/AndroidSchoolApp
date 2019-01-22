package com.example.asus.fragement;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asus.baseurl.BaseUrl;
import com.example.vo.Post;
import com.example.asus.community.post.PostAdapter;
import com.example.asus.myapplication.R;
import com.example.vo.User;
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

public class FragmentTwo extends Fragment {
    private static final String TAG = "FragmentTwo";
    
    private List<Post> postList;

    private ListView listView_community;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_two,null);
        postList =new ArrayList<>();
        listView_community=view.findViewById(R.id.listView_community);
        initCommunity();
        listView_community.setAdapter(new PostAdapter(getActivity(), postList));
        return view;
    }

    //辅助方法：初始化布局
    private void initCommunity(){
        doHttpGetPost();
    }

    //辅助方法：获得post
    public void doHttpGetPost(){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody requestBody = new FormBody.Builder().build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"queryAllPost").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Gson gson=new Gson();
                List<Post> postListFromJson=gson.fromJson(result,new TypeToken<List<Post>>(){}.getType());
                for (Post post:postListFromJson){
                    doHttpGetUserName(post,Integer.toString(post.getPost_source_id()));
                    postList.add(post);
                }
            }
        });
    }

    //辅助方法：用于获取指定卖家的昵称，并保存
    public void  doHttpGetUserName(final Post post, String post_source_id){
        //1.okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        FormBody requestBody = new FormBody.Builder()
                .add("user_id", post_source_id).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(BaseUrl.BASE_URL + "findUserById").post(requestBody).build();
        //3.将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4.执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson=new Gson();
                User user=gson.fromJson(result,User.class);
                Log.i(TAG, "onResponse: "+user.getUser_name());
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("post_source_names",Context.MODE_PRIVATE);
                sharedPreferences.edit().putString(""+post.getPost_id(),user.getUser_name()).apply();
            }
        });

    }




}