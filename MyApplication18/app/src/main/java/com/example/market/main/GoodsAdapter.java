package com.example.market.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.market.item.ItemActivity;
import com.example.vo.Goods;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    //    成员变量
    private Context context;
    private List<Goods> goodsList;
    private Bitmap bitmap;

    //    构造方法
    public GoodsAdapter(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }


    //    内部静态持有类
   public static class ViewHolder extends RecyclerView.ViewHolder {

        View goodsView;//点击事件

        CardView cardView;
        ImageView goodsImage;
        TextView goodsName;

        Handler handler;

        public ViewHolder(View view) {
            super(view);

            goodsView = view;//点击事件

            cardView = (CardView) view;
            goodsImage = view.findViewById(R.id.goods_image);
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    goodsImage.setImageBitmap(bitmap);
                }
            };
            goodsName = view.findViewById(R.id.goods_name);
        }
    }


    //    重写
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.market_main_goods, parent, false);

        //点击事件
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Goods goods = goodsList.get(position);

                Gson gson = new Gson();
                String json = gson.toJson(goods);
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("goods", json);
                context.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goods goods = goodsList.get(position);
        holder.goodsName.setText(goods.getGoods_name() + "-￥" + goods.getGoods_price());
        doHttpGetGoodsImg(holder.handler, goods.getGoods_image());
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    //    Support方法
    //辅助方法：用于请求设置goods_image
    public void doHttpGetGoodsImg(final Handler handler, String img_name) {
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
                bitmap = BitmapFactory.decodeStream(inputStream);
                Message message = new Message();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        });
    }
}
