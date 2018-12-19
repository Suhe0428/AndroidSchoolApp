package com.example.asus.community;

public class Community {
    public String community_item_source;
    public int community_item_source_img;
    public String community_item_content;
    public String community_item_content_img;

    public Community() {
    }

    public Community(String community_item_source, int community_item_source_img, String community_item_content, String community_item_content_img) {
        this.community_item_source = community_item_source;
        this.community_item_source_img = community_item_source_img;
        this.community_item_content = community_item_content;
        this.community_item_content_img = community_item_content_img;
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

    public String getCommunity_item_content_img() {
        return community_item_content_img;
    }

    public void setCommunity_item_content_img(String community_item_content_img) {
        this.community_item_content_img = community_item_content_img;
    }
}
