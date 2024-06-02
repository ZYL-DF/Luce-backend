package com.ichirinko.luce.dto;

import com.ichirinko.luce.model.User;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private int id;
    private String emailAddress;
    private String username;
    private String password;

    public UserDTO(User user) {
        this.id = user.getId();
        this.emailAddress = user.getEmailAddress();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public UserDTO(int id, String emailAddress, String username, String password) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

