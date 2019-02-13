package com.example.asus.action;

import com.example.asus.dao.TargetDao;
import com.example.asus.vo.Target;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Wux
 * @ClssName TargetAction
 * @Description
 * @date 2019/1/17 23:36
 */
public class TargetAction extends ActionSupport {
    private TargetDao targetDao=new TargetDao();

    private int target_id;

    public void setTarget_id(String target_id) {
        this.target_id = Integer.parseInt(target_id);
    }

    /**
     * 根据ID查找target
     * @return
     * @throws Exception
     */
    public String findTargetById() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        Target target=targetDao.findTargetById(target_id);
        Gson gson=new Gson();
        String json=gson.toJson(target);

        writer.write(json);
        writer.flush();
        return null;
    }
}
