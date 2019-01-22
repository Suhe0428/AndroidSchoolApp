package com.example.asus.my.replace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.myapplication.R;

public class StepOneActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_step_one);
        //设置标题
        setTitle("更换手机号");
        //设置标题返回控件
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_replace_step_one_back);
        }
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
            case R.id.next:
                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                String user_phone=sharedPreferences.getString("user_phone","");
                editText=findViewById(R.id.editText_oldPhone);
                if(editText.getText().toString().equals(user_phone)){
                    Intent intent=new Intent(StepOneActivity.this,StepTwoActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"原手机号错误",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
