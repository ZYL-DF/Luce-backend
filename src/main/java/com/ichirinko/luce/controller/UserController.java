package com.ichirinko.luce.controller;


import com.ichirinko.luce.dto.ResponseDTO;
import com.ichirinko.luce.dto.UserDTO;
import com.ichirinko.luce.dto.UserLoginDTO;
import com.ichirinko.luce.dto.UserLoginResponseDTO;
import com.ichirinko.luce.model.User;
import com.ichirinko.luce.service.UserService;
import com.ichirinko.luce.utils.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<UserLoginResponseDTO>> login (@RequestBody UserLoginDTO userLoginDTO){

        if(userLoginDTO.getEmailAddress()==null  || userLoginDTO.getPassword()==null) {
            return new ResponseEntity<>(new ResponseDTO<>("登录信息缺失"), HttpStatus.BAD_REQUEST);
        }

        var result = userService.userLogin(userLoginDTO.getEmailAddress(), userLoginDTO.getPassword());

        if(result != null) {
            Map<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(result.getId()));
            payload.put("name", result.getUsername());

            // 生成JWT的令牌
            String token = JWTService.getToken(payload);
            UserLoginResponseDTO responseDTO = new UserLoginResponseDTO(token,result.getId(),result.getEmailAddress(),result.getUsername());


            return new ResponseEntity<>(new ResponseDTO<>("登录成功",responseDTO),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ResponseDTO<>("登录失败"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserDTO>> register (@RequestBody User user){

        if(user.getEmailAddress()==null || user.getUsername()==null || user.getPassword()==null) {
            return new ResponseEntity<>(new ResponseDTO<>("注册信息不能为空"), HttpStatus.BAD_REQUEST);
        }

            var result = userService.userRegister(user);

            if(result) {
                return new ResponseEntity<>(new ResponseDTO<>("注册成功"),HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new ResponseDTO<>("用户邮箱已存在"),HttpStatus.BAD_REQUEST);
            }
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAllUser () {

        var users = userService.getAllUser();
        List<UserDTO> result = new ArrayList<>();

        for(User user : users) {
            UserDTO userDTO = new UserDTO(user);
            result.add(userDTO);
        }

        if(result.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>("用户列表为空",null), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ResponseDTO<>("获取全部用户列表成功",result),HttpStatus.OK);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> getUserById (@PathVariable("id") int id) {

        var user = userService.getUserById(id);


        if(user != null) {
            UserDTO userDTO = new UserDTO(user);
            return new ResponseEntity<>(new ResponseDTO<>("获取用户成功",userDTO), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ResponseDTO<>("用户不存在",null),HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/")
    public ResponseEntity<ResponseDTO<UserDTO>> updateUserInfo (@RequestBody User user){
        var result = userService.updateUserInfo(user);

        if(result!=null) {
            UserDTO userDTO = new UserDTO(result);

            return new ResponseEntity<>(new ResponseDTO<>("更新指定用户成功",userDTO),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ResponseDTO<>("更新指定用户失败,不存在id="+user.getId()+"的用户"),HttpStatus.BAD_REQUEST);
        }

    }
}
