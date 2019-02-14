package com.example.asus.my.password;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.login.LoginActivity;
import com.example.asus.my.replace.StepOneActivity;
import com.example.asus.my.replace.StepTwoActivity;
import com.example.asus.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EditPasswordActivity extends AppCompatActivity {
    private static final String TAG = "EditPasswordActivity";

    private EditText editText_oldPassword;

    private EditText editText_newPassword;

    private Button button_commit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edit_password);
        /*0.标题栏*/
        setTitle("修改密码");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_replace_step_one_back);
        }
        /*1.布局响应*/
        button_commit=findViewById(R.id.button_commit);
        editText_oldPassword=findViewById(R.id.editText_oldPassword);
        editText_newPassword=findViewById(R.id.editText_newPassword);
        button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword=editText_oldPassword.getText().toString();
                String newPassword=editText_newPassword.getText().toString();
                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                String password=sharedPreferences.getString("user_password","");
                confOldPassword(password,oldPassword,newPassword);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_title,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /*SupportMethod*/
    //验证旧密码
    private void confOldPassword(String password,String oldPassword,String newPassword){
        if(password.equals(oldPassword)){//修改密码
            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
            String user_phone=sharedPreferences.getString("user_phone","");
            doHttpEditPassword(user_phone,newPassword);
        }else {//提示密码错误
            Toast.makeText(EditPasswordActivity.this,"原密码错误！",Toast.LENGTH_SHORT).show();
        }
    }
    //修改新密码
    public void doHttpEditPassword(String user_phone,String newPassword){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("user_phone",user_phone)
                .add("user_password",newPassword).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"updateUserPassword").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                if("true".equals(result)){
                    SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    sharedPreferences.edit()
                            .putString("isLogin", "no")
                            .apply();
                    Intent intent=new Intent(EditPasswordActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(EditPasswordActivity.this,"密码修改失败",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
