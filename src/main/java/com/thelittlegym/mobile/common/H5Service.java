package com.thelittlegym.mobile.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hibernate on 2017/6/21.
 */
@Service
public class H5Service {
    //http://h5.qq125.com/2018/01/coupon/loadcoupon.php?tel=15618373270
    //private static final String URL = "http://wx.qq125.com/2017/06/coupon/loadcoupon.php?tel=";
    private static final String URL = "http://h5.qq125.com/2018/01/coupon/loadcoupon.php?tel=";
    //private static final String URL ="http://tlgc.oss-cn-shanghai.aliyuncs.com/assert/json/coupon.json?tel=";
    //18825257804
    //13341608522
    @Autowired
    private HttpService httpService;

    public String getResult(String url) throws Exception{
        return httpService.doGet(url);
    }

    public JSONArray getByType(String tel,String type) throws Exception{
        String result = getResult(StringUtils.trimWhitespace(URL + tel));
        if(result!=null) {
            JSONArray returnArr = new JSONArray();
            JSONObject jsonObject = JSONObject.parseObject(result);
            if ("1".equals(jsonObject.getString("resCode"))) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (Object p : jsonArray) {
                    JSONObject j = (JSONObject) p;
                    if (type.equals(j.getString("type")) && type.equals("coupon")) {
                        returnArr.add(j);
                        return returnArr;
                    }
                    if (!type.equals("coupon")) {
                        returnArr.add(j);
                    }
                }
                return returnArr;
            }
        }
        return null;
    }

    public Map<String, Object> getAll(String tel) throws Exception{
        String result = getResult(StringUtils.trimWhitespace(URL + tel));
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(result!=null) {
            JSONArray returnArr = new JSONArray();
            JSONObject jsonObject = JSONObject.parseObject(result);
            if ("1".equals(jsonObject.getString("resCode"))) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (Object p : jsonArray) {
                    JSONObject j = (JSONObject) p;
                    String type = j.getString("type");
                    if (type.equals("zx")) {
                        returnMap.put("zx",j.getString("val"));
                    }
                    if (type.equals("keshi")) {
                        returnMap.put("keshi",j.getInteger("total"));
                    }
                    if (type.equals("coupon")) {
                        returnMap.put("coupon",j.getInteger("total"));
                    }
                    if (type.equals("prize")) {
                        returnMap.put("prize",j.getString("total"));
                    }
                    if (type.equals("points")) {
                        returnMap.put("points",j.getString("total"));
                    }
                }
                return returnMap;
            }
        }
        return null;
    }
}
