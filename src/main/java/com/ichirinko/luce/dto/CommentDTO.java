package com.ichirinko.luce.dto;


import java.io.Serializable;

public class CommentDTO implements Serializable {
    private int videoId;
    private int userId;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public CommentDTO(int videoId, int userId, String comment) {
        this.videoId = videoId;
        this.userId = userId;
        this.comment = comment;
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

    private String comment;
}
