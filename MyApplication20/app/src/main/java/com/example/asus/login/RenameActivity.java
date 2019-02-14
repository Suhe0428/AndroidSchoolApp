package com.example.asus.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RenameActivity extends AppCompatActivity {
    private static final String TAG = "RenameActivity";

    private EditText editText_userName;
    private Button button_userName_save;

    private String user_phone;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename);
        setTitle("修改昵称");
        /*1.获取编辑框内数据*/
        editText_userName=findViewById(R.id.editText_usrName);

        /*2.保存按钮监听*/
        button_userName_save=findViewById(R.id.button_userName_save);
        button_userName_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                user_phone = sharedPreferences.getString("user_phone", "");

                user_name=editText_userName.getText().toString();
                Log.i(TAG, "onClick: "+user_name);

                doPostSetUserName(user_phone,user_name);

                SharedPreferences.Editor editor=
                        getSharedPreferences("user_name",Context.MODE_PRIVATE).edit();
                editor.putString("user_name",user_name);
                editor.apply();
                Intent intent = new Intent(RenameActivity.this,MainActivity.class);
                intent.putExtra("navId",4);
                startActivity(intent);
                finish();
            }
        });

    }

    //辅助方法：post数据传输,设置用户昵称
    public void doPostSetUserName(String user_phone,String user_name){
        //1.okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        FormBody requestBody = new FormBody.Builder()
                .add("user_phone",user_phone)
                .add("user_name", user_name).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(BaseUrl.BASE_URL + "updateUserName").post(requestBody).build();
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

            }
        });
    }

    //重写返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(RenameActivity.this,MainActivity.class);
            intent.putExtra("navId",4);
            startActivity(intent);
            finish();
        }
        return true;
    }

}
