package com.example.asus.community.upload;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.asus.myapplication.R;

public class PostUploadActivity extends AppCompatActivity {
    private static final String TAG = "PostUploadActivity";
    /*成员变量*/
    private ImageButton imageButton_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upload);
        /*0.标题栏*/
        setTitle("发帖");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_help_back);
        }
        /*1.加载布局*/
        imageButton_picture=findViewById(R.id.imageButton_picture);
        imageButton_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开手机相册
            }
        });

    }



    /*Support*/
    //1.打开手机相册
    public void openAlbum(){

    }
    //2.上传到服务器
    public void doHttpUploadPost(){

    }


    /*重写*/
    //1.加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.community_upload_title,menu);
        return true;
    }
    //2.标题栏响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case  R.id.commit:
                //上传到服务器
                break;
            default:
        }
        return true;
    }
}
