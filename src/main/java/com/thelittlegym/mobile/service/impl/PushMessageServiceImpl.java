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
    public void notice(OasisMSG oasisMSG) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId("Is-sAuw0gZGlDXxFrGlVRZsLXY3nq8wtxe0rXZQ3pjY");
        templateMessage.setToUser("o2KB1jt6YK_GVlFTl7DfE53wRphg");

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "您已经完成本次课程"),
                new WxMpTemplateData("keyword1", "张三"),
                new WxMpTemplateData("keyword2", "2016年07月18日17:20 ~ 18:20"),
                new WxMpTemplateData("keyword3", "2课时"),
                new WxMpTemplateData("keyword4", "72课时"),
                new WxMpTemplateData("remark", "如有疑问请及时联系教务老师，客服热线：xxxxxxxx。")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }
    }
    @Override
    public void orderStatus(Order order){
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId("Is-sAuw0gZGlDXxFrGlVRZsLXY3nq8wtxe0rXZQ3pjY");
        templateMessage.setToUser("o2KB1jt6YK_GVlFTl7DfE53wRphg");

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "您已经完成本次课程"),
                new WxMpTemplateData("keyword1", "张三"),
                new WxMpTemplateData("keyword2", "2016年07月18日17:20 ~ 18:20"),
                new WxMpTemplateData("keyword3", "2课时"),
                new WxMpTemplateData("keyword4", "72课时"),
                new WxMpTemplateData("remark", "如有疑问请及时联系教务老师，客服热线：xxxxxxxx。")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }
    }

}
