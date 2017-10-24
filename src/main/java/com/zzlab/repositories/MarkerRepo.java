package com.zzlab.repositories;

import com.zzlab.models.Marker;

import java.util.List;

public interface MarkerRepo {

    Marker create(String email, String password);

    List<Marker> getAll();

    List<Marker> getAll(int limit);

    Marker get(long id);

    Marker getByEmail(String email);

    Marker update(long id);

    void delete(long id);
}
