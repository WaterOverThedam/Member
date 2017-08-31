package com.thelittlegym.mobile.service.impl;


import com.thelittlegym.mobile.dao.ParticipatorDao;
import com.thelittlegym.mobile.entity.Participator;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.service.IParticipatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hibernate on 2017/5/26.
 */
@Service
public class ParticipatorServiceImpl implements IParticipatorService {
    @Autowired
    private ParticipatorDao participatorDao;

    @Override
    public Map<String, Object> addPar(String tel, String name, String actId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Participator p = new Participator();
        if (null != participatorDao.findOneByPhoneAndActid(tel,actId)) {
            returnMap.put("result", ResultEnum.ENROL_EXIST);
        } else {
            p.setPhone(tel);
            p.setName(name);
            p.setActid(actId);
            p.setCreateTime(new Date());
            participatorDao.save(p);
            returnMap.put("value", p);
            returnMap.put("result", ResultEnum.ENROL_SUCCESS);
        }
        return returnMap;
    }

    @Override
    public void updatePar(Participator p)  {
        participatorDao.save(p);
    }

    @Override
    public Boolean valPar(Map<String, Object> sendMap, String reqNum)  {
        Boolean flag = false;
        try {
            if (null != sendMap && sendMap.size() > 0) {
                Object objSuccess = sendMap.get("success");
                Date timeStamp = (Date) sendMap.get("timestamp");
                Boolean isSend = (Boolean) objSuccess;
                String message = sendMap.get("message").toString();
                Long diffMins = getDateDiffMins(timeStamp,new Date());
                if (isSend && diffMins <= 30 && message.equals(reqNum)) {
                    flag = true;
                }
            }
        }catch (Exception e){
            flag = false;
        }

        return flag;
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
