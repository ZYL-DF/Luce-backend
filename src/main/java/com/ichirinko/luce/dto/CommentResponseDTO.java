package com.ichirinko.luce.dto;


import java.sql.Timestamp;

public class CommentResponseDTO {
    private int id;
    private String comment;
    private int userId;
    private String userName;
    private Timestamp date;

    public CommentResponseDTO(int id, String comment, int userId, String userName, Timestamp date) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.userName = userName;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
