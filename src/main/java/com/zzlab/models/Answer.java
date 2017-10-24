package com.zzlab.models;

import java.time.ZonedDateTime;

/**
 * 解答
 */
public class Answer {
    private long id;
    private long problemId;
    private long studentId;
    private String answerDirPath;

    private long markerId;
    private int point;
    private String markerComments;
    private ZonedDateTime markDate;


    public Answer(long id, long problemId, long studentId, String answerDirPath) {
        this.id = id;
        this.problemId = problemId;
        this.studentId = studentId;
        this.answerDirPath = answerDirPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProblemId() {
        return problemId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getAnswerDirPath() {
        return answerDirPath;
    }

    public void setAnswerDirPath(String answerDirPath) {
        this.answerDirPath = answerDirPath;
    }

    public long getMarkerId() {
        return markerId;
    }

    public void setMarkerId(long markerId) {
        this.markerId = markerId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getMarkerComments() {
        return markerComments;
    }

    public void setMarkerComments(String markerComments) {
        this.markerComments = markerComments;
    }

    public ZonedDateTime getMarkDate() {
        return markDate;
    }

    public void setMarkDate(ZonedDateTime markDate) {
        this.markDate = markDate;
    }
}
