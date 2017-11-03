package com.zzlab.services;

import com.google.gson.Gson;
import com.zzlab.jwt.AuthManager;
import com.zzlab.models.Marker;
import com.zzlab.repositories.MarkerRepo;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class MarkerService {
    private MarkerRepo markerRepo;

    public MarkerService(MarkerRepo markerRepo, AuthManager manager) {
        this.markerRepo = markerRepo;
        Gson gson = new Gson();

        get("", (req, res) -> {
            List<Marker> markers = this.markerRepo.getAll();
            markers = markers.stream().peek(marker -> marker.setPassword(null)).collect(Collectors.toList());
            return gson.toJson(markers);
        });

        get("/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            Marker marker = this.markerRepo.get(id);
            marker.setPassword(null);
            return gson.toJson(marker);
        });

        put("/:id", (req, res) -> {
            if (Long.parseLong(req.params(":id")) != manager.getUserId(req)) {
                res.status(403);
                return "{\"message\":\"You can't use this api, because request ID does not match your ID.\"}";
            }
            Marker m = gson.fromJson(req.body(), Marker.class);
            m.setId(Long.parseLong(req.params(":id")));
            Marker marker = this.markerRepo.update(m);
            marker.setPassword(null);
            return gson.toJson(marker);
        });

        delete("/:id", (req, res) -> {
            if (Long.parseLong(req.params(":id")) != manager.getUserId(req)) {
                res.status(403);
                return "{\"message\":\"You can't use this api, because request ID does not match your ID.\"}";
            }
            long id = Long.parseLong(req.params(":id"));
            Marker marker = this.markerRepo.get(id);
            this.markerRepo.delete(id);
            marker.setPassword(null);
            return gson.toJson(marker);
        });
    }
}
