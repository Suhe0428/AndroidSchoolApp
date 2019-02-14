package com.example.asus.my.mymarket;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.Goods;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyMarketAdapter extends RecyclerView.Adapter<MyMarketAdapter.ViewHolder> {
    private static final String TAG = "MyMarketAdapter";
    /*成员变量*/
    private Context context;
    private List<Goods> goodsList;

    /*构造函数*/
    public MyMarketAdapter(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    /*内部持有类*/
    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView imageView_goodsImage;
        public Handler imgHandler;
        public TextView textView_goodsName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView =(CardView) itemView;
            imageView_goodsImage=itemView.findViewById(R.id.imageView_goodsImage);
            textView_goodsName=itemView.findViewById(R.id.textView_goodsName);

            imgHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Bitmap bitmap= (Bitmap) msg.obj;
                    imageView_goodsImage.setImageBitmap(bitmap);
                }
            };
        }
    }

    /*继承重写*/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.my_market_item, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Goods goods=goodsList.get(i);
        doHttpGetGoodsImg(viewHolder.imgHandler, goods.getGoods_image());
        viewHolder.textView_goodsName.setText(goods.getGoods_name());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(goods,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }


    /*Support*/
    //1.用于请求设置goods_image
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
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message message = new Message();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        });
    }

    //2.删除Goods
    private void doHttpDeleteGoods(Goods goods){
        String json=new Gson().toJson(goods);
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("json",json).build();
        Request request=new Request.Builder().get().url(BaseUrl.BASE_URL+"deleteGoods").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                if("true".equals(result)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            Toast.makeText(context,"删除成功",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }).start();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            Toast.makeText(context,"删除失败",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }).start();
                }
            }
        });
    }

    //3.显示对话框
    private void showDialog(final Goods goods, final int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_delete_dialog);
        builder.setTitle("温馨提示");
        builder.setMessage("确定要删除记录吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goodsList.remove(position);
                MyMarketAdapter.this.notifyItemRemoved(i);
                notifyDataSetChanged();
                doHttpDeleteGoods(goods);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
