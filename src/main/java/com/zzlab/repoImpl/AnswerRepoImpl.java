package com.zzlab.repoImpl;

import com.zzlab.models.Answer;
import com.zzlab.models.Problem;
import com.zzlab.repositories.AnswerRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AnswerRepoImpl implements AnswerRepo {
    private DBManager manager;

    AnswerRepoImpl(DBManager manager) {
        this.manager = manager;
        this.initTable();
    }

    private void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " +
                "ANSWERS (" +
                "id INTEGER PRIMARY KEY, " +
                "problemId INTEGER, " +
                "studentId TEXT, " +
                "answerDirPath TEXT, " +
                "submitDate TEXT, " +
                "markerId INTEGER, " +
                "point INTEGER, " +
                "markerComments TEXT, " +
                "markDate TEXT) ";
        this.manager.updateSQL(sql);
    }

    private Function<ResultSet, List> binder = set -> {
        List<Answer> answerList = new ArrayList<>();
        try {
            while (set.next()) {
                long id = set.getLong("id");
                ;
                long problemId = set.getLong("problemId");
                String studentId = set.getString("studentId");
                String answerDirPath = set.getString("answerDirPath");
                ZonedDateTime submitDate = ZonedDateTime.parse(set.getString("submitDate"));
                long markerId = set.getLong("markerId");
                int point = set.getInt("point");
                String markerComments = set.getString("markerComments");
                ZonedDateTime markDate = ZonedDateTime.parse(set.getString("markDate"));

                answerList.add(new Answer(id, problemId, studentId, answerDirPath, submitDate, markerId,
                        point, markerComments, markDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answerList;
    };

    @Override
    public List<Answer> createMulti(List<Answer> answers) {
        StringBuilder sql1 = new StringBuilder("INSERT INTO ANSWERS(problemId, studentId, answerDirPath, submitDate) " +
                "VALUES");
        for (Answer answer : answers) {
            sql1.append(
                    String.format("(%d, '%s', '%s', '%s'),",
                            answer.getProblemId(),
                            answer.getStudentId(),
                            answer.getAnswerDirPath(),
                            answer.getSubmitDate()
                    )
            );
        }
        sql1.deleteCharAt(sql1.length() - 1);
        this.manager.updateSQL(sql1.toString());
        String sql2 = "SELECT * FROM ANSWERS ORDER BY id DESC LIMIT " + answers.size();
        return (List<Answer>) this.manager.querySQL(sql2, this.binder);
    }

    @Override
    public Answer create(long problemId, long studentId, String answerDirPath, ZonedDateTime submitDate) {
        String sql1 = "INSERT INTO ANSWERS(problemId, studentId, answerDirPath, submitDate) VALUES" +
                String.format("(%d, '%s', '%s', '%s')", problemId, studentId, answerDirPath, submitDate);
        this.manager.updateSQL(sql1);
        String sql2 = "SELECT * FROM ANSWERS WHERE ROWID = last_insert_rowid()";
        return (Answer) this.manager.querySQL(sql2, this.binder).get(0);
    }

    @Override
    public List<Answer> getAllByProblem(long problemId) {
        String sql = "SELECT * FROM ANSWERS WHERE WHERE problemId == " + problemId;
        // WIP
        return null;
    }

    @Override
    public List<Answer> getAllByProblemSubject(String subject) {
        return null;
    }

    @Override
    public List<Answer> getAllByStudentId(String studentId) {
        return null;
    }

    @Override
    public List<Answer> getAllByTime(ZonedDateTime from, ZonedDateTime to) {
        return null;
    }

    @Override
    public Answer get(long id) {
        return null;
    }

    @Override
    public Answer getByTitle(String title) {
        return null;
    }

    @Override
    public Answer update(Problem problem) {
        return null;
    }

    @Override
    public Answer updateToMark(long id, long markId, int point, String markerComments,
                               ZonedDateTime markDate) {
        return null;
    }

    @Override
    public Answer delete(long id) {
        return null;
    }
}
