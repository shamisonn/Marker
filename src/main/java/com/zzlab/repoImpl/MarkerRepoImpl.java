package com.zzlab.repoImpl;

import com.zzlab.models.Marker;
import com.zzlab.models.Role;
import com.zzlab.repositories.MarkerRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MarkerのCRUD処理を記述する。
 * by sqlite3-jdbc
 */
public class MarkerRepoImpl implements MarkerRepo {
    private String dbUrl;
    private String dbUser;
    private String dbPass;

    public MarkerRepoImpl(String dbPath, String dbUser, String dbPass){
        this.dbUrl = "jdbc:sqlite:" + dbPath;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        this.initTable();
    }

    private void initTable() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement stat = conn.createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS MARKERS;");
            stat.executeUpdate("CREATE TABLE MARKERS (id INTEGER PRIMARY KEY, name TEXT, password TEXT, role TEXT);");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null)
                    conn.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public Marker create(String name, String password) {
        Marker marker = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement stat = conn.createStatement();
            String sql = String.format(
                    "INSERT INTO MARKERS(name, password, role) VALUES('%s', '%s', 'NOMAL')",
                    name, password
            );
            stat.executeUpdate(sql);
            marker = this.getByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null)
                    conn.close();

            } catch(SQLException e) {
                System.err.println(e);
            }
        }
        return marker;
    }

    @Override
    public List<Marker> getAll() {
        List<Marker> markerList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement stat = conn.createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM MARKERS");
            while (set.next()) {
                long id = set.getLong("id");
                String name = set.getString("name");
                String pass = set.getString("password");
                String role = set.getString("role");
                markerList.add(new Marker(id, name, pass, Role.valueOf(role)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null)
                    conn.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }

        return markerList;
    }

    @Override
    public List<Marker> getAll(int limit, int offset) {
        return null;
    }

    @Override
    public Marker get(long id) {
        return null;
    }

    @Override
    public Marker getByName(String name) {
        return null;
    }

    @Override
    public Marker update(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
