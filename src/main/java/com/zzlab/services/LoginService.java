package com.zzlab.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zzlab.jwt.LoginManager;
import com.zzlab.models.Marker;
import com.zzlab.repositories.MarkerRepo;

import java.util.Objects;

import static spark.Spark.post;

public class LoginService {
    // TODO
    public LoginService(MarkerRepo repo, LoginManager manager) {
        Gson gson = new Gson();
        long id = 0;

        post("", (req, res) -> {
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


    }


}