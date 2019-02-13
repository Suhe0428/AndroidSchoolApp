package com.example.asus.action;

import com.example.asus.dao.TaskKeyWordDao;
import com.example.asus.vo.TaskKeyWord;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Wux
 * @ClssName TaskKeyWordAction
 * @Description
 * @date 2019/1/19 18:08
 */
public class TaskKeyWordAction extends ActionSupport {
    private TaskKeyWordDao taskKeyWordDao=new TaskKeyWordDao();

    private String task_keyword_name;

    private String json;

    public void setJson(String json) {
        this.json = json;
    }

    public void setTask_keyword_name(String task_keyword_name) {
        this.task_keyword_name = task_keyword_name;
    }

    /**
     * 查找所有TaskKeyWord
     * @return
     * @throws IOException
     */
    public String queryAllTaskKeyWord() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        List<TaskKeyWord> taskKeyWordList=taskKeyWordDao.queryAllTaskKeyWord();
        String jsonResult=new Gson().toJson(taskKeyWordList);

        writer.write(jsonResult);
        writer.flush();
        return null;
    }

    /**
     * 根据ID 查找TaskKeyWord
     * @return
     * @throws IOException
     */
    public String findTaskKeyWordById() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        TaskKeyWord taskKeyWordFromJson=new Gson().fromJson(json,TaskKeyWord.class);
        TaskKeyWord taskKeyWord=taskKeyWordDao.findTaskKeyWordById(taskKeyWordFromJson.getTask_keyword_id());
        String jsonResult=new Gson().toJson(taskKeyWord);

        writer.write(jsonResult);
        writer.flush();
        return null;
    }

    /**
     * 根据task_keyword_name查找ID
     * @return
     */
    public String findTaskKeywordIDByName() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        String task_keyword_id=taskKeyWordDao.findTaskKeywordIdByName(task_keyword_name);

        writer.write(task_keyword_id);
        writer.flush();
        return null;
    }
}
