package com.example.asus.fragement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asus.message.MessageAdapter;
import com.example.asus.message.MessageBean;
import com.example.asus.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentThree extends Fragment {
    public List<MessageBean> messageBeanList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        messageBeanList = new ArrayList<>();
        ListView listView_message = view.findViewById(R.id.list_message);
        initMessage();
        listView_message.setAdapter(new MessageAdapter(getActivity(), messageBeanList));
        return view;
    }

    private void initMessage() {//初始化数据
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

    }
}