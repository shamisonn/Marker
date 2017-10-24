package com.zzlab.services;

import com.zzlab.Main;
import com.zzlab.models.Marker;
import com.zzlab.repositories.MarkerRepo;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class MarkerService {
    private MarkerRepo markerRepo;

    public MarkerService(final MarkerRepo markerRepo) {
        this.markerRepo = markerRepo;

        get("", (req, res) -> {
            List<Marker> markers = markerRepo.getAll();
            Map<String, Object> vars = new HashMap<>();
            vars.put("markers", markers);

            return Main.render(vars, "./markers/index.vm");
        });

        get("/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            Marker marker = markerRepo.get(id);
            return marker.getName();
        });

    }

}
