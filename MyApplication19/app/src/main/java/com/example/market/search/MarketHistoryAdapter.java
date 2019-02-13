package com.example.market.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.myapplication.R;

import java.util.List;

public class MarketHistoryAdapter extends ArrayAdapter<MarketHistory> {

    private int resourceId;

    public MarketHistoryAdapter(@NonNull Context context, int textViewResourceId, List<MarketHistory> objects) {
        super(context, textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MarketHistory history=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        //前Image
        ImageView imageView_icon=view.findViewById(R.id.item_image);
        imageView_icon.setImageResource(R.drawable.ic_market_search_history);
        //文本
        TextView textView=view.findViewById(R.id.item_text);
        textView.setText(history.getHistoryText());

        return view;
    }

}
