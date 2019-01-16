package com.example.asus.dao;

import com.example.asus.util.JdbcUtil;
import com.example.asus.vo.Goods;
import com.example.asus.vo.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wux
 * @ClssName PostDao
 * @Description
 * @date 2019/1/15 16:08
 */
public class PostDao {

    /**
     * 获取所有post
     * @return
     */
    public List<Post> queryAllPost(){
        List<Post> postList=new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from post";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Post post=new Post();
                post.setPost_id(rs.getInt(1));
                post.setPost_source_id(rs.getInt(2));
                post.setPost_content(rs.getString(3));
                post.setPost_content_img(rs.getString(4));
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postList;
    }

    public Post findPostById(int post_id){
        Post post=new Post();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from post where post_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,post_id);
            rs = ps.executeQuery();
            while(rs.next()){
                post.setPost_id(rs.getInt(1));
                post.setPost_source_id(rs.getInt(2));
                post.setPost_content(rs.getString(3));
                post.setPost_content_img(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }
}
