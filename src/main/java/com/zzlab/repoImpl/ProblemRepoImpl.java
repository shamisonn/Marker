package com.zzlab.repoImpl;

import com.zzlab.models.Problem;
import com.zzlab.repositories.ProblemRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProblemRepoImpl implements ProblemRepo {
    DBManager manager;

    public ProblemRepoImpl(DBManager manager) {
        this.manager = manager;
        this.initTable();
    }

    private void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " +
                "PROBLEMS(" +
                "id INTEGER PRIMARY KEY, " +
                "title TEXT, " +
                "description TEXT, " +
                "subject TEXT, " +
                "date TEXT)";
        this.manager.updateSQL(sql);
    }

    private Function<ResultSet, List> binder = set -> {
        List<Problem> problemList = new ArrayList<>();
        try {
            while (set.next()) {
                long id = set.getLong("id");
                String title = set.getString("title");
                String description = set.getString("description");
                String subject = set.getString("subject");
                ZonedDateTime date = ZonedDateTime.parse(set.getString("date"));
                problemList.add(new Problem(id, title, description, subject, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return problemList;
    };

    @Override
    public Problem create(String title, String description, String subject, ZonedDateTime date) {
        String sql = String.format(
                "INSERT INTO PROBLEMS(title, description, subject, date) " +
                        "VALUES('%s', '%s', '%s', '%s')",
                title, description, subject, date
        );
        this.manager.updateSQL(sql);
        return this.getByTitle(title);
    }

    @Override
    public List<Problem> getAll() {
        String sql = "SELECT * FROM PROBLEMS";
        return (List<Problem>) this.manager.querySQL(sql, this.binder);
    }

    @Override
    public List<Problem> getAll(int limit, int offset) {
        String sql = String.format(
                "SELECT * FROM PROBLEMS LIMIT %d OFFSET %d",
                limit, offset
        );

        return (List<Problem>) this.manager.querySQL(sql, this.binder);
    }

    @Override
    public Problem get(long id) {
        String sql = String.format(
                "SELECT * FROM PROBLEMS WHERE id == %d",
                id
        );
        return ((List<Problem>) this.manager.querySQL(sql, this.binder)).get(0);
    }

    @Override
    public Problem getByTitle(String title) {
        String sql = String.format(
                "SELECT * FROM PROBLEMS WHERE title == '%s'",
                title
        );
        List<Problem> problems = (List<Problem>) this.manager.querySQL(sql, this.binder);
        if (problems.size() == 0) {
            return null;
        }
        return problems.get(0);
    }

    @Override
    public Problem update(Problem problem) {
        Problem old = this.get(problem.getId());
        if (problem.getTitle() == null) {
            problem.setTitle(old.getTitle());
        }
        if (problem.getDescription() == null) {
            problem.setDescription(old.getDescription());
        }
        if (problem.getSubject() == null) {
            problem.setSubject(old.getSubject());
        }
        if (problem.getDate() == null) {
            problem.setDate(old.getDate());
        }

        String sql = String.format(
                "UPDATE MARKERS SET title = '%s', description = '%s', subject = '%s', date = '%s' " +
                        "WHERE id == %d",
                problem.getTitle(),
                problem.getDescription(),
                problem.getSubject(),
                problem.getDate()
        );
        this.manager.updateSQL(sql);
        return problem;
    }

    @Override
    public void delete(long id) {
        String sql = String.format(
                "DELETE FROM PROBLEMS WHERE id == %d",
                id
        );
        this.manager.updateSQL(sql);
    }
}
