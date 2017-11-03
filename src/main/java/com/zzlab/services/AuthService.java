package com.zzlab.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zzlab.jwt.AuthManager;
import com.zzlab.models.Marker;
import com.zzlab.repositories.MarkerRepo;

import java.util.Objects;

import static spark.Spark.post;

public class AuthService {
    // TODO
    public AuthService(MarkerRepo repo, AuthManager manager) {
        Gson gson = new Gson();
        long id = 0;

        post("/signup", (req, res) -> {
            Marker m = gson.fromJson(req.body(), Marker.class);
            if (repo.getByName(m.getName()) != null) {
                res.status(403);
                return "{\"message\": \"the name is already used.\"}";
            }
            Marker marker = repo.create(m.getName(), m.getPassword());
            marker.setPassword(null);
            return gson.toJson(marker);
        });

        post("/login", (req, res) -> {
            JsonObject object = gson.fromJson(req.body(), JsonObject.class);
            String name = object.get("name").getAsString();
            String pass = object.get("password").getAsString();

            Marker marker = repo.getByName(name);

            if (!Objects.equals(pass, marker.getPassword())) {
                return "{\"message\": \"password is not valid.\"}";
            } else {
                res.header("Authorization", "Bearer " + manager.createToken(marker.getId()));
                return "{\"message\": \"welcome! check header\"}";
            }
        });

        post("/logout", (req, res) -> {
            if (req.headers().contains("Authorization")) {
                manager.invalidateToken(req.headers("Authorization").split(" ")[1]);
                return "{\"message\": \"success\"}";
            }
            return "{\"message\": \"You already logout.\"}";
        });



    }


}