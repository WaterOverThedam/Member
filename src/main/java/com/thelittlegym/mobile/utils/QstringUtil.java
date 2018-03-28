package com.thelittlegym.mobile.utils;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.net.URLDecoder;

/**
 * Created by TONY on 2018/3/27.
 */
public class QstringUtil {

        Map paramMap;
        HttpServletRequest request;

        public void QueryStringParser(HttpServletRequest request) {
            String queryString = request.getQueryString();
            if (queryString == null) {
                paramMap = null;
                this.request = request;
                return;
            } else
                paramMap = new HashMap();
            StringTokenizer st = new StringTokenizer(queryString, "&");
            while (st.hasMoreTokens()) {
                String pairs = st.nextToken();
                String key = pairs.substring(0, pairs.indexOf('='));
                String value = pairs.substring(pairs.indexOf('=') + 1);
                paramMap.put(key, value);
            }
        }

        public String get(String key) {
            if(paramMap==null)
                return request.getParameter(key);
            else
                return URLDecoder.decode((String) paramMap.get(key));
        }

}
