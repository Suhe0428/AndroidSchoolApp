package com.example.asus.community.comment;

public class Comment {
    private int comment_id;//ID
    private int comment_user_id;//评论人账号
    private int comment_post_id;//被评论的贴
    private String comment_content;//评论内容

    public Comment() {
    }

    public Comment(int comment_user_id, int comment_post_id, String comment_content) {
        this.comment_user_id = comment_user_id;
        this.comment_post_id = comment_post_id;
        this.comment_content = comment_content;
    }

    public Comment(int comment_id, int comment_user_id, int comment_post_id, String comment_content) {
        this.comment_id = comment_id;
        this.comment_user_id = comment_user_id;
        this.comment_post_id = comment_post_id;
        this.comment_content = comment_content;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(int comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public int getComment_post_id() {
        return comment_post_id;
    }

    public void setComment_post_id(int comment_post_id) {
        this.comment_post_id = comment_post_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
}
