package com.example.asus.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.test.R;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<History> {

    private int resourceId;

    public HistoryAdapter(@NonNull Context context, int textViewResourceId, List<History> objects) {
        super(context, textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        History history=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        //前Image
        ImageView imageView_icon=view.findViewById(R.id.item_image);
        imageView_icon.setImageResource(R.drawable.ic_history_24dp);
        //文本
        TextView textView=view.findViewById(R.id.item_text);
        textView.setText(history.getHistoryText());

        return view;
    }

}
