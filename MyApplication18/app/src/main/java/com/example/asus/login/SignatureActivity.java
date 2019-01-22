package com.example.asus.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.fragement.FragmentFour;
import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;

public class SignatureActivity extends AppCompatActivity {
    private static final String TAG = "SignatureActivity";
    private EditText editText_signature;
    private Button button_save;

    private String signature;

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SignatureActivity.this,MainActivity.class);
        intent.putExtra("navId",4);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        setTitle("设置签名");

        /*保存签名*/
        editText_signature=findViewById(R.id.editText_signature);
        button_save=findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature=editText_signature.getText().toString();
                SharedPreferences.Editor editor=
                        getSharedPreferences("user_signature",Context.MODE_PRIVATE).edit();
                editor.putString("signature",signature);
                editor.apply();
                Intent intent=new Intent(SignatureActivity.this,MainActivity.class);
                intent.putExtra("navId",4);
                startActivity(intent);
                finish();
            }
        });


    }
}
