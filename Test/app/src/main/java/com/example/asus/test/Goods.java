package com.example.asus.test;

public class Goods {
    private int id;
    private String name;
    private String target;
    private String price;
    private String seller;
    private String place;
    private int imageId;
    private String info;

    public Goods() { }

    public Goods(int id, String name, String target, String price, String seller, String place, int imageId,String info) {
        this.id = id;
        this.name = name;
        this.target = target;
        this.price = price;
        this.seller = seller;
        this.place = place;
        this.imageId = imageId;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
