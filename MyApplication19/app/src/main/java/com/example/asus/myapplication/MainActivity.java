package com.example.asus.myapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.asus.fragement.FragmentFour;
import com.example.asus.fragement.FragmentOne;
import com.example.asus.fragement.FragmentThree;
import com.example.asus.fragement.FragmentTwo;

public class MainActivity extends AppCompatActivity {

    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;
    private FragmentFour fragmentFour;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showNav(R.id.navigation_home);
                    return true;
                case R.id.navigation_dashboard:
                    showNav(R.id.navigation_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    showNav(R.id.navigation_notifications);
                    return true;
                case R.id.navigation_my:
                    showNav(R.id.navigation_my);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        int nav=intent.getIntExtra("navId",1);
        if(nav==1){
            nav=R.id.navigation_home;
        }else if(nav==2){
            nav=R.id.navigation_dashboard;
        }else if(nav==3){
            nav=R.id.navigation_notifications;
        }else if(nav==4){
            nav=R.id.navigation_my;
        }
        init(nav);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    /*重写返回键退出程序*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MainActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //init（）用来初始化组件
    private void init(int nav){
        fragmentOne=new FragmentOne();
        fragmentTwo=new FragmentTwo();
        fragmentThree=new FragmentThree();
        fragmentFour=new FragmentFour();
        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();
        beginTransaction.add(R.id.content,fragmentOne)
                .add(R.id.content,fragmentTwo)
                .add(R.id.content,fragmentThree)
                .add(R.id.content,fragmentFour);//开启一个事务将fragment动态加载到组件
        beginTransaction.hide(fragmentOne).hide(fragmentTwo).hide(fragmentThree).hide(fragmentFour);//隐藏fragment
        beginTransaction.addToBackStack(null);//返回到上一个显示的fragment
        beginTransaction.commit();//每一个事务最后操作必须是commit（），否则看不见效果
        showNav(nav);
    }

    //展示底部导航
    private void showNav(int navId){
        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();
        switch (navId){
            case R.id.navigation_home:
                beginTransaction.hide(fragmentTwo).hide(fragmentThree).hide(fragmentFour);
                beginTransaction.show(fragmentOne);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_dashboard:
                beginTransaction.hide(fragmentOne).hide(fragmentThree).hide(fragmentFour);
                beginTransaction.show(fragmentTwo);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_notifications:
                beginTransaction.hide(fragmentOne).hide(fragmentTwo).hide(fragmentFour);
                beginTransaction.show(fragmentThree);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_my:
                beginTransaction.hide(fragmentOne).hide(fragmentTwo).hide(fragmentThree);
                beginTransaction.show(fragmentFour);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
        }
    }


}

