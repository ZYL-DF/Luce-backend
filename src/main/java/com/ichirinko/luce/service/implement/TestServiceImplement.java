package com.ichirinko.luce.service.implement;

import com.ichirinko.luce.mapper.TestMapper;
import com.ichirinko.luce.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImplement implements TestService {
    private TestMapper testMapper;

    public TestServiceImplement(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Override
    public void createTable() {
        testMapper.createTable();
    }
}
