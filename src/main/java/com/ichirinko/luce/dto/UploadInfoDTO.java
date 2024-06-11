package com.ichirinko.luce.dto;

public class UploadInfoDTO {
    private int uploadUserId;
    private String title;
    private String detail;

    public UploadInfoDTO(int uploadUserId, String title, String detail) {
        this.uploadUserId = uploadUserId;
        this.title = title;
        this.detail = detail;
    }

    public int getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(int uploadUserId) {
        this.uploadUserId = uploadUserId;
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
}
