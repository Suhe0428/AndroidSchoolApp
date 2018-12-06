package com.example.asus.camera;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.asus.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CameraActivity extends AppCompatActivity {

    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private int[] icon = {
            R.mipmap.b1, R.mipmap.b2, R.mipmap.b3,
            R.mipmap.b4, R.mipmap.b5, R.mipmap.b6,
            R.mipmap.b7, R.mipmap.b8, R.mipmap.b9,
    };
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // 隐藏系统标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        gridView = findViewById(R.id.picture_gridView);
        //1.准备数据源
        dataList = new ArrayList<Map<String, Object>>();
        //2.加载适配器SimpleAdapter
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.camera_gridview, new String[]{"image"}, new int[]{R.id.icon_image});
        //3.GridView加载适配器
        gridView.setAdapter(simpleAdapter);
        //4.GridView配置监听器
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CameraActivity.this,"ok",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<Map<String, Object>> getData() {

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < icon.length; i++) {
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("image",icon[i]);
                dataList.add(map);
            }
        }
        return dataList;
    }
}