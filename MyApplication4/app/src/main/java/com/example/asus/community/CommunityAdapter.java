package com.example.asus.community;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityAdapter extends BaseAdapter {

    public List<Community>  communityList;
    public Context context;
    public LayoutInflater layoutInflater;
    boolean isChanged = false;

    public CommunityAdapter(Context context,List<Community> communityList) {
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
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.fragment_two_community_list_item,null);
            viewHolder.circleImageView_community_source_img=convertView.findViewById(R.id.circleImageView_community_source_img);
            viewHolder.textView_community_source=convertView.findViewById(R.id.textView__community_source);
            viewHolder.textView__community_content=convertView.findViewById(R.id.textView__community_content);
            viewHolder.imageView_community_content_img=convertView.findViewById(R.id.imageView_community_content_img);

            viewHolder.imageButton_fav=convertView.findViewById(R.id.imageButton_fav);
            viewHolder.imageButton_com=convertView.findViewById(R.id.imageButton_com);
            viewHolder.imageButton_sha=convertView.findViewById(R.id.imageButton_sha);
            viewHolder.textView_fav_num=convertView.findViewById(R.id.textView_fav_num);
            viewHolder.textView_com_num=convertView.findViewById(R.id.textView_com_num);
            viewHolder.textView_sha_num=convertView.findViewById(R.id.textView_sha_num);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Community community=communityList.get(position);
        viewHolder.circleImageView_community_source_img.setImageResource(community.getCommunity_item_source_img());
        viewHolder.textView_community_source.setText(community.getCommunity_item_source());
        viewHolder.textView__community_content.setText(community.getCommunity_item_content());
        viewHolder.imageView_community_content_img.setImageResource(community.getCommunity_item_content_img());

//        底部操作响应
        viewHolder.imageButton_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == viewHolder.imageButton_fav)
                {

                    if(isChanged){
                        viewHolder.imageButton_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_community_favorite_24dp));

                        int num=Integer.parseInt(viewHolder.textView_fav_num.getText().toString());
                        String str_num=Integer.toString(num+1);
                        viewHolder.textView_fav_num.setText(str_num);
                    }else
                    {
                        viewHolder.imageButton_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_community_favorite_border_24dp));
                        int num=Integer.parseInt(viewHolder.textView_fav_num.getText().toString());
                        String str_num=Integer.toString(num-1);
                        viewHolder.textView_fav_num.setText(str_num);
                    }
                    isChanged = !isChanged;

                }
            }
        });


        return convertView;
    }

    private static class ViewHolder{
//      Item主体
        public TextView textView_community_source;
        public CircleImageView circleImageView_community_source_img;
        public TextView textView__community_content;
        public ImageView imageView_community_content_img;
//      底部操作
        public ImageButton imageButton_fav;
        public TextView textView_fav_num;
        public ImageButton imageButton_com;
        public TextView textView_com_num;
        public ImageButton imageButton_sha;
        public TextView textView_sha_num;
    }
}