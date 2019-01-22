package com.example.asus.vo;

public class Place {
    private int place_id;
    private String place_name;

    public Place() {
    }

    public Place(int place_id, String place_name) {
        this.place_id = place_id;
        this.place_name = place_name;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }
}
