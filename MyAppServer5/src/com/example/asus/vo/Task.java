package com.example.asus.vo;

/**
 * @author Wux
 * @ClssName Task
 * @Description
 * @date 2019/1/19 13:55
 */
public class Task {
    private int task_id;
    private String task_title;
    private int task_keyword_id;
    private int task_state;
    private int task_user_id;
    private String task_user_phone;
    private int task_executor_id;
    private String task_executor_phone;
    private String task_time;
    private String task_place;
    private String task_content;
    private String task_img;
    private String task_publish_time;

    public Task() {
    }

    public Task(int task_id, String task_title, int task_keyword_id, int task_state, int task_user_id, String task_user_phone, int task_executor_id, String task_executor_phone, String task_time, String task_place, String task_content, String task_img, String task_publish_time) {
        this.task_id = task_id;
        this.task_title = task_title;
        this.task_keyword_id = task_keyword_id;
        this.task_state = task_state;
        this.task_user_id = task_user_id;
        this.task_user_phone = task_user_phone;
        this.task_executor_id = task_executor_id;
        this.task_executor_phone = task_executor_phone;
        this.task_time = task_time;
        this.task_place = task_place;
        this.task_content = task_content;
        this.task_img = task_img;
        this.task_publish_time = task_publish_time;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public int getTask_keyword_id() {
        return task_keyword_id;
    }

    public void setTask_keyword_id(int task_keyword_id) {
        this.task_keyword_id = task_keyword_id;
    }

    public int getTask_state() {
        return task_state;
    }

    public void setTask_state(int task_state) {
        this.task_state = task_state;
    }

    public int getTask_user_id() {
        return task_user_id;
    }

    public void setTask_user_id(int task_user_id) {
        this.task_user_id = task_user_id;
    }

    public String getTask_user_phone() {
        return task_user_phone;
    }

    public void setTask_user_phone(String task_user_phone) {
        this.task_user_phone = task_user_phone;
    }

    public int getTask_executor_id() {
        return task_executor_id;
    }

    public void setTask_executor_id(int task_executor_id) {
        this.task_executor_id = task_executor_id;
    }

    public String getTask_executor_phone() {
        return task_executor_phone;
    }

    public void setTask_executor_phone(String task_executor_phone) {
        this.task_executor_phone = task_executor_phone;
    }

    public String getTask_time() {
        return task_time;
    }

    public void setTask_time(String task_time) {
        this.task_time = task_time;
    }

    public String getTask_place() {
        return task_place;
    }

    public void setTask_place(String task_place) {
        this.task_place = task_place;
    }

    public String getTask_content() {
        return task_content;
    }

    public void setTask_content(String task_content) {
        this.task_content = task_content;
    }

    public String getTask_img() {
        return task_img;
    }

    public void setTask_img(String task_img) {
        this.task_img = task_img;
    }

    public String getTask_publish_time() {
        return task_publish_time;
    }

    public void setTask_publish_time(String task_publish_time) {
        this.task_publish_time = task_publish_time;
    }
}
