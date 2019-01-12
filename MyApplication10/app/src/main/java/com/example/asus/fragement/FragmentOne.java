package com.example.asus.fragement;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;
import com.example.asus.waterfall.Fruit;
import com.example.asus.waterfall.FruitAdapter;
import com.example.asus.secondhand.SecondHandActivity;
import com.example.asus.view.ImageBarnnerFrameLayout;
import com.example.asus.view.PhoneWidth;
import com.example.market.main.MarketActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentOne extends Fragment implements ImageBarnnerFrameLayout.FrameLayoutListenner {
    private List<Fruit> fruitList=new ArrayList<>();
    private ImageBarnnerFrameLayout imageBarnnerFrameLayout;
    private int[] ids=new int[]{
            R.drawable.image_main_show_1,
            R.drawable.image_main_show_2,
            R.drawable.image_main_show_3
    };
    private LinearLayout linearLayout_second;
    private LinearLayout linearLayout_help;
    private ImageButton imageButton_secondHand;
    private ImageButton imageButton_help;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_one,container,false);
        /*1.轮播图*/
        //1.1获取手机宽度
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        PhoneWidth.WIDTH=displayMetrics.widthPixels;
        //1.2添加图片
        imageBarnnerFrameLayout=view.findViewById(R.id.image_group);
        //1.3点击事件
        imageBarnnerFrameLayout.setFrameLayoutListenner(this);
        //1.4圆点布局
        List<Bitmap> list=new ArrayList<>();
        for (int i = 0; i <ids.length ; i++) {
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),ids[i]);
            list.add(bitmap);
        }
        imageBarnnerFrameLayout.addBitmap(list);//在ImageBarnnerFrameLayout中


        /*2.分区进入*/
        linearLayout_second=view.findViewById(R.id.linearLayout_second);
        linearLayout_help=view.findViewById(R.id.linearLayout_help);
        imageButton_secondHand=view.findViewById(R.id.imageButton_secondHand);
        imageButton_help=view.findViewById(R.id.imageButton_help);
        linearLayout_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MarketActivity.class);
                startActivity(intent);
            }
        });
        linearLayout_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"校园互助",Toast.LENGTH_SHORT).show();
            }
        });
        imageButton_secondHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MarketActivity.class);
                startActivity(intent);
            }
        });
        imageButton_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"校园互助",Toast.LENGTH_SHORT).show();
            }
        });

        /*3.浏览*/
        initFruits();
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void clickImageIndex(int position) {//轮播图片点击触发事件
        Toast.makeText(getActivity(),"轮播图",Toast.LENGTH_SHORT).show();
    }

    private void initFruits(){
        for(int i=0;i<2;i++){
            Fruit pear1 = new Fruit(getRandomLengthName("pear"), R.drawable.image_main_group_show_1);
            fruitList.add(pear1);
            Fruit pear2 = new Fruit(getRandomLengthName("pear"), R.drawable.image_main_group_show_2);
            fruitList.add(pear2);
            Fruit pear3 = new Fruit(getRandomLengthName("pear"), R.drawable.image_main_group_show_3);
            fruitList.add(pear3);
            Fruit pear4 = new Fruit(getRandomLengthName("pear"), R.drawable.image_main_group_show_4);
            fruitList.add(pear4);
        }
    }

    private String getRandomLengthName(String name){
        Random random=new Random();
        int length =random.nextInt(20)+1;
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(name);
        }
        return stringBuilder.toString();
    }

}