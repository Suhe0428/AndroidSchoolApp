package com.example.asus.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";

    private EditText editText_phone;
    private EditText editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("注册");

        editText_phone = findViewById(R.id.editText_phone);
        editText_password = findViewById(R.id.editText_password);

        final Button button_register = findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取填写的信息
                String phone = editText_phone.getText().toString();
                String password = editText_password.getText().toString();
                if ("".equals(password)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("密码不能为空");
                        }
                    });
                } else {
                    doPost(phone, password);
                }
            }
        });

    }

    /*post数据传输*/
    public void doPost(String tel, String password) {
        //1.okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        FormBody requestBody = new FormBody.Builder()
                .add("user_phone", tel)
                .add("user_password", password).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(BaseUrl.BASE_URL + "register").post(requestBody).build();
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
                Log.i(TAG, "onResponse: " + result);
                if ("register successfully".equals(result)) {//注册成功
                    Intent intent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
                    startActivity(intent);
                    finish();
                } else {//账号已存在
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("账号已存在");
                        }
                    });
                }
            }
        });
    }

    /*显示对话框*/
    public void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_login_dialog);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
