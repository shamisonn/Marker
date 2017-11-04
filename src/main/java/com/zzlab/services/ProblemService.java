package com.zzlab.services;

import com.google.gson.Gson;
import com.zzlab.models.Problem;
import com.zzlab.repositories.ProblemRepo;

import java.util.List;

import static spark.Spark.*;

public class ProblemService {
    ProblemRepo repo;

    public ProblemService(ProblemRepo problemRepo) {
        this.repo = problemRepo;
        Gson gson = new Gson();

        get("", (req, res) -> {
            List<Problem> problems = this.repo.getAll();
            return gson.toJson(problems);
        });

        get("/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            Problem problem = repo.get(id);
            return gson.toJson(problem);
        });

        put("/:id", (req, res) -> {
            Problem p = gson.fromJson(req.body(), Problem.class);
            p.setId(Long.parseLong(req.params(":id")));
            Problem problem = this.repo.update(p);
            return gson.toJson(problem);
        });

        delete("/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            Problem problem = this.repo.get(id);
            this.repo.delete(id);
            return gson.toJson(problem);
        });
    }
}
