package com.example.help.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.myapplication.R;

public class HelpHistoryAdapter extends ArrayAdapter<HelpHistory> {
    private int resourceId;

    public HelpHistoryAdapter(Context context, int resource, List<HelpHistory> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HelpHistory helpHistory=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        //前Image
        ImageView imageView_icon=view.findViewById(R.id.item_image);
        imageView_icon.setImageResource(R.drawable.ic_market_search_history);
        //文本
        TextView textView=view.findViewById(R.id.item_text);
        textView.setText(helpHistory.getHelp_history_text());

        return view;
    }
}
