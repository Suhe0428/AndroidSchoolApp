package com.example.asus.waterfall;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private List<Fruit> fruitList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        View fruitView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            fruitView=view;
            fruitImage =  view.findViewById(R.id.fruit_image);
            fruitName = view.findViewById(R.id.textView_title);

        }
    }
    public FruitAdapter(List<Fruit> fruitList1){
        fruitList=fruitList1;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.waterfall_item,parent,false);

        //点击事件
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Fruit fruit=fruitList.get(position);
                Toast.makeText(v.getContext(),"ok"+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Fruit fruit=fruitList.get(position);
                Toast.makeText(v.getContext(),"ok"+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit=fruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }
}