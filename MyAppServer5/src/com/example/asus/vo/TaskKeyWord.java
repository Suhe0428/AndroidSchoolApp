package com.example.asus.vo;

/**
 * @author Wux
 * @ClssName TaskKeyWord
 * @Description
 * @date 2019/1/19 15:02
 */
public class TaskKeyWord {
    private int task_keyword_id;
    private String task_keyword_name;

    public TaskKeyWord() {
    }

    public TaskKeyWord(int task_keyword_id, String task_keyword_name) {
        this.task_keyword_id = task_keyword_id;
        this.task_keyword_name = task_keyword_name;
    }

    public int getTask_keyword_id() {
        return task_keyword_id;
    }

    public void setTask_keyword_id(int task_keyword_id) {
        this.task_keyword_id = task_keyword_id;
    }

    public String getTask_keyword_name() {
        return task_keyword_name;
    }

    public void setTask_keyword_name(String task_keyword_name) {
        this.task_keyword_name = task_keyword_name;
    }
}
