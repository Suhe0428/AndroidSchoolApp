package com.example.asus.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.apache.struts2.ServletActionContext;
import com.example.asus.dao.UserDao;
import com.example.asus.vo.User;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserAction extends ActionSupport {
    private UserDao userDao = new UserDao();
    private int user_id;
    private String user_name;
    private String user_phone;
    private String user_password;

    public void setUser_id(String user_id) {
        this.user_id = Integer.parseInt(user_id);
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    /**
     * 登录
     */
    public String login() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String flag = "";
        if (userDao.isFindUserByPhone(user_phone)) {
            if (user_password.equals(userDao.findUserByPhone(user_phone).getUser_password())) {//用户名，密码正确
                flag = "yes";
            } else {//密码错误
                flag = "password is wrong";
            }
        } else {//用户名错误
            flag = "user is wrong";
        }
        writer.write(flag);
        writer.flush();
        return null;
    }

    /**
     * 注册
     */
    public String register() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String flag = "";
        if (!userDao.isFindUserByPhone(user_phone)) {
            User user = new User();
            user.setUser_phone(user_phone);
            user.setUser_password(user_password);
            if (userDao.addUser(user)) {
                flag = "register successfully";
            }
        } else {
            flag = "account existed";
        }
        writer.write(flag);
        writer.flush();
        return null;
    }

    /**
     * 根据ID查询User
     * @return
     * @throws Exception
     */
    public String findUserById() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();
        User newUser = userDao.findUserById(user_id);
        Gson gson=new Gson();
        String jsonObject=gson.toJson(newUser);
        writer.write(jsonObject);
        writer.flush();
        return null;
    }

    /**
     * 根据Phone查询User
     * @return
     * @throws Exception
     */
    public String findUserByPhone() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();
        UserDao uDao = new UserDao();
        User newUser = uDao.findUserByPhone(user_phone);
        Gson gson=new Gson();
        String jsonObject=gson.toJson(newUser);
        writer.write(jsonObject);
        writer.flush();
        return null;
    }

    /**
     * 修改用户信息
     */
    public String updateUser() throws Exception {
        String isUpdate = null;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        User user = userDao.findUserById(user_id);
        user.setUser_name(user_name);
        if (userDao.updateUser(user)) {
            isUpdate = "true";
        } else {
            isUpdate = "false";
        }
        writer.write(isUpdate);
        writer.flush();
        return null;
    }

}
