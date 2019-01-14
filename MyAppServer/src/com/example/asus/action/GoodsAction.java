package com.example.asus.action;

import com.example.asus.dao.GoodsDao;
import com.example.asus.vo.Goods;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GoodsAction extends ActionSupport {
    private GoodsDao goodsDao=new GoodsDao();

    public String queryAllGoods() throws IOException {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer=response.getWriter();

        List<Goods> goodsList=goodsDao.queryAllGoods();
        String goodsList_json=null;
        Gson gson=new Gson();
        goodsList_json=gson.toJson(goodsList);
        System.out.print(goodsList_json);

        writer.write(goodsList_json);
        writer.flush();
        return null;
    }
}
