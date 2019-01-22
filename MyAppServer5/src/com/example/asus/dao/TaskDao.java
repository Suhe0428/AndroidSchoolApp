package com.example.asus.dao;

import com.example.asus.util.JdbcUtil;
import com.example.asus.vo.Post;
import com.example.asus.vo.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wux
 * @ClssName TaskDao
 * @Description
 * @date 2019/1/19 15:31
 */
public class TaskDao {

    /**
     * 获取所有Task
     * @return
     */
    public List<Task> queryAllTask(){
        List<Task> taskList=new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from task";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Task task=new Task();
                task.setTask_id(rs.getInt(1));
                task.setTask_title(rs.getString(2));
                task.setTask_keyword_id(rs.getInt(3));
                task.setTask_state(rs.getInt(4));
                task.setTask_user_id(rs.getInt(5));
                task.setTask_user_phone(rs.getString(6));
                task.setTask_executor_id(rs.getInt(7));
                task.setTask_executor_phone(rs.getString(8));
                task.setTask_time(rs.getString(9));
                task.setTask_place(rs.getString(10));
                task.setTask_content(rs.getString(11));
                task.setTask_img(rs.getString(12));
                task.setTask_publish_time(rs.getString(13));
                taskList.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    /**
     * 根据ID查找Task
     * @param task_id
     * @return
     */
    public Task findTaskById(int task_id){
        Task task=new Task();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from post where task_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,task_id);
            rs = ps.executeQuery();
            while(rs.next()){
                task.setTask_id(rs.getInt(1));
                task.setTask_title(rs.getString(2));
                task.setTask_keyword_id(rs.getInt(3));
                task.setTask_state(rs.getInt(4));
                task.setTask_user_id(rs.getInt(5));
                task.setTask_user_phone(rs.getString(6));
                task.setTask_executor_id(rs.getInt(7));
                task.setTask_executor_phone(rs.getString(8));
                task.setTask_time(rs.getString(9));
                task.setTask_place(rs.getString(10));
                task.setTask_content(rs.getString(11));
                task.setTask_img(rs.getString(12));
                task.setTask_publish_time(rs.getString(13));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  task;
    }


    /**
     * 添加Task
     * @param task
     * @return
     */
    public boolean addTask(Task task){
        boolean isAdd=false;
        Connection conn=null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "insert into task  values (null,?,?,?,?,?,?,?,?,?,?,?,?) ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,task.getTask_title());
            ps.setInt(2,task.getTask_keyword_id());
            ps.setInt(3,task.getTask_state());
            ps.setInt(4,task.getTask_user_id());
            ps.setString(5,task.getTask_user_phone());
            ps.setInt(6,task.getTask_executor_id());
            ps.setString(7,task.getTask_executor_phone());
            ps.setString(8,task.getTask_time());
            ps.setString(9,task.getTask_place());
            ps.setString(10,task.getTask_content());
            ps.setString(11,task.getTask_img());
            ps.setString(12,task.getTask_publish_time());
            ps.executeUpdate();
            isAdd=true;;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(null, ps, conn);
        }
        return isAdd;
    }

    /**
     * 删除指定Task
     * @param task_id
     * @return
     */
    public boolean deleteTask(int task_id){
        boolean isDelete=false;
        Connection conn=null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "delete from task where task_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,task_id);
            ps.executeUpdate();
            isDelete=true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(null, ps, conn);
        }
        return  isDelete;
    }

    /**
     * 修改Task
     * @param task
     * @return
     */
    public boolean updateTask(Task task,int state){
        boolean isUpdate=false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "update task set task_state=? where task_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,state);
            ps.setInt(2,task.getTask_id());
            ps.executeUpdate();
            isUpdate=true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(null, ps, conn);
        }
        return isUpdate;
    }
}
