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
        String sql = "DROP TABLE IF EXISTS MARKERS;" +
                "CREATE TABLE MARKERS (id INTEGER PRIMARY KEY, name TEXT, password TEXT, role TEXT)";
        this.updateSQL(sql);
    }

    private void updateSQL(String sql) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
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

    private List<Marker> querySQL(String sql) {
        List<Marker> markerList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement stat = conn.createStatement();
            ResultSet set = stat.executeQuery(sql);
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
    public Marker create(String name, String password) {
        String sql = String.format(
                "INSERT INTO MARKERS(name, password, role) VALUES('%s', '%s', 'NOMAL')",
                name, password
        );
        this.updateSQL(sql);
        return this.getByName(name);
    }

    @Override
    public List<Marker> getAll() {
        String sql = "SELECT * FROM MARKERS";

        return this.querySQL(sql);
    }

    @Override
    public List<Marker> getAll(int limit, int offset) {
        String sql = String.format(
                "SELECT * FROM MARKERS LIMIT %d OFFSET %d",
                limit, offset
        );

        return this.querySQL(sql);
    }

    @Override
    public Marker get(long id) {
        String sql = String.format(
                "SELECT * FROM MARKERS WHERE id == %d",
                id
        );
        return this.querySQL(sql).get(0);
    }

    @Override
    public Marker getByName(String name) {
        String sql = String.format(
                "SELECT * FROM MARKERS WHERE name == '%s'",
                name
        );
        List<Marker> markers = this.querySQL(sql);
        if (markers.size() == 0) {
            return null;
        }
        return markers.get(0);
    }

    @Override
    public Marker update(Marker marker) {
        long id = marker.getId();
        String sql = String.format(
                "SELECT * FROM MARKERS WHERE id == %d",
                id
        );
        Marker old = this.querySQL(sql).get(0);
        if (marker.getName() == null) {
            marker.setName(old.getName());
        }
        if (marker.getPassword() == null) {
            marker.setPassword(old.getPassword());
        }
        if (marker.getRole() == null) {
            marker.setRole(old.getRole());
        }
        sql = String.format(
                "UPDATE MARKERS SET name = '%s', password = '%s', role = '%s' WHERE id == %d",
                marker.getName(),
                marker.getPassword(),
                marker.getRole().name(),
                marker.getId()
        );
        this.updateSQL(sql);
        return marker;
    }

    @Override
    public void delete(long id) {
        String sql = String.format(
                "DELETE FROM MARKERS WHERE id == %d",
                id
        );
        this.updateSQL(sql);
    }
}
