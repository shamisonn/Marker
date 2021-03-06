package com.zzlab.repositories;

import com.zzlab.models.Answer;
import com.zzlab.models.Problem;

import java.time.ZonedDateTime;
import java.util.List;

public interface AnswerRepo {

    List<Answer> createMulti(List<Answer> answers);

    Answer create(long problemId, long studentId, String answerDirPath, ZonedDateTime submitDate);

    List<Answer> getAllByProblem(long problemId);

    List<Answer> getAllByProblemSubject(String subject);

    List<Answer> getAllByStudentId(String studentId);

    List<Answer> getAllByTime(ZonedDateTime from, ZonedDateTime to);

    Answer get(long id);

    Answer getByTitle(String title);

    Answer update(Problem problem);

    Answer updateToMark(long id, long markId, int point, String markerComments, ZonedDateTime markDate);

    Answer delete(long id);
}
