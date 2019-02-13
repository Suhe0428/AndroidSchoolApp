package com.example.asus.my.mymarket;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.Goods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyMarketActivity extends AppCompatActivity {
    private static final String TAG = "MyMarketActivity";

    private RecyclerView recyclerView;

    private List<Goods> goodsList=new ArrayList<>();

    private MyMarketAdapter myMarketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_market);
        //0.标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_help_back);
        }
        setTitle("我的交易");
        //1.生成布局
        doHttpGetGoods();
        recyclerView=findViewById(R.id.recyclerView_my_market);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        myMarketAdapter=new MyMarketAdapter(goodsList);
        recyclerView.setAdapter(myMarketAdapter);
    }

    /*Support*/
    public void doHttpGetGoods() {
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"queryAllGoods").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Log.i(TAG, "onResponse: "+result);
                List<Goods> goodsListFromJson=new Gson().fromJson(result,new TypeToken<List<Goods>>(){}.getType());
                for (int i=0;i<goodsListFromJson.size();i++){
                    Goods goods=goodsListFromJson.get(i);
                    SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                    String user_id=sharedPreferences.getString("user_id","");
                    if(goods.getGoods_seller_id()!=Integer.parseInt(user_id)){
                        goodsListFromJson.remove(goods);
                        i--;
                    }
                }
                goodsList.addAll(goodsListFromJson);
            }
        });
    }

    /*重写*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
