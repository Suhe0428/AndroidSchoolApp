package com.example.asus.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityAdapter extends BaseAdapter {

    public List<Community>  communityList;
    public Context context;
    public LayoutInflater layoutInflater;

    public CommunityAdapter(List<Community> communityList, Context context) {
        this.communityList = communityList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return communityList.size();
    }

    @Override
    public Object getItem(int position) {
        return communityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.fragment_two_community_list_item,null);
            viewHolder.circleImageView_community_source_img=convertView.findViewById(R.id.circleImageView_community_source_img);
            viewHolder.textView_community_source=convertView.findViewById(R.id.textView__community_source);
            viewHolder.textView__community_content=convertView.findViewById(R.id.textView__community_content);
            viewHolder.imageView_community_content_img=convertView.findViewById(R.id.imageView_community_content_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    private static class ViewHolder{
        public TextView textView_community_source;
        public CircleImageView circleImageView_community_source_img;
        public TextView textView__community_content;
        public ImageView imageView_community_content_img;
    }
}