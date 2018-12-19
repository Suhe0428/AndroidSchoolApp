package com.example.asus.msgitem;

public class MsgItem {
    public static final int TYPE_RECEIVED = 0;// 接收消息
    public static final int TYPE_SENT = 1;// 发送消息

    private String content;

    private int type;

    public MsgItem(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public static int getTypeReceived() {
        return TYPE_RECEIVED;
    }

    public static int getTypeSent() {
        return TYPE_SENT;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}