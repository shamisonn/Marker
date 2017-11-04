package com.zzlab.repoImpl;

import com.zzlab.models.Marker;
import com.zzlab.models.Role;
import com.zzlab.repositories.MarkerRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * MarkerのCRUD処理を記述する。
 * by sqlite3-jdbc
 */
public class MarkerRepoImpl implements MarkerRepo {
    DBManager manager;

    public MarkerRepoImpl(DBManager manager) {
        this.manager = manager;
        this.initTable();
    }

    private void initTable() {
        String sql = "DROP TABLE IF EXISTS MARKERS;" +
                "CREATE TABLE MARKERS (id INTEGER PRIMARY KEY, name TEXT, password TEXT, role TEXT)";
        this.manager.updateSQL(sql);
    }

    private Function<ResultSet, List> binder = set -> {
        List<Marker> markerList = new ArrayList<>();
        try {
            while (set.next()) {
                long id = set.getLong("id");
                String name = set.getString("name");
                String pass = set.getString("password");
                String role = set.getString("role");
                markerList.add(new Marker(id, name, pass, Role.valueOf(role)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return markerList;
    };

    @Override
    public Marker create(String name, String password) {
        String sql = String.format(
                "INSERT INTO MARKERS(name, password, role) VALUES('%s', '%s', 'NOMAL')",
                name, password
        );
        this.manager.updateSQL(sql);
        return this.getByName(name);
    }

    @Override
    public List<Marker> getAll() {
        String sql = "SELECT * FROM MARKERS";
        return (List<Marker>) this.manager.querySQL(sql, this.binder);
    }

    @Override
    public List<Marker> getAll(int limit, int offset) {
        String sql = String.format(
                "SELECT * FROM MARKERS LIMIT %d OFFSET %d",
                limit, offset
        );

        return (List<Marker>) this.manager.querySQL(sql, this.binder);
    }

    @Override
    public Marker get(long id) {
        String sql = String.format(
                "SELECT * FROM MARKERS WHERE id == %d",
                id
        );
        return ((List<Marker>) this.manager.querySQL(sql, this.binder)).get(0);
    }

    @Override
    public Marker getByName(String name) {
        String sql = String.format(
                "SELECT * FROM MARKERS WHERE name == '%s'",
                name
        );
        List<Marker> markers = (List<Marker>) this.manager.querySQL(sql, this.binder);
        if (markers.size() == 0) {
            return null;
        }
        return markers.get(0);
    }

    @Override
    public Marker update(Marker marker) {
        Marker old = this.get(marker.getId());
        if (marker.getName() == null) {
            marker.setName(old.getName());
        }
        if (marker.getPassword() == null) {
            marker.setPassword(old.getPassword());
        }
        if (marker.getRole() == null) {
            marker.setRole(old.getRole());
        }
        String sql = String.format(
                "UPDATE MARKERS SET name = '%s', password = '%s', role = '%s' WHERE id == %d",
                marker.getName(),
                marker.getPassword(),
                marker.getRole().name(),
                marker.getId()
        );
        this.manager.updateSQL(sql);
        return marker;
    }

    @Override
    public void delete(long id) {
        String sql = String.format(
                "DELETE FROM MARKERS WHERE id == %d",
                id
        );
        this.manager.updateSQL(sql);
    }
}
