package com.ichirinko.luce.dto;

import com.ichirinko.luce.model.User;

import java.sql.Time;
import java.util.Date;

/**
 * @ClassName VideoDTO
 * @Description
 * @Author Ichirinko
 * @Date 2024/6/6 下午4:14
 * @Version 1.0
 **/
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
