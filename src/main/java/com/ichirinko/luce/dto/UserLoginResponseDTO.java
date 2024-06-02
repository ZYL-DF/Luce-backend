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
    public UserLoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
