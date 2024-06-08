package com.ichirinko.luce.dto;

import java.io.Serializable;

/**
 * @ClassName UserLoginResponseDTO
 * @Description
 * @Author Ichirinko
 * @Date 2024/6/1 21:56
 * @Version 1.0
 **/
public class UserLoginResponseDTO implements Serializable {
    private final String token;
    private int id;
    private String emailAddress;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserLoginResponseDTO(String token, int id, String emailAddress, String username) {
        this.token = token;
        this.id = id;
        this.emailAddress = emailAddress;
        this.username = username;
    }

    public String getToken() {
        return token;
    }
}
