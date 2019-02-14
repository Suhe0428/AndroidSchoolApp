package com.example.asus.action;

import com.example.asus.dao.GoodsDao;
import com.example.asus.dao.TargetDao;
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

    private TargetDao targetDao=new TargetDao();

    private String json;

    private String text;

    private String goods_classification;

    private String goods_price;

    public void setJson(String json) {
        this.json = json;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setGoods_classification(String goods_classification) {
        this.goods_classification = goods_classification;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    /**
     * 获取所有goods
     * @return
     * @throws IOException
     */
    public String queryAllGoods() throws IOException {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer=response.getWriter();

        List<Goods> goodsList=goodsDao.queryAllGoods();
        Gson gson=new Gson();
        String goodsList_json=gson.toJson(goodsList);

        writer.write(goodsList_json);
        writer.flush();
        return null;
    }

    /**
     * 删除指定Goods
     * @return
     * @throws IOException
     */
    public String deleteGoods() throws IOException {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer=response.getWriter();

        Goods goods=new Gson().fromJson(json,Goods.class);
        boolean isDelete=goodsDao.deleteGoods(goods.getGoods_id());

        writer.write(Boolean.toString(isDelete  ));
        writer.flush();
        return null;
    }

    /**
     * 搜索Goods
     * @return
     */
    public String selectGoods() throws IOException {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer=response.getWriter();
        String result;
        List<Goods> goodsList=goodsDao.selectGoods(text);
        if(goodsList.isEmpty()){
            result="false";
        }else{
            result=new Gson().toJson(goodsList);
        }
        writer.write(result);
        writer.flush();
        return null;
    }

    /**
     * 根据条件搜索Goods
     * @return
     */
    public String selectGoodsByCondition() throws Exception {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer=response.getWriter();

        int target_id=targetDao.findTargetIdByName(goods_classification);
        String result;
        List<Goods> goodsList=goodsDao.selectGoodsByCondition(target_id,goods_price);
        result=new Gson().toJson(goodsList);

        writer.write(result);
        writer.flush();
        return null;
    }
}
