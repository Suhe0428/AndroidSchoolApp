package com.example.asus.dao;

import com.example.asus.util.JdbcUtil;
import com.example.asus.vo.Target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wux
 * @ClssName TargetDao
 * @Description
 * @date 2019/1/17 23:32
 */
public class TargetDao {

    /**
     * ¸ù¾ÝID²éÕÒtarget
     * @param target_id
     * @return
     * @throws Exception
     */
    public Target findTargetById(int target_id) throws Exception {
        Target target=new Target();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from target where target_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, target_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                target.setTarget_id(rs.getInt(1));
                target.setTarget_name(rs.getString(2));
            }
        } finally {
            JdbcUtil.free(rs, ps, conn);
        }
        return target;
    }
}
