package com.example.asus.waterfall;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private List<Goods> goodsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View goodsView;
        ImageView imageView_goodsImage;
        TextView textView_goodsName;
        CircleImageView circleImageView;
        TextView textView_userName;

        public ViewHolder(View view) {
            super(view);
            goodsView=view;
            imageView_goodsImage =  view.findViewById(R.id.imageView_goodsImage);
            textView_goodsName = view.findViewById(R.id.textView_goodsInfo);
            circleImageView=view.findViewById(R.id.circle_userImage);
            textView_userName=view.findViewById(R.id.textView_userName);

        }

    }

    public GoodsAdapter(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*1.加载布局*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_waterfall_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);

        /*2.设置监听*/
        viewHolder.imageView_goodsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Goods goods=goodsList.get(position);
                Toast.makeText(v.getContext(), "ok" + goods.getGoods_name(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.textView_goodsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Goods goods=goodsList.get(position);
                Toast.makeText(v.getContext(), "ok" + goods.getGoods_name(), Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.ViewHolder holder, int position) {
        Goods goods = goodsList.get(position);
        holder.imageView_goodsImage.setImageResource(R.drawable.image_main_group_show_1);
        holder.textView_goodsName.setText(goods.getGoods_name());
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }
}