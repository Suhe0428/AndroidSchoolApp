package com.example.asus.community;

import android.content.Context;
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

    private List<Community> communityList;
    private Context context;
    private LayoutInflater layoutInflater;

    public CommunityAdapter(Context context, List<Community> communityList) {
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_two_community_list_item, null);
            viewHolder.circleImageView_community_source_img = convertView.findViewById(R.id.circleImageView_community_source_img);
            viewHolder.textView_community_source = convertView.findViewById(R.id.textView__community_source);
            viewHolder.textView__community_content = convertView.findViewById(R.id.textView__community_content);
            viewHolder.imageView_community_content_img = convertView.findViewById(R.id.imageView_community_content_img);

            /*帖子操作相关
             * 点赞
             * 评论
             * 分享*/
            viewHolder.imageButton_fav = convertView.findViewById(R.id.imageButton_fav);
            viewHolder.imageButton_com = convertView.findViewById(R.id.imageButton_com);
            viewHolder.imageButton_sha = convertView.findViewById(R.id.imageButton_sha);
            viewHolder.textView_fav_num = convertView.findViewById(R.id.textView_fav_num);
            viewHolder.textView_com_num = convertView.findViewById(R.id.textView_com_num);
            viewHolder.textView_sha_num = convertView.findViewById(R.id.textView_sha_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Community community = communityList.get(position);
        viewHolder.circleImageView_community_source_img.setImageResource(community.getCommunity_item_source_img());
        viewHolder.textView_community_source.setText(community.getCommunity_item_source());
        viewHolder.textView__community_content.setText(community.getCommunity_item_content());
        viewHolder.imageView_community_content_img.setImageResource(community.getCommunity_item_content_img());
        if (!community.getCommunity_item_isfav()) {//初始化为未点赞
            viewHolder.imageButton_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_community_favorite_border_24dp));
        } else {
            viewHolder.imageButton_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_community_favorite_24dp));
        }
        viewHolder.textView_fav_num.setText(Integer.toString(community.getCommunity_item_fav_num()));

//        底部操作响应
        viewHolder.imageButton_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!community.getCommunity_item_isfav()) {//未点赞的点赞
                    viewHolder.imageButton_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_community_favorite_24dp));
                    community.setCommunity_item_isfav(true);
                    //点赞数+1
                    int num = community.getCommunity_item_fav_num();
                    String str_num = Integer.toString(num + 1);
                    community.setCommunity_item_fav_num(++num);
                    viewHolder.textView_fav_num.setText(str_num);
                } else {//已经点赞的取消
                    viewHolder.imageButton_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_community_favorite_border_24dp));
                    community.setCommunity_item_isfav(false);
                    //点赞数-1
                    int num = community.getCommunity_item_fav_num();
                    String str_num = Integer.toString(num - 1);
                    community.setCommunity_item_fav_num(--num);
                    viewHolder.textView_fav_num.setText(str_num);
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
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