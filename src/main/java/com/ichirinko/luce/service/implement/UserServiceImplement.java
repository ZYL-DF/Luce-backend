package com.ichirinko.luce.service.implement;

import com.ichirinko.luce.mapper.UserMapper;
import com.ichirinko.luce.model.User;
import com.ichirinko.luce.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplement implements UserService {
    private final UserMapper userMapper;

    public UserServiceImplement(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User userLogin(String emailAddress, String password) {
        User result = userMapper.getUserByEmailAddress(emailAddress);

        if(result == null) {
            return null;
        }

        if(result.getPassword().equals(password)) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public boolean userRegister(User user) {
        User result = userMapper.getUserByEmailAddress(user.getEmailAddress());

        if(result != null) {
            return false;
        }

        userMapper.createUser(user);
        return true;
    }

    @Override
    public List<User> getAllUser() {

        return userMapper.getAllUsers();
    }

    @Override
    public User updateUserInfo(User user) {

        var userGet = userMapper.getUserById(user.getId());

        if(userGet!=null) {
            userMapper.updateUser(user);

            return userMapper.getUserById(user.getId());
        }
        else {
            return null;
        }

    }
}
