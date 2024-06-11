package com.ichirinko.luce.model;


import java.sql.Time;
import java.util.Date;

public class Video {
    private int id;
    private String url;
    private String coverUrl;
    private String title;
    private String detail;
    private int playTimes;
    private Date uploadDate;
    private int uploadUserId;
    private Time length;

    public Video(int id, String url, String coverUrl, String title, String detail, int playTimes, Date uploadDate, int uploadUserId, Time length) {
        this.id = id;
        this.url = url;
        this.coverUrl = coverUrl;
        this.title = title;
        this.detail = detail;
        this.playTimes = playTimes;
        this.uploadDate = uploadDate;
        this.uploadUserId = uploadUserId;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(int uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public Time getLength() {
        return length;
    }

    public void setLength(Time length) {
        this.length = length;
    }
}
