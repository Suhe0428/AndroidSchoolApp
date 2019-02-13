package com.example.asus.my.replace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.login.LoginActivity;
import com.example.asus.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StepTwoActivity extends AppCompatActivity {
    private String old_phone;

    private EditText editText_newPhone;

    private EditText editText_code;

    private Button button_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_step_two);

        setTitle("更换手机号");

        editText_newPhone=findViewById(R.id.editText_newPhone);

        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        old_phone=sharedPreferences.getString("user_phone","");

        button_commit=findViewById(R.id.commit);
        button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_phone=editText_newPhone.getText().toString();
                doHttpUpdatePhone(old_phone,new_phone);
            }
        });


    }

    //更新数据
    public void doHttpUpdatePhone(String old_phone,String new_phone){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("old_phone",old_phone).add("user_phone",new_phone).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"updateUserPhone").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                if("true".equals(result)){
                    Looper.prepare();
                    Toast.makeText(StepTwoActivity.this,"更换手机号成功，请重新登录",Toast.LENGTH_LONG).show();
                    Looper.loop();
                    Intent intent=new Intent(StepTwoActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Looper.prepare();
                    Toast.makeText(StepTwoActivity.this,"更换手机号失败",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });

    }
}
