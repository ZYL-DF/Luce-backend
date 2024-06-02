package com.ichirinko.luce.mapper;

import com.ichirinko.luce.model.User;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByEmailAddress(String emailAddress);

    void createUser(User user);

    void updateUser(User user);
}
