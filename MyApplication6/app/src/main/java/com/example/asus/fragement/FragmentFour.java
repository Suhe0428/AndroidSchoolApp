package com.example.asus.fragement;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.login.LoginActivity;
import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;
import com.example.vo.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentFour extends Fragment {
    private static final String TAG = "FragmentFour";
    public static final String BaseUrl="http://192.168.1.101:8080/MyAppServer/AppServlet/";
    private Button button_exit;
    private CircleImageView circleImageView;
    private TextView textView_name;


    private Handler handler;
    private User user;

    private String tel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_four,container,false);

        handler=new Handler();
        /*1.信息设置*/
        circleImageView=view.findViewById(R.id.imageView_head);
        //circleImageView.setImageResource();//设置头像
        textView_name=view.findViewById(R.id.textView_name);
        if(tel==null){
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            tel=sharedPreferences.getString("user_phone","");
            doPost(tel);
        }else {
            doPost(tel);//设置昵称
        }

        /*2.功能*/


        /*3.用户注销*/
        button_exit=view.findViewById(R.id.button_exit);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                sharedPreferences.edit()
                        .putString("isLogin","no")
                        .apply();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    /*设置昵称*/
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            textView_name.setText(user.getUser_name());
        }
    };
    /*post数据传输*/
    public void doPost(String tel){
        //1.okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        FormBody requestBody=new FormBody.Builder()
                .add("user_phone",tel).build();
        Request.Builder builder=new Request.Builder();
        Request request = builder.get().url(BaseUrl+"getUser").post(requestBody).build();
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
                final String  result=response.body().string();
                Gson gson = new Gson();
                user=gson.fromJson(result,User.class);
                new Thread(){
                    public void run(){
                        handler.post(runnable);
                    }
                }.start();
            }
        });
    }

    /*获取Activity中的数据*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tel=((MainActivity)context).getTitles();
        Log.i(TAG, "onAttach: "+tel);
    }
}
