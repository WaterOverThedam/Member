package com.thelittlegym.mobile.common;

import com.thelittlegym.mobile.utils.weixin.entity.JsApiTicket;
import com.thelittlegym.mobile.utils.weixin.main.Sign;
import com.thelittlegym.mobile.utils.weixin.utils.WeixinUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hibernate on 2017/6/1.
 */
@Service
public class WeixinService {
    //缓存签名
    private static Map<String, String> signatureMap = new HashMap<String, String>();
    private static final long Expiresin = 7000L;
//    public Map<String,String>  getSign() {
//        Sign sign = new Sign();
//        String jsapi_ticket = WeixinUtil.getJsApiTicket().getTicket();
//
//        // 注意 URL 一定要动态获取，不能 hardcode
//        String url = "http://test.thelittlegym.com.cn/index";
//        Map<String, String> ret = sign.sign(jsapi_ticket, url);
//        for (Map.Entry entry : ret.entrySet()) {
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
//        return ret;
//    }

    public static Map<String, String> getSignature(String url) {
        String signature = signatureMap.get("signature");
        if (signature != null && !"".equals(signature)) {
            //获取签名的时间戳
            String signTimeStamp = signatureMap.get("timestamp");
            Long nowTime = System.currentTimeMillis() / 1000;

            if (nowTime - Long.parseLong(signTimeStamp) <= Expiresin) {
                return signatureMap;
            } else {
                String jsapi_ticket = WeixinUtil.getJsApiTicket().getTicket();
                Sign sign = new Sign();
                signatureMap = sign.sign(jsapi_ticket, url);
            }
        } else {
            JsApiTicket jsApiTicket = WeixinUtil.getJsApiTicket();
            if (null != jsApiTicket){
                String jsapi_ticket = jsApiTicket.getTicket();
                Sign sign = new Sign();
                signatureMap = sign.sign(jsapi_ticket, url);
            }else{
                return null;
            }

        }
//        for (Map.Entry entry : signatureMap.entrySet()) {
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
        return signatureMap;
    }

}
