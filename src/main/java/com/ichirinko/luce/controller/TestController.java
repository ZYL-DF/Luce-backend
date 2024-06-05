package com.ichirinko.luce.controller;


import com.ichirinko.luce.dto.ResponseDTO;
import com.ichirinko.luce.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/init")
    public ResponseEntity<ResponseDTO<String>> init() {
        try {
            testService.createTable();
            return new ResponseEntity<>(new ResponseDTO<>("数据库初始化成功", ""), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseDTO<>("数据库初始化失败"), HttpStatus.BAD_REQUEST);
        }
    }
}