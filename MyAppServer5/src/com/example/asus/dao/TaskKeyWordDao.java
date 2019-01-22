package com.example.asus.dao;

import com.example.asus.util.JdbcUtil;
import com.example.asus.vo.Task;
import com.example.asus.vo.TaskKeyWord;

import java.awt.image.Kernel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wux
 * @ClssName TaskKeyWordDao
 * @Description
 * @date 2019/1/19 17:26
 */
public class TaskKeyWordDao {

    /**
     * 获取所有TaskKeyWord
     * @return
     */
    public List<TaskKeyWord> queryAllTaskKeyWord(){
        List<TaskKeyWord> taskKeyWordList=new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from task_keyword";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
               TaskKeyWord taskKeyWord=new TaskKeyWord();
               taskKeyWord.setTask_keyword_id(rs.getInt(1));
               taskKeyWord.setTask_keyword_name(rs.getString(2));
               taskKeyWordList.add(taskKeyWord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(rs,ps,conn);
        }
        return taskKeyWordList;
    }

    /**
     * 根据ID查找TaskKeyWord
     * @param task_keyword_id
     * @return
     */
    public TaskKeyWord findTaskKeyWordById(int task_keyword_id){
        TaskKeyWord taskKeyWord=new TaskKeyWord();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from task_keyword where task_keyword_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,task_keyword_id);
            rs = ps.executeQuery();
            while(rs.next()){
                taskKeyWord.setTask_keyword_id(rs.getInt(1));
                taskKeyWord.setTask_keyword_name(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(rs,ps,conn);
        }
        return taskKeyWord;
    }

    public String findTaskKeywordIdByName(String task_keyword_name){
        String task_keyword_id=null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select task_keyword_id from task_keyword where task_keyword_name=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,task_keyword_name);
            rs = ps.executeQuery();
            while(rs.next()){
                task_keyword_id=Integer.toString(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(rs,ps,conn);
        }
        return task_keyword_id;
    }
}
