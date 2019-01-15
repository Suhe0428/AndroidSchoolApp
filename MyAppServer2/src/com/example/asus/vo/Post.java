package com.example.asus.vo;

/**
 * @author Wux
 * @ClssName Post
 * @Description
 * @date 2019/1/15 16:03
 */
public class Post {
    private int post_id;
    private String post_source_phone;//����������
    private String post_content;//������������
    private String post_content_img;//����ͼƬ����

    public Post() {
    }

    public Post(int post_id, String post_source_phone, String post_content, String post_content_img) {
        this.post_id = post_id;
        this.post_source_phone = post_source_phone;
        this.post_content = post_content;
        this.post_content_img = post_content_img;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPost_source_phone() {
        return post_source_phone;
    }

    public void setPost_source_phone(String post_source_phone) {
        this.post_source_phone = post_source_phone;
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
