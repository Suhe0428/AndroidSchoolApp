package com.example.asus.action;

import com.example.asus.dao.CommentDao;
import com.example.asus.vo.Comment;
import com.example.asus.vo.Post;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Wux
 * @ClssName CommentAction
 * @Description
 * @date 2019/1/16 16:53
 */
public class CommentAction extends ActionSupport {
    private CommentDao commentDao=new CommentDao();
    private int comment_post_id;

    private String json;

    public void setComment_post_id(String comment_post_id) {
        this.comment_post_id = Integer.parseInt(comment_post_id);
    }

    public void setJson(String json) {
        this.json = json;
    }

    /**
     * 查询与指定post相关的评论
     * @return
     * @throws IOException
     */
    public String selectCommentByPostId() throws IOException {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer=response.getWriter();

        List<Comment> commentList=commentDao.selectCommentByPostId(comment_post_id);
        Gson gson=new Gson();
        String commentList_json=gson.toJson(commentList);

        System.out.println(commentList_json);

        writer.write(commentList_json);
        writer.flush();
        return null;
    }

    /**
     * 添加评论
     * @return
     * @throws IOException
     */
    public String addComment() throws IOException {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer=response.getWriter();

        Comment comment=new Gson().fromJson(json,Comment.class);
        boolean isAdd=commentDao.addComment(comment);

        writer.write(Boolean.toString(isAdd));
        writer.flush();
        return null;
    }
}
