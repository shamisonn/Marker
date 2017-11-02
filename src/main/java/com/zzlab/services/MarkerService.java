package com.zzlab.services;

import com.google.gson.Gson;
import com.zzlab.models.Marker;
import com.zzlab.repositories.MarkerRepo;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class MarkerService {
    private MarkerRepo markerRepo;

    public MarkerService(final MarkerRepo markerRepo) {
        this.markerRepo = markerRepo;
        Gson gson = new Gson();

        get("", (req, res) -> {

            List<Marker> markers = this.markerRepo.getAll();
            return gson.toJson(markers);
        });

        get("/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            Marker marker = this.markerRepo.get(id);
            return gson.toJson(marker);
        });

        post("", (req, res) -> {
            Marker m = gson.fromJson(req.body(), Marker.class);
            Marker marker = this.markerRepo.create(m.getName(), m.getPassword());
            return gson.toJson(marker);
        });
    }
}
