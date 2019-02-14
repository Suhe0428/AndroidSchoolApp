package com.example.asus.util;

import java.sql.*;
import java.util.Properties;

public final class JdbcUtil {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    private static Properties pr = new Properties();

    private JdbcUtil() {
    }

    // 设计该工具类的静态初始化器中的代码，该代码在装入类时执行，且只执行一次
    static {
        try {
            pr.load(JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            driver = pr.getProperty("driver");
            url = pr.getProperty("url");
            user = pr.getProperty("username");
            password = pr.getProperty("password");
            Class.forName(driver);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // 设计获得连接对象的方法getConnection()
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // 设计释放结果集、语句和连接的方法free()
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        JdbcUtil.getConnection();
    }
}

