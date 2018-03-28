package com.thelittlegym.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.entity.OasisMSG;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.service.PushMessageService;
import com.thelittlegym.mobile.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@Slf4j
@RequestMapping("/test")
public class testCtrl {
    @Autowired
    private PushMessageService pushMessageService;
    @RequestMapping(value = "/oasis_msg")
    public ModelAndView oasisMsg(@RequestBody String param,Map<String, Object> res,HttpServletResponse rsp) {
        rsp.addHeader("Access-Control-Allow-Origin", "*");
        rsp.setHeader("Content-Type", "text/xml;charset=UTF-8");
        OasisMSG oasisMSG = new OasisMSG();
        //String soap="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><mGetOutMessage xmlns=\"http://tempuri.org/crm_server/Crm_OutMessage\"><outobject><UserID>279833</UserID><ID>4861</ID><SessionID /><ObjectName>crm_zdytable_238592_26277</ObjectName><fields><sobjectField><fieldsign>手机号</fieldsign><fieldname>crmzdy_81762775</fieldname><fieldvalue>test</fieldvalue><fieldtype>文本</fieldtype></sobjectField><sobjectField><fieldsign>短信内容</fieldsign><fieldname>crmzdy_81762774</fieldname><fieldvalue>stes</fieldvalue><fieldtype>文本</fieldtype></sobjectField><sobjectField><fieldsign>中心编号</fieldsign><fieldname>crmzdy_81762776</fieldname><fieldvalue>500012</fieldvalue><fieldtype>文本</fieldtype></sobjectField><sobjectField><fieldsign>记录ID</fieldsign><fieldname>id</fieldname><fieldvalue>4861</fieldvalue><fieldtype>整数</fieldtype></sobjectField></fields></outobject></mGetOutMessage></soap:Body></soap:Envelope>";
        //初始化报文，调用parse方法，获得结果map
        try {
            Map map = new XmlUtil().parse(param);
            oasisMSG.setFirst("你已经完成本次课程");
            oasisMSG.setRemark("如有疑问请及时联系教务老师，客服热线：xxxxxxxx。");
            oasisMSG.setOpenId("o2KB1jt6YK_GVlFTl7DfE53wRphg");
            oasisMSG.setId(map.get("ID").toString());
            oasisMSG.setDate(map.get("crmzdy_82044258").toString());
            oasisMSG.setHz(map.get("crmzdy_82011765").toString());
            oasisMSG.setHao(map.get("crmzdy_82044268").toString());
            oasisMSG.setSheng(map.get("crmzdy_82044270").toString());
            //System.out.println(map);
            //System.out.println(oasisMSG);
            pushMessageService.notice(oasisMSG,"Is-sAuw0gZGlDXxFrGlVRZsLXY3nq8wtxe0rXZQ3pjY");
        } catch (Exception e) {
            res.put("isSuccess","false");
            res.put("error","未知错误");
            e.printStackTrace();
        }
        res.put("isSuccess","true");
        res.put("id", oasisMSG.getId());

        return new ModelAndView("message/success", res);
    }

}