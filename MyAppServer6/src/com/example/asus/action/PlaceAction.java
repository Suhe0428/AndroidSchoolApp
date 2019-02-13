package com.example.asus.action;

import com.example.asus.dao.PlaceDao;
import com.example.asus.vo.Place;
import com.example.asus.vo.Target;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Wux
 * @ClssName PlaceAction
 * @Description
 * @date 2019/1/18 11:43
 */
public class PlaceAction extends ActionSupport{
    private PlaceDao placeDao=new PlaceDao();
    private int place_id;

    public void setPlace_id(String place_id) {
        this.place_id = Integer.parseInt(place_id);
    }

    public String findPlaceById() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");//防止中文乱码,放在response.getWriter()前
        PrintWriter writer = response.getWriter();

        Place place =placeDao.findPlaceById(place_id);
        Gson gson=new Gson();
        String json=gson.toJson(place);

        writer.write(json);
        writer.flush();
        return null;
    }
}
