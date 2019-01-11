package com.example.asus.fragement;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asus.message.MessageActivity;
import com.example.asus.message.MessageAdapter;
import com.example.asus.message.MessageBean;
import com.example.asus.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentThree extends Fragment {
    private List<MessageBean> messageBeanList;
    private ListView listView_message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        messageBeanList = new ArrayList<>();
        listView_message = view.findViewById(R.id.list_message);
        initMessage();
        listView_message.setAdapter(new MessageAdapter(getActivity(), messageBeanList));
        //点击进入
        listView_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),MessageActivity.class);
                intent.putExtra("name",messageBeanList.get(position).getMessage_source());
                intent.putExtra("content",messageBeanList.get(position).getMessage_content());
                startActivity(intent);

            }
        });

        //长按删除
        listView_message.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return true;
            }
        });

        return view;
    }

    //初始化数据
    private void initMessage() {
        MessageBean message1 = new MessageBean(R.drawable.pear, "中国移动", "11-3", "一起来聊天啊");
        messageBeanList.add(message1);
        MessageBean message2 = new MessageBean(R.drawable.pear, "中国联通", "11-3", "一起来聊天啊");
        messageBeanList.add(message2);
        MessageBean message3 = new MessageBean(R.drawable.pear, "中国电信", "11-3", "一起来聊天啊");
        messageBeanList.add(message3);
        MessageBean message4 = new MessageBean(R.drawable.pear, "马云", "11-3", "一起来聊天啊");
        messageBeanList.add(message4);
        MessageBean message5 = new MessageBean(R.drawable.pear, "马化腾", "11-3", "一起来聊天啊");
        messageBeanList.add(message5);
        MessageBean message6 = new MessageBean(R.drawable.pear, "李彦宏", "11-3", "一起来聊天啊");
        messageBeanList.add(message6);
        MessageBean message7 = new MessageBean(R.drawable.pear, "华为", "11-3", "一起来聊天啊");
        messageBeanList.add(message7);
        MessageBean message8 = new MessageBean(R.drawable.pear, "苹果", "11-3", "一起来聊天啊");
        messageBeanList.add(message8);
        MessageBean message9 = new MessageBean(R.drawable.pear, "小米", "11-3", "一起来聊天啊");
        messageBeanList.add(message9);
        MessageBean message10 = new MessageBean(R.drawable.pear, "步步高", "11-3", "一起来聊天啊");
        messageBeanList.add(message10);
        MessageBean message11 = new MessageBean(R.drawable.pear, "魅族", "11-3", "一起来聊天啊");
        messageBeanList.add(message11);
    }

    /*显示删除对话框*/
    public void showDeleteDialog(final int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_dialog_black_24dp);
        builder.setTitle("提示");
        builder.setMessage("删除");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        messageBeanList.remove(position);
                        listView_message.setAdapter(new MessageAdapter(getActivity(), messageBeanList));
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}