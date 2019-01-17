package com.example.asus.vo;

/**
 * @author Wux
 * @ClssName Target
 * @Description
 * @date 2019/1/17 23:31
 */
public class Target {
    private int target_id;
    private String target_name;

    public Target() {
    }

    public Target(int target_id, String target_name) {
        this.target_id = target_id;
        this.target_name = target_name;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }
}
