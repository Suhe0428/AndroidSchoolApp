package com.example.asus.community;

public class Community {
    public String community_item_source;//发帖人名称
    public int community_item_source_img;//发帖人头像
    public String community_item_content;//帖子文字内容
    public int community_item_content_img;//帖子图片内容
    public boolean community_item_isfav;//是否点赞
    public int community_item_fav_num;//点赞数

    public Community() {
    }

    public Community(String community_item_source, int community_item_source_img, String community_item_content, int community_item_content_img, boolean community_item_isfav, int community_item_fav_num) {
        this.community_item_source = community_item_source;
        this.community_item_source_img = community_item_source_img;
        this.community_item_content = community_item_content;
        this.community_item_content_img = community_item_content_img;
        this.community_item_isfav = community_item_isfav;
        this.community_item_fav_num = community_item_fav_num;
    }

    public int getCommunity_item_fav_num() {
        return community_item_fav_num;
    }

    public void setCommunity_item_fav_num(int community_item_fav_num) {
        this.community_item_fav_num = community_item_fav_num;
    }

    public boolean getCommunity_item_isfav() {
        return community_item_isfav;
    }

    public void setCommunity_item_isfav(boolean community_item_isfav) {
        this.community_item_isfav = community_item_isfav;
    }

    public String getCommunity_item_source() {
        return community_item_source;
    }

    public void setCommunity_item_source(String community_item_source) {
        this.community_item_source = community_item_source;
    }

    public int getCommunity_item_source_img() {
        return community_item_source_img;
    }

    public void setCommunity_item_source_img(int community_item_source_img) {
        this.community_item_source_img = community_item_source_img;
    }

    public String getCommunity_item_content() {
        return community_item_content;
    }

    public void setCommunity_item_content(String community_item_content) {
        this.community_item_content = community_item_content;
    }

    public int getCommunity_item_content_img() {
        return community_item_content_img;
    }

    public void setCommunity_item_content_img(int community_item_content_img) {
        this.community_item_content_img = community_item_content_img;
    }
}
