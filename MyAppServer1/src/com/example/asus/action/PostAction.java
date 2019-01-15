package com.example.asus.action;

import com.example.asus.dao.PostDao;
import com.example.asus.vo.Post;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wux
 * @ClssName PostAction
 * @Description
 * @date 2019/1/15 16:15
 */
public class PostAction extends ActionSupport {
    private PostDao postDao=new PostDao();

    /**
     * 获取全部post
     * @return
     * @throws IOException
     */
    public String queryAllPost() throws IOException {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer=response.getWriter();

        List<Post> postList=postDao.queryAllPost();
        Gson gson=new Gson();
        String postList_json=gson.toJson(postList);

        System.out.print(postList_json);

        writer.write(postList_json);
        writer.flush();
        return null;
    }
}
