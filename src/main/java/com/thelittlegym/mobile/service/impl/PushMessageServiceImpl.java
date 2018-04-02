package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.config.WechatAccountConfig;
import com.thelittlegym.mobile.entity.OasisMSG;
import com.thelittlegym.mobile.entity.Order;
import com.thelittlegym.mobile.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tony
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void notice(OasisMSG oasisMSG,String tpl) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(tpl);
        templateMessage.setToUser(oasisMSG.getOpenId());

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first",oasisMSG.getFirst()),
                new WxMpTemplateData("keyword1",oasisMSG.getHz()),
                new WxMpTemplateData("keyword2",oasisMSG.getDate()),
                new WxMpTemplateData("keyword3",oasisMSG.getHao()),
                new WxMpTemplateData("keyword4",oasisMSG.getSheng()),
                new WxMpTemplateData("remark",oasisMSG.getRemark())
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }
    }
    @Override
    public void orderStatus(Order order,String tpl){
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(tpl);
        templateMessage.setToUser("o2KB1jt6YK_GVlFTl7DfE53wRphg");

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货。"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "18868812345"),
                new WxMpTemplateData("keyword3", order.getOrderId()),
                new WxMpTemplateData("keyword4", order.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5", "￥" + order.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临！"));
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }
    }

}
