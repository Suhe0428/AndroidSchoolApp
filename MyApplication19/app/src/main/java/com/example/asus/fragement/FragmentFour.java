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

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.login.LoginActivity;
import com.example.asus.login.RenameActivity;
import com.example.asus.login.SignatureActivity;
import com.example.asus.my.mygeneral.MyGeneralActivity;
import com.example.asus.my.myhelp.MyHelpActivity;
import com.example.asus.my.mymarket.MyMarketActivity;
import com.example.asus.my.replace.StepOneActivity;
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

    private CircleImageView circleImageView;

    private TextView textView_name;
    private String tel;
    private User user;
    private Handler handler;

    private TextView textView_signature;
    private String signature;

    private TextView textView_replacePhone;

    private TextView textView_myMarket;

    private TextView textView_myHelp;

    private TextView textView_general;

    private Button button_exit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);

        handler = new Handler();
        /**1.信息设置*/
            //1.1设置头像
            circleImageView = view.findViewById(R.id.imageView_head);
            //circleImageView.setImageResource();//设置头像

            //1.2设置昵称
            textView_name = view.findViewById(R.id.textView_name);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            tel = sharedPreferences.getString("user_phone", "");
            doPostGetUser(tel);

            textView_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),RenameActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            //1.3设置签名
            textView_signature = view.findViewById(R.id.textView_signature);
            SharedPreferences sp=getActivity().getSharedPreferences("user_signature",Context.MODE_PRIVATE);
            if("".equals(signature)){
                signature="null";
            }else{
                signature=sp.getString("signature","设置签名");
            }
            textView_signature.setText(signature);
            textView_signature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignatureActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });


        /*2.功能*/
        textView_replacePhone=view.findViewById(R.id.textView_myInfo);
        textView_replacePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),StepOneActivity.class);
                startActivity(intent);
            }
        });

        textView_myHelp=view.findViewById(R.id.textView_myHelp);
        textView_myHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MyHelpActivity.class);
                startActivity(intent);
            }
        });

        textView_myMarket=view.findViewById(R.id.textView_myBusiness);
        textView_myMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MyMarketActivity.class);
                startActivity(intent);
            }
        });

        textView_general=view.findViewById(R.id.textView_setting);
        textView_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MyGeneralActivity.class);
                startActivity(intent);
            }
        });

        /*3.用户注销*/
        button_exit = view.findViewById(R.id.button_exit);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                sharedPreferences.edit()
                        .putString("isLogin", "no")
                        .apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    /*显示昵称*/
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(null==user.getUser_name()){
                textView_name.setText("设置昵称");
            }else{
                textView_name.setText(user.getUser_name());
            }
        }
    };

    //辅助方法：post数据传输，用于根据tel获取当前用户
    public void doPostGetUser(String tel) {
        //1.okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.填充数据，构造Request
        FormBody requestBody = new FormBody.Builder().add("user_phone", tel).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "findUserByPhone").post(requestBody).build();
        //3.将Request封装为Call，并执行
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(result, User.class);
                new Thread() {
                    public void run() {
                        handler.post(runnable);
                    }
                }.start();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        onCreate(null);
    }
}
