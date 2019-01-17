package com.example.market.upload;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.myapplication.R;


public class UploadTitleLayout extends LinearLayout{
    private Button button_back;
    private Button button_upload;

    public UploadTitleLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.market_upload_title,this);

        /*1.返回按钮*/
        button_back=findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        /*2.发布按钮*/
        button_upload=findViewById(R.id.button_upload);
        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ok",Toast.LENGTH_SHORT).show();
                ((Activity)getContext()).finish();
            }
        });

    }
}
