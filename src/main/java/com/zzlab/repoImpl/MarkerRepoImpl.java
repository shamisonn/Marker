package com.zzlab.repoImpl;

import com.zzlab.models.Marker;
import com.zzlab.models.Role;
import com.zzlab.repositories.MarkerRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * MarkerのCRUD処理を記述する。
 * by sqlite3-jdbc
 */
public class MarkerRepoImpl implements MarkerRepo {

    public MarkerRepoImpl(){

    }

    @Override
    public Marker create(String name, String password) {
        return null;
    }

    @Override
    public List<Marker> getAll() {
        List<Marker> markers = new ArrayList<>();
        markers.add(new Marker(1, "shami", "pass1", Role.ADMIN));
        markers.add(new Marker(2, "yanagi", "pass2", Role.NOMAL));
        return markers;
    }

    @Override
    public List<Marker> getAll(int limit) {
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
