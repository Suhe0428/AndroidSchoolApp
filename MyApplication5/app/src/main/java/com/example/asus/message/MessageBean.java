package com.example.asus.message;

public class MessageBean {
    /**
     * 头像
     * 标题
     * 时间
     * 内容 */
    public int message_source_img;//来信人头像
    public String message_source;//来信人昵称
    public String message_time;//来信时间
    public String message_content;//消息内容

    public MessageBean(int message_source_img, String message_source, String message_time, String message_content) {
        this.message_source_img = message_source_img;
        this.message_source = message_source;
        this.message_time = message_time;
        this.message_content = message_content;
    }

    public MessageBean() {
    }

    public int getMessage_source_img() {
        return message_source_img;
    }

    public void setMessage_source_img(int message_source_img) {
        this.message_source_img = message_source_img;
    }

    public String getMessage_source() {
        return message_source;
    }

    public void setMessage_source(String message_source) {
        this.message_source = message_source;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }
}
