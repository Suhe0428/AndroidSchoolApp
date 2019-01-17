package com.example.asus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.asus.util.JdbcUtil;
import com.example.asus.vo.User;

public class UserDao {
	/**
	 * 查找用户是否存在
	 * isFindUserByPhone():boolean
	 * input : user_phone
	 * return : isfind
	 * */
	public boolean isFindUserByPhone(String user_phone) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isfind=false;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from user where user_phone=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_phone);
			rs = ps.executeQuery();
			if (rs.next()) {
				isfind=true;
			}
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return isfind;
	}
	
	/**
	 * 根据ID查找user
	 * FindUserById():User
	 * input : user_id
	 * return : user
	 * */
	public User findUserById(int user_id) throws Exception{
		User user=new User();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from user where user_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				user.setUser_id(rs.getInt(1));
				user.setUser_name(rs.getString(2));
				user.setUser_phone(rs.getString(3));
				user.setUser_password(rs.getString(4));
			}
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return user;
	}

	/**
	 * 根据账号查找user
	 * @param user_phone
	 * @return
	 * @throws Exception
	 */
	public User findUserByPhone(String user_phone) throws Exception{
		User user=new User();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from user where user_phone=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_phone);
			rs = ps.executeQuery();
			if (rs.next()) {
				user.setUser_id(rs.getInt(1));
				user.setUser_name(rs.getString(2));
				user.setUser_phone(rs.getString(3));
				user.setUser_password(rs.getString(4));
			}
		} finally {
			JdbcUtil.free(rs, ps, conn);
		}
		return user;
	}

	/**
	 * 添加用户
	 * AddUser():boolean
	 * input : user
	 * return : isRegister*/
	public boolean addUser(User user) throws Exception{
		Connection conn=null;
		PreparedStatement ps = null;
		boolean isRegister=false;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "insert into user  values (null,null,?,?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_phone());
			ps.setString(2, user.getUser_password());
			ps.executeUpdate();
			isRegister=true;
		} finally {
			JdbcUtil.free(null, ps, conn);
		}
		return isRegister;
	}

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateUser(User user) throws Exception{
		boolean isUpdate=false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "update user set user_name=?,user_password=? where user_phone=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_name());
			ps.setString(2, user.getUser_password());
			ps.setString(3,user.getUser_phone());
			ps.executeUpdate();
			isUpdate=true;
		} finally {
			JdbcUtil.free(null, ps, conn);
		}
		return isUpdate;
	}
}
