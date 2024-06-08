package com.ichirinko.luce.dto;


public class VideoDTO {
    private String url;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VideoDTO(String url) {
        this.url = url;
    }
}
