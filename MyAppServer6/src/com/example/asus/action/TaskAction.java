package com.example.asus.action;

import com.example.asus.dao.TaskDao;
import com.example.asus.vo.Task;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Wux
 * @ClssName TaskAction
 * @Description
 * @date 2019/1/19 15:01
 */
public class TaskAction extends ActionSupport {
    private TaskDao taskDao=new TaskDao();

    private String json;

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public void setJson(String json) {
        this.json = json;
    }

    /**
     * 查找所有Task
     * @return
     * @throws IOException
     */
    public String queryAllTask() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        List<Task> taskList=taskDao.queryAllTask();
        String jsonResult= new Gson().toJson(taskList);

        writer.write(jsonResult);
        writer.flush();
        return null;
    }

    /**
     * 根据ID查找Task
     * @return
     * @throws IOException
     */
    public String findTaskById() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        Task taskFromJson=new Gson().fromJson(json,Task.class);
        Task task=taskDao.findTaskById(taskFromJson.getTask_id());
        String jsonResult=new Gson().toJson(task);

        writer.write(jsonResult);
        writer.flush();
        return null;
    }

    //查找最新一条

    //根据用户ID查找

    /**
     * 添加Task
     * @return
     * @throws IOException
     */
    public String addTask() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        Task taskFromJson=new Gson().fromJson(json,Task.class);
        boolean isAdd=taskDao.addTask(taskFromJson);
        String jsonResult=Boolean.toString(isAdd);

        writer.write(jsonResult);
        writer.flush();
        return null;
    }
    /**
     * 删除指定Task
     * @return
     * @throws IOException
     */
    public String deleteTask() throws IOException {
        boolean isDelete = false;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        Task taskFromJson=new Gson().fromJson(json,Task.class);
        isDelete=taskDao.deleteTask(taskFromJson.getTask_id());
        String jsonResult=Boolean.toString(isDelete);

        writer.write(jsonResult);
        writer.flush();
        return null;
    }

    /**
     * 领取任务
     * @return
     * @throws IOException
     */
    public String updateTask() throws IOException {
        boolean isUpdate=false;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        Task task=new Gson().fromJson(json,Task.class);
        System.out.print(task.getTask_id());
        isUpdate=taskDao.updateTask(task,0);
        String jsonResult= Boolean.toString(isUpdate);

        writer.write(jsonResult);
        writer.flush();
        return null;
    }

    /**
     * 提交任务
     * @return
     * @throws IOException
     */
    public String commitTask() throws IOException {
        boolean isUpdate=false;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        Task task=new Gson().fromJson(json,Task.class);
        System.out.print(task.getTask_id());
        isUpdate=taskDao.updateTask(task,1);
        String jsonResult= Boolean.toString(isUpdate);

        writer.write(jsonResult);
        writer.flush();
        return null;
    }

    /**
     * 查找Task
     * @return
     * @throws IOException
     */
    public String selectTask() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();
        String result;
        List<Task> taskList=taskDao.selectTask(text);
        if(taskList.isEmpty()){
            result="false";
        }else{
            result=new Gson().toJson(taskList);
        }
        writer.write(result);
        writer.flush();
        return null;
    }
}
