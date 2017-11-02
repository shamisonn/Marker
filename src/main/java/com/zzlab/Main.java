package com.zzlab;

import com.zzlab.jwt.LoginManager;
import com.zzlab.repoImpl.MarkerRepoImpl;
import com.zzlab.repositories.MarkerRepo;
import com.zzlab.services.LoginService;
import com.zzlab.services.MarkerService;

import static spark.Spark.path;

public class Main {

    public static void main(String[] args) {
        String dbPath = "sqlite.db";
        String dbUser = "hoge";
        String dbPass = "fuga";

        LoginManager manager = new LoginManager("aa");
        manager.scheduledCleanBlackList();
        MarkerRepo markerRepo = new MarkerRepoImpl(dbPath, dbUser, dbPass);
        path("/markers", () -> new MarkerService(markerRepo));
        path("/login", () -> new LoginService(markerRepo, manager));

    }
}
