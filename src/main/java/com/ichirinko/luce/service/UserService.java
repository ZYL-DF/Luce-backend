package com.ichirinko.luce.service;

import com.ichirinko.luce.model.User;

import java.util.List;

public interface UserService {

    User userLogin(String emailAddress, String password);

    boolean userRegister(User user);

    List<User> getAllUser();

    User updateUserInfo(User user);

    User getUserById(int id);
}
