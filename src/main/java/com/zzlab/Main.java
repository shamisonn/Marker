package com.zzlab;

import com.zzlab.jwt.AuthManager;
import com.zzlab.repoImpl.MarkerRepoImpl;
import com.zzlab.repositories.MarkerRepo;
import com.zzlab.services.AuthService;
import com.zzlab.services.MarkerService;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        String dbPath = "sqlite.db";
        String dbUser = "hoge";
        String dbPass = "fuga";

        AuthManager manager = new AuthManager("aa");
        manager.scheduledCleanBlackList();
        MarkerRepo markerRepo = new MarkerRepoImpl(dbPath, dbUser, dbPass);

        path("", () -> new AuthService(markerRepo, manager));

        before("/markers", manager.authBefore);
        path("/markers", () -> new MarkerService(markerRepo, manager));
        after("/markers", manager.authAfter);




    }
}
