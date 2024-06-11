package com.ichirinko.luce.model;


import java.sql.Timestamp;
import java.util.Date;

public class Comment {
    private int id;
    private String comment;
    private int userId;
    private Timestamp date;
    public Comment() {

    }
    public Comment(int id, String comment, int userId, Timestamp date) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.date = date;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
