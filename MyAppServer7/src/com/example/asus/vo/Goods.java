package com.example.asus.vo;

public class Goods {
    private int goods_id;//编号，主键
    private String goods_name;//名字
    private String goods_info;//简介
    private int goods_target_id;//分类id
    private int goods_seller_id;//卖家账号
    private double goods_price;//价格
    private int goods_place_id;//交易地点id
    private String goods_image;//图片地址

    public Goods() {
    }

    public Goods(int goods_id, String goods_name, String goods_info, int goods_target_id, int goods_seller_id, double goods_price, int goods_place_id, String goods_image) {
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.goods_info = goods_info;
        this.goods_target_id = goods_target_id;
        this.goods_seller_id = goods_seller_id;
        this.goods_price = goods_price;
        this.goods_place_id = goods_place_id;
        this.goods_image = goods_image;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(String goods_info) {
        this.goods_info = goods_info;
    }

    public int getGoods_target_id() {
        return goods_target_id;
    }

    public void setGoods_target_id(int goods_target_id) {
        this.goods_target_id = goods_target_id;
    }

    public int getGoods_seller_id() {
        return goods_seller_id;
    }

    public void setGoods_seller_id(int goods_seller_id) {
        this.goods_seller_id = goods_seller_id;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_place_id() {
        return goods_place_id;
    }

    public void setGoods_place_id(int goods_place_id) {
        this.goods_place_id = goods_place_id;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }
}
