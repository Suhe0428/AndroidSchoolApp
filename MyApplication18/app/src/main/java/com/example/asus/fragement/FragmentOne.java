package com.example.asus.fragement;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.asus.view.ImageBarnnerFrameLayout;
import com.example.asus.view.PhoneWidth;
import com.example.help.main.HelpActivity;
import com.example.vo.Goods;
import com.example.asus.waterfall.GoodsAdapter;
import com.example.market.main.MarketActivity;
import com.example.vo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentOne extends Fragment implements ImageBarnnerFrameLayout.FrameLayoutListenner {
    private static final String TAG = "FragmentOne";
    
    private List<Goods> goodsList=new ArrayList<>();
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
                Intent intent=new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
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
                Intent intent=new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        /*3.布局浏览*/
        initGoods();
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        GoodsAdapter adapter=new GoodsAdapter(goodsList,getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

    //轮播图片点击触发事件
    @Override
    public void clickImageIndex(int position) {
        Toast.makeText(getActivity(),"轮播图",Toast.LENGTH_SHORT).show();
    }

    //辅助方法：用以初始化布局显示
    private void initGoods(){
        doPostGetGoods();
    }

    //辅助方法：用于获取页面展示的Goods
    public void doPostGetGoods(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody requestBody = new FormBody.Builder().build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "queryAllGoods").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Gson gson = new Gson();
                List<Goods> list=gson.fromJson(result, new TypeToken<List<Goods>>(){}.getType());
                Collections.shuffle(list);
                for(int i=0;i<list.size();i++){
                    Goods goods=list.get(i);
                    doPostGetUserName(goods,Integer.toString(goods.getGoods_seller_id()));
                    goodsList.add(goods);
                    i++;
                }
            }
        });
    }

    //辅助方法：用于获取指定卖家的昵称，并保存
    public void  doPostGetUserName(final Goods goods, String goods_seller_id){
        //1.okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        FormBody requestBody = new FormBody.Builder()
                .add("user_id", goods_seller_id).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(BaseUrl.BASE_URL + "findUserById").post(requestBody).build();
        //3.将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4.执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson=new Gson();
                User user=gson.fromJson(result,User.class);
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("goods_seller_names",Context.MODE_PRIVATE);
                sharedPreferences.edit().putString(""+goods.getGoods_id(),user.getUser_name()).apply();
            }
        });

    }

}