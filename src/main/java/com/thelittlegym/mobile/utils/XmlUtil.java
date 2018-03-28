package com.thelittlegym.mobile.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TONY on 2018/3/27.
 */
public class XmlUtil {
    public Map<String, Object> map = new HashMap<String, Object>();

    public Map parse(String soap) throws DocumentException {
        Document doc = DocumentHelper.parseText(soap);//报文转成doc对象
        Element root = doc.getRootElement();//获取根元素，准备递归解析这个XML树

        getCode(root);
        return map;
    }

    public void getCode(Element root) {
        if (root.elements() != null) {
            List<Element> list = root.elements();//如果当前跟节点有子节点，找到子节点
            for (Element e : list) {//遍历每个节点
                if (e.elements().size() > 0) {
                    if(e.getName().equals("fields")){
                        getField(e);   //fields节点另外处理
                    }else {
                        getCode(e);//当前节点不为空的话，递归遍历子节点；
                    }
                }
                if (e.elements().size() == 0) {
                    map.put(e.getName(), e.getTextTrim());
                }//如果为叶子节点，那么直接把名字和值放入map
            }
        }
    }

    public void getField(Element fields) {
            List<Element> list = fields.elements();//如果当前跟节点有子节点，找到子节点
            for (Element e : list) {//遍历每个节点
                List<Element> obj = e.elements();
                String key  =  obj.get(1).getTextTrim();
                String value  =  obj.get(2).getTextTrim();
                if(!key.equals("") && !value.equals("")){
                    map.put(key,value);
                }
            }
    }
}
