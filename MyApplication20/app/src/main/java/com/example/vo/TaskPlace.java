package com.example.vo;

/**
 * @author Wux
 * @ClssName TaskPlace
 * @Description
 * @date 2019/1/19 15:02
 */
public class TaskPlace {
    private int task_place_id;
    private String task_place_name;

    public TaskPlace() {
    }

    public TaskPlace(int task_place_id, String task_place_name) {
        this.task_place_id = task_place_id;
        this.task_place_name = task_place_name;
    }

    public int getTask_place_id() {
        return task_place_id;
    }

    public void setTask_place_id(int task_place_id) {
        this.task_place_id = task_place_id;
    }

    public String getTask_place_name() {
        return task_place_name;
    }

    public void setTask_place_name(String task_place_name) {
        this.task_place_name = task_place_name;
    }
}
