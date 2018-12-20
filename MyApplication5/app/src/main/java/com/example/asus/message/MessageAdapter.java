package com.example.asus.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends BaseAdapter {

    public List<MessageBean> messageBeanList;
    public Context context;
    public LayoutInflater layoutInflater;

    public MessageAdapter(Context context,List<MessageBean> messageBeanList) {
        this.context=context;
        this.messageBeanList = messageBeanList;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messageBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.fragment_three_message_list_item,null);
            viewHolder.circleImageView=convertView.findViewById(R.id.circleImageView_message_item_image);
            viewHolder.textView_message_item_title=convertView.findViewById(R.id.textView_message_item_title);
            viewHolder.textView_message_item_time=convertView.findViewById(R.id.textView_message_item_time);
            viewHolder.textView_message_item_content=convertView.findViewById(R.id.textView_message_item_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //从list取出对象
        MessageBean messageBean=messageBeanList.get(position);
        //设置item的内容（头像、标题、时间、内容）
        viewHolder.circleImageView.setImageResource(messageBean.getMessage_source_img());
        viewHolder.textView_message_item_title.setText(messageBean.getMessage_source());
        viewHolder.textView_message_item_time.setText(messageBean.getMessage_time());
        viewHolder.textView_message_item_content.setText(messageBean.getMessage_content());
        return convertView;
    }

    private static class ViewHolder{
        public CircleImageView circleImageView;
        public TextView textView_message_item_title;
        public TextView textView_message_item_time;
        public TextView textView_message_item_content;
    }
}


