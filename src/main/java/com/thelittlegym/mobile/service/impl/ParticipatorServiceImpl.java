package com.thelittlegym.mobile.service.impl;


import com.thelittlegym.mobile.dao.ActivityEnrollmentDao;
import com.thelittlegym.mobile.dao.ParticipatorDao;
import com.thelittlegym.mobile.entity.*;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.exception.MyException;
import com.thelittlegym.mobile.service.IParticipatorService;
import com.thelittlegym.mobile.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hibernate on 2017/5/26.
 */
@Service
@Slf4j
public class ParticipatorServiceImpl implements IParticipatorService {
    @Autowired
    private ParticipatorDao participatorDao;
    @Autowired
    private ActivityEnrollmentDao activityEnrollmentDao;

    @Override
    public Result enroll(Integer actId,User user){
        ActivityEnrollment activityEnrollment = new ActivityEnrollment();
        try {
        Activity activity = new Activity();
        activity.setId(actId);
        activityEnrollment.setUser(user);
        activityEnrollment.setActivity(activity);
        activityEnrollment.setStatus(1);
        activityEnrollment.setCreateTime(new Date());
        activityEnrollmentDao.save(activityEnrollment);
        }catch (Exception e){
            log.info("报名错误{}",e);
            throw new MyException(ResultEnum.ENROL_ERR);
        }
        return ResultUtil.success(ResultEnum.ENROL_SUCCESS,activityEnrollment);
    };
    @Override
    public Result addPar(String tel,String name,Integer actId,User user,String familyTitle) {
        Participator p = new Participator();
        try {
            Activity activity = new Activity();
            activity.setId(actId);
            p.setName(name);
            p.setActivity(activity);
            p.setUser(user);
            p.setFamilyTitle(familyTitle);
            p.setCreateTime(new Date());
            participatorDao.save(p);
        }catch (Exception e){
            log.info("添加名单错误{}",e);
            throw new MyException(ResultEnum.SAVE_FAILURE);
        }
        return ResultUtil.success(ResultEnum.SUCCESS,p);

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
