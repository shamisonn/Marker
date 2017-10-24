package com.zzlab;

import com.zzlab.repoImpl.MarkerRepoImpl;
import com.zzlab.services.MarkerService;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

import static spark.Spark.path;

public class Main {

    public static void main(String[] args) {

        path("/markers", () -> new MarkerService(new MarkerRepoImpl()));

    }

    public static String render(Map<String, Object> model, String templatePath) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
