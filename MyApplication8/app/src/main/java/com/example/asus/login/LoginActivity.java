package com.example.asus.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;
import com.example.asus.register.RegisterActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    public static final String BaseUrl="http://192.168.1.101:8080/MyAppServer/AppServlet/";

    private  EditText editText_tel;
    private  EditText editText_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //去掉标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //获取登录状态
        if(readLogin()){//已登录
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();//关闭当前登录界面，否则在主界面按后退键还会回到登录界面
        }
        editText_tel=findViewById(R.id.editText_tel);
        editText_password=findViewById(R.id.editText_password);
        //登录（未登录）
        Button button_submit=findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String phone=editText_tel.getText().toString();
                String password=editText_password.getText().toString();
                doPost(phone,password);
            }
        });
        //注册
        Button button_register=findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /*post数据传输*/
    public void doPost(final String tel, final String password){
        //1.okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        FormBody requestBody=new FormBody.Builder()
                .add("user_phone",tel)
                .add("user_password",password).build();
        Request.Builder builder=new Request.Builder();
        Request request = builder.get().url(BaseUrl+"login").post(requestBody).build();
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
                final String result = response.body().string();
                if("yes".equals(result)){
                    writeLogin(tel,password);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("navId",1);
                    startActivity(intent);
                    finish();
                }else if("password is wrong".equals(result)){//密码错误
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("密码错误！");
                        }
                    });
                }else {//用户名错误
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("用户名错误！");
                        }
                    });
                }
            }
        });
    }

    /*显示对话框*/
    public void showDialog(String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_dialog);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    /**
     * 记住登录
     * 1.写入登录信息*/
    public void writeLogin(String phone,String password){
        /*保存信息*/
        //SharedPreferences 保存数据的实现代码
        SharedPreferences sharedPreferences =getSharedPreferences("user", Context.MODE_PRIVATE);
        //如果不能找到Editor接口。尝试使用 SharedPreferences.Editor
        sharedPreferences.edit()
                .putString("user_phone", phone)
                .putString("user_password",password)
                .putString("isLogin","yes")
                .apply();
        //我将用户信息保存到其中，你也可以保存登录状态
    }

    /*2.读取登录信息*/
    public boolean readLogin(){
        /*获取信息*/
        //取sharedpreferences中数据的代码
        boolean isLogin=false;
        SharedPreferences sharedPreferences =getSharedPreferences("user", Context.MODE_PRIVATE);
        String is = sharedPreferences.getString("isLogin", "null");
        if(is.equals("yes")){
            isLogin=true;
        }
        return isLogin;
    }

    /**重写返回键退出程序*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LoginActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
