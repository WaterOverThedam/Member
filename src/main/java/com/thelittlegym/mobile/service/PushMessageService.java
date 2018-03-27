package com.thelittlegym.mobile.service;


import com.thelittlegym.mobile.entity.OasisMSG;
import com.thelittlegym.mobile.entity.Order;

/**
 * 推送消息
 * Created by 廖师兄
 * 2017-07-30 22:08
 */
public interface PushMessageService {


    void notice(OasisMSG oasisMSG);
    void orderStatus(Order order);
}
