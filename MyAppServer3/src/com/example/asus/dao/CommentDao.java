package com.example.asus.dao;

import com.example.asus.util.JdbcUtil;
import com.example.asus.vo.Comment;
import com.example.asus.vo.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wux
 * @ClssName CommentDao
 * @Description
 * @date 2019/1/16 11:26
 */
public class CommentDao {

    /**
     * 获取指定post的所有评论
     * @param comment_post_id
     * @return
     */
    public List<Comment> selectCommentByPostId(int comment_post_id){
        List<Comment> commentList=new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from comment where comment_post_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,comment_post_id);
            rs = ps.executeQuery();
            while(rs.next()){
                Comment comment=new Comment();
                comment.setComment_id(rs.getInt(1));
                comment.setComment_user_id(rs.getInt(2));
                comment.setComment_post_id(rs.getInt(3));
                comment.setComment_content(rs.getString(4));
                commentList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentList;
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    public boolean addComment(Comment comment){
        boolean isAdd=false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "insert into comment values (null,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,comment.getComment_user_id());
            ps.setInt(2,comment.getComment_post_id());
            ps.setString(3,comment.getComment_content());
            ps.executeUpdate();
            isAdd=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdd;
    }
}
