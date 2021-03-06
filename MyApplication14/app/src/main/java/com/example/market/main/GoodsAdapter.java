package com.example.market.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.myapplication.R;
import com.example.market.item.ItemActivity;
import com.google.gson.Gson;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{

    private Context context;
    private List<Goods> goodsList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        View goodsView;//点击事件

        CardView cardView;
        ImageView goodsImage;
        TextView goodsName;

        public ViewHolder(View view) {
            super(view);

            goodsView=view;//点击事件

            cardView = (CardView)view;
            goodsImage = view.findViewById(R.id.goods_image);
            goodsName = view.findViewById(R.id.goods_name);
        }
    }
    public GoodsAdapter(List<Goods> goodsList1){
        goodsList = goodsList1;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.market_main_goods_item,parent,false);

        //点击事件
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Goods goods = goodsList.get(position);

                Gson gson=new Gson();
                String json=gson.toJson(goods);
                Intent intent=new Intent(context, ItemActivity.class);
                intent.putExtra("goods",json);
                context.startActivity(intent);

            }
        });
        return viewHolder;
}
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goods goods = goodsList.get(position);
        holder.goodsName.setText(goods.getName()+"-"+goods.getPrice());
        Glide.with(context).load(goods.getImageId()).into(holder.goodsImage);
    }
    @Override
    public int getItemCount() {
        return goodsList.size();
    }
}
