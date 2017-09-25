package com.thelittlegym.mobile.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by hibernate on 2017/6/21.
 */
@Service
public class H5Service {
    private static final String URL = "http://wx.qq125.com/2017/06/coupon/loadcoupon.php?tel=";
    //18825257804
    //13341608522
    @Autowired
    private HttpService httpService;

    public String getResult(String url) throws Exception{
        return httpService.doGet(url);
    }

    public JSONArray getByType(String tel,String type) throws Exception{
        String result = getResult(StringUtils.trimWhitespace(URL + tel));
        JSONArray returnArr = new JSONArray();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if("1".equals(jsonObject.getString("resCode"))){
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (Object p : jsonArray) {
                JSONObject j = (JSONObject) p;
                if ( type.equals(j.getString("type")) && type.equals("coupon") ){
                    returnArr.add(j);
                    return returnArr;
                }
                if ( !type.equals("coupon")){
                    returnArr.add(j);
                }
            }
            return returnArr;
        }
        return null;
    }
}
