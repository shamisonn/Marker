package com.zzlab;

import com.zzlab.jwt.AuthManager;
import com.zzlab.repoImpl.DBManager;
import com.zzlab.repoImpl.MarkerRepoImpl;
import com.zzlab.repoImpl.ProblemRepoImpl;
import com.zzlab.repositories.MarkerRepo;
import com.zzlab.repositories.ProblemRepo;
import com.zzlab.services.AuthService;
import com.zzlab.services.MarkerService;
import com.zzlab.services.ProblemService;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        String dbPath = "sqlite.db";
        String dbUser = "hoge";
        String dbPass = "fuga";

        AuthManager manager = new AuthManager("aa");
        manager.scheduledCleanBlackList();
        DBManager dbManager = new DBManager(dbPath, dbUser, dbPass);
        MarkerRepo markerRepo = new MarkerRepoImpl(dbManager);
        ProblemRepo problemRepo = new ProblemRepoImpl(dbManager);

        path("", () -> new AuthService(markerRepo, manager));

        before("/markers", manager.authBefore);
        before("/problems", manager.authBefore);

        path("/markers", () -> new MarkerService(markerRepo, manager));
        path("/problems", () -> new ProblemService(problemRepo));

        after("/markers", manager.authAfter);
        after("/problems", manager.authAfter);




    }
}
