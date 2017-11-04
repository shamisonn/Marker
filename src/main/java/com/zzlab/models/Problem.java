package com.zzlab.models;

import java.time.ZonedDateTime;

/**
 * 問題
 */
public class Problem {
    private long id;
    private String title; // 問題名
    private String description; // 問題説明
    private String subject; // 科目名
    private ZonedDateTime date;

    public Problem(long id, String title, String description, String subject, ZonedDateTime date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
