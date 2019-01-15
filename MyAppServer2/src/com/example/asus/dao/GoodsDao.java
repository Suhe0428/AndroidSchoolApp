package com.example.asus.dao;

import com.example.asus.util.JdbcUtil;
import com.example.asus.vo.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsDao {

    /**
     * 获取所有Goods
     * @return
     */
    public List<Goods> queryAllGoods(){
        List<Goods> goodsList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from goods";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Goods goods=new Goods();
                goods.setGoods_id(rs.getInt(1));
                goods.setGoods_name(rs.getString(2));
                goods.setGoods_info(rs.getString(3));
                goods.setGoods_target_id(rs.getInt(4));
                goods.setGoods_seller_id(rs.getString(5));
                goods.setGoods_price(rs.getDouble(6));
                goods.setGoods_place(rs.getString(7));
                goods.setGoods_image(rs.getString(8));
                goodsList.add(goods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goodsList;
    }
}
