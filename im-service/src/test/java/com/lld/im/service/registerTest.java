package com.lld.im.service;

import com.lld.im.service.user.dao.mapper.ImUserDataMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class registerTest {
    @Autowired
    ImUserDataMapper imUserDataMapper;

}
