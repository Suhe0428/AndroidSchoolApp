package com.example.asus.secondhand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.asus.myapplication.R;
import com.example.asus.waterfall.Fruit;
import com.example.asus.waterfall.FruitAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecondHandActivity extends AppCompatActivity {

    private List<Fruit> fruitList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand);
        initFruits();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

    }

    private void initFruits(){
        for(int i=0;i<2;i++){
            for (int j = 0; j < 10; j++) {
                Fruit pear = new Fruit(getRandomLengthName("pear"), R.drawable.pear);
                fruitList.add(pear);
            }
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
