package com.example.market.item;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.Goods;
import com.example.vo.Place;
import com.example.vo.Target;
import com.example.vo.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import cn.carbs.android.expandabletextview.library.ExpandableTextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    private ImageView imageView_goods;
    public Handler imageGoodsHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap=(Bitmap) msg.obj;
            imageView_goods.setImageBitmap(bitmap);
        }
    };

    private TextView textView_name;

    private TextView textView_price;

    private TextView textView_target;
    public Handler textTargetHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String target_name=(String) msg.obj;
            textView_target.setText(target_name);
        }
    };

    private TextView textView_place;
    private Handler textPlaceHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String place_name=(String)msg.obj;
            textView_place.setText(place_name);
        }
    };

    private TextView textView_seller;
    public Handler textSellerHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String seller_name=(String) msg.obj;
            textView_seller.setText(seller_name);
        }
    };

    private TextView textView_seller_phone;
    public Handler textPhoneHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String seller_phone=(String) msg.obj;
            textView_seller_phone.setText(seller_phone);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item);
        /*1.获取数据*/
        Intent intent=getIntent();
        String json=intent.getStringExtra("goods");
        Log.i(TAG, "onCreate: "+json);
        Gson gson =new Gson();
        Goods goods= gson.fromJson(json,Goods.class);
        /*2.设置标题*/
        setTitle(goods.getGoods_name());
        /*3.设置内容*/
            /*3.1 图片*/
            imageView_goods=findViewById(R.id.imageView_goods);
            doHttpGetGoodsImg(goods.getGoods_image());

            /*3.2 名称*/
            textView_name=findViewById(R.id.textView_name);
            textView_name.setText(goods.getGoods_name());

            /*3.3 价格*/
            textView_price=findViewById(R.id.textView_price);
            textView_price.setText("￥"+Double.toString(goods.getGoods_price()));

            /*3.4 标签分类*/
            textView_target=findViewById(R.id.textView_target);
            doHttpGetTargetName(Integer.toString(goods.getGoods_target_id()));

            /*3.5 交易地点*/
            textView_place=findViewById(R.id.textView_place);
        Log.i(TAG, "onCreate: "+goods.getGoods_place_id());
            doHttpGetPlaceName(Integer.toString(goods.getGoods_place_id()));

            /*3.6 商家*/
            textView_seller=findViewById(R.id.textView_seller);

            /*3.7 联系方式*/
            textView_seller_phone=findViewById(R.id.textView_seller_phone);

            doHttpGetSeller(Integer.toString(goods.getGoods_seller_id()));

            /**3.8 简介*/
            ExpandableTextView expandableTextView = findViewById(R.id.etv);
            expandableTextView.setText(goods.getGoods_info());
            expandableTextView.setExpandListener(new ExpandableTextView.OnExpandListener() { //展开收起监听
                @Override
                public void onExpand(ExpandableTextView view) {//展开
                    Log.i("", "展开") ;
                }
                @Override
                public void onShrink(ExpandableTextView view) {//收起
                    Log.i("", "收起") ;
                }
            });
    }

    //辅助方法：用于请求设置goods_image
    public void doHttpGetGoodsImg(String img_name) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BaseUrl.GOODS_IMG_BASE_URL + img_name)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();//得到图片的流
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message message=new Message();
                message.obj=bitmap;
                imageGoodsHandler.sendMessage(message);
            }
        });
    }

    //辅助方法：用于获取goods_seller_id的user_phone
    public void doHttpGetSeller(String user_id){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("user_id",user_id).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"findUserById").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result =response.body().string();
                Gson gson=new Gson();
                User user=gson.fromJson(result,User.class);
                Message message1=new Message();
                message1.obj=user.getUser_name();
                textSellerHandler.sendMessage(message1);

                Message message2=new Message();
                message2.obj=user.getUser_phone();
                textPhoneHandler.sendMessage(message2);

            }
        });
    }

    //辅助方法：用于获取goods_target_id的target_name
    public void doHttpGetTargetName(String target_id){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("target_id",target_id).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"findTargetById").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Gson gson=new Gson();
                Target target=gson.fromJson(result,Target.class);
                Message message=new Message();
                message.obj=target.getTarget_name();
                textTargetHandler.sendMessage(message);
            }
        });
    }

    //辅助方法：用于获取good_place_id的place_name
    public void doHttpGetPlaceName(String place_id){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("place_id",place_id).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"findPlaceById").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Log.i(TAG, "onResponse: "+result);
                Place place=new Gson().fromJson(result,Place.class);
                Message message=new Message();
                message.obj=place.getPlace_name();
                textPlaceHandler.sendMessage(message);
            }
        });
    }
}
