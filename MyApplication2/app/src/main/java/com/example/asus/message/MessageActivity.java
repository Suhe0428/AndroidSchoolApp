package com.example.asus.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import com.example.asus.fragement.FragmentThree;
import com.example.asus.msgitem.MsgItem;
import com.example.asus.msgitem.MsgItemAdapter;
import com.example.asus.myapplication.MainActivity;
import com.example.asus.myapplication.R;


public class MessageActivity extends AppCompatActivity {
    private List<MsgItem> msgList = new ArrayList<MsgItem>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("name"));

        initMsgs(intent.getStringExtra("content")); // 初始化消息数据

        inputText = findViewById(R.id.text_input);
        send =  findViewById(R.id.button_send);
        msgRecyclerView =  findViewById(R.id.recycleView_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgItemAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        //发送
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    MsgItem msg = new MsgItem(content, MsgItem.TYPE_SENT);
                    msgList.add(msg);

                    // 当有新消息时，刷新ListView中的显示
                    adapter.notifyItemInserted(msgList.size() - 1);
                    // 将ListView定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    // 清空输入框中的内容
                    inputText.setText("");
                }
            }
        });
    }

    /**
     * 初始化聊天消息
     */
    private void initMsgs(String content) {
        MsgItem msg1 = new MsgItem(content, MsgItem.TYPE_RECEIVED);
        msgList.add(msg1);
    }

}
