package com.zzlab;

import com.zzlab.repoImpl.MarkerRepoImpl;
import com.zzlab.services.MarkerService;

import static spark.Spark.path;

public class Main {

    public static void main(String[] args) {
        String dbPath = "sqlite.db";
        String dbUser = "hoge";
        String dbPass = "fuga";

        path("/markers", () -> new MarkerService(new MarkerRepoImpl(dbPath, dbUser, dbPass)));

    }
}
