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
                goods.setGoods_seller_id(rs.getInt(5));
                goods.setGoods_price(rs.getDouble(6));
                goods.setGoods_place_id(rs.getInt(7));
                goods.setGoods_image(rs.getString(8));
                goodsList.add(goods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(rs,ps,conn);
        }
        return goodsList;
    }

    /**
     * 删除指定Goods
     * @param goods_id
     * @return
     */
    public boolean deleteGoods(int goods_id){
        boolean isDelete=false;
        Connection conn=null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "delete from goods where goods_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,goods_id);
            ps.executeUpdate();
            isDelete=true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(null, ps, conn);
        }
        return  isDelete;

    }

    /**
     * 根据条件查询Goods
     * @param text
     * @return
     */
    public List<Goods> selectGoods(String text){
        List<Goods> goodsList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from goods where goods_name like '%"+text+"%'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Goods goods=new Goods();
                goods.setGoods_id(rs.getInt(1));
                goods.setGoods_name(rs.getString(2));
                goods.setGoods_info(rs.getString(3));
                goods.setGoods_target_id(rs.getInt(4));
                goods.setGoods_seller_id(rs.getInt(5));
                goods.setGoods_price(rs.getDouble(6));
                goods.setGoods_place_id(rs.getInt(7));
                goods.setGoods_image(rs.getString(8));
                goodsList.add(goods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(rs,ps,conn);
        }
        return goodsList;
    }


    /**
     * 根据条件查询Goods
     * @param target_id
     * @param goods_price
     * @return
     */
    public List<Goods> selectGoodsByCondition(int target_id,String goods_price){
        List<Goods> goodsList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from goods where goods_target_id=" + target_id + " and goods_price" + goods_price;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Goods goods=new Goods();
                goods.setGoods_id(rs.getInt(1));
                goods.setGoods_name(rs.getString(2));
                goods.setGoods_info(rs.getString(3));
                goods.setGoods_target_id(rs.getInt(4));
                goods.setGoods_seller_id(rs.getInt(5));
                goods.setGoods_price(rs.getDouble(6));
                goods.setGoods_place_id(rs.getInt(7));
                goods.setGoods_image(rs.getString(8));
                goodsList.add(goods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(rs,ps,conn);
        }
        return goodsList;
    }
}
