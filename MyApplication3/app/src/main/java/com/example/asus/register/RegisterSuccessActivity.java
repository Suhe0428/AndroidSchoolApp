package com.example.asus.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;
import com.example.asus.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        final Intent intent=new Intent(RegisterSuccessActivity.this, LoginActivity.class); //你要转向的Activity Timer timer = new Timer();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override public void run() {
                startActivity(intent); //执行
                finish();
            }
        };
        timer.schedule(task, 1000 * 3);


    }
}
