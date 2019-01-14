package com.example.asus.waterfall;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private static final String TAG = "GoodsAdapter";

    private Activity activity;

    private List<Goods> goodsList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View goodsView;
        ImageView imageView_goodsImage;
        TextView textView_goodsName;
        CircleImageView circleImageView;
        TextView textView_userName;

        public ViewHolder(View view) {
            super(view);
            goodsView = view;
            imageView_goodsImage = view.findViewById(R.id.imageView_goodsImage);
            textView_goodsName = view.findViewById(R.id.textView_goodsInfo);
            circleImageView = view.findViewById(R.id.circle_userImage);
            textView_userName = view.findViewById(R.id.textView_userName);
        }
    }

    public GoodsAdapter(List<Goods> goodsList, Activity activity) {
        this.goodsList = goodsList;
        this.activity = activity;
    }

    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*1.加载布局*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_waterfall_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        /*2.设置监听*/
        viewHolder.imageView_goodsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Goods goods = goodsList.get(position);
                Toast.makeText(v.getContext(), goods.getGoods_name(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.textView_goodsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Goods goods = goodsList.get(position);
                Toast.makeText(v.getContext(), goods.getGoods_name(), Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.ViewHolder holder, int position) {
        Goods goods = goodsList.get(position);

        doHttpGetImg(holder.imageView_goodsImage, goods.getGoods_image());

        holder.textView_goodsName.setText(goods.getGoods_name());

        SharedPreferences sharedPreferences = activity.getSharedPreferences("goods_seller_names", Context.MODE_PRIVATE);
        String goods_seller_name = sharedPreferences.getString("" + goods.getGoods_id(), "1");
        holder.textView_userName.setText(goods_seller_name);
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    //辅助方法：用于请求设置goods_image
    public void doHttpGetImg(final ImageView imageView, String img_name) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BaseUrl.IMG_BASE_URL + img_name)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();//得到图片的流
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            }
        });
    }


}