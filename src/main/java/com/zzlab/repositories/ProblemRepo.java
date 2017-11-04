package com.zzlab.repositories;


import com.zzlab.models.Problem;

import java.time.ZonedDateTime;
import java.util.List;

public interface ProblemRepo {

    Problem create(String title, String description, String subject, ZonedDateTime date);

    List<Problem> getAll();

    List<Problem> getAll(int limit, int offset);

    Problem get(long id);

    Problem getByTitle(String title);

    Problem update(Problem problem);

    void delete(long id);
}
