package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.entity.OasisMSG;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {
    @Autowired
    PushMessageServiceImpl pushMessageService;

    @Test
    public void noticeTest() {
        OasisMSG oasisMSG = new OasisMSG();
        pushMessageService.notice(oasisMSG);
    }
}