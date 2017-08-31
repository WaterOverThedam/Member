package com.thelittlegym.mobile.utils.msg.send;

import com.thelittlegym.mobile.utils.msg.config.AppConfig;
import com.thelittlegym.mobile.utils.msg.lib.MESSAGEXsend;
import com.thelittlegym.mobile.utils.msg.utils.ConfigLoader;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 汁 on 2017/5/25.
 * 发送验证码
 */

public class ValNum {
    private static final AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);

    public Map<String,Object> sendVal(String tel){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String valNum = getRandomStr(1000, 9999);
        MESSAGEXsend submail = new MESSAGEXsend(config);
        submail.addTo(tel);
        submail.setProject("IkkGR1");
        submail.addVar("time", "30分钟");
        submail.addVar("code", valNum);
        submail.xsend();
        try {
            returnMap.put("timestamp",new Date());
            returnMap.put("message", valNum);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：发送失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
    }

    /*
   生成随机数验证码
    */
    public String getRandomStr(int min, int max) {
        int randNum = min + (int) (Math.random() * ((max - min) + 1));
        return randNum + "";
    }

    /*
    时间比较，分钟
     */
    public long getDateDiffMins(Date initDate, Date nowDate) {
        long nm = 1000 * 60;
        long diff = nowDate.getTime() - initDate.getTime();
        long mins = diff / nm;
        return mins;
    }
}
