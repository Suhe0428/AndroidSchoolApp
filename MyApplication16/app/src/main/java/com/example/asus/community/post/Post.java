package com.example.asus.community.post;

public class Post {
    private int post_id;//帖子ID
    private int post_source_id;//发帖人ID
    private String post_content;//帖子文字内容
    private String post_content_img;//帖子图片内容

    public Post() {
    }

    public Post(int post_id, int post_source_id, String post_content, String post_content_img) {
        this.post_id = post_id;
        this.post_source_id = post_source_id;
        this.post_content = post_content;
        this.post_content_img = post_content_img;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getPost_source_id() {
        return post_source_id;
    }

    public void setPost_source_id(int post_source_id) {
        this.post_source_id = post_source_id;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_content_img() {
        return post_content_img;
    }

    public void setPost_content_img(String post_content_img) {
        this.post_content_img = post_content_img;
    }
}
