package com.example.asus.dao;

import com.example.asus.util.JdbcUtil;
import com.example.asus.vo.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wux
 * @ClssName PlaceDao
 * @Description
 * @date 2019/1/18 11:40
 */
public class PlaceDao {
    /**
     * ¸ù¾ÝID²éÕÒplace
     * @param place_id
     * @return
     * @throws Exception
     */
    public Place findPlaceById(int place_id) throws Exception {
        Place place=new Place();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from place where place_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, place_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                place.setPlace_id(rs.getInt(1));
                place.setPlace_name(rs.getString(2));
            }
        } finally {
            JdbcUtil.free(rs, ps, conn);
        }
        return place;
    }
}
