package com.example.asus.upload;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.test.R;

public class UploadTitleLayout extends LinearLayout{
    public UploadTitleLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title_upload,this);
        Button titleBack=findViewById(R.id.title_back);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        Button titleUpload=findViewById(R.id.upload);
        titleUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ok",Toast.LENGTH_SHORT).show();
                ((Activity)getContext()).finish();
            }
        });
    }
}
