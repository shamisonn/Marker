package com.zzlab.repoImpl;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

public class DBManager {
    private String dbUrl;
    private String dbUser;
    private String dbPass;

    public DBManager(String dbPath, String dbUser, String dbPass) {
        this.dbUrl = "jdbc:sqlite:" + dbPath;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public void updateSQL(String sql) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public List querySQL(String sql, Function<ResultSet, List> binder) {
        List list = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement stat = conn.createStatement();
            ResultSet set = stat.executeQuery(sql);
            list = binder.apply(set);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return list;
    }
}
