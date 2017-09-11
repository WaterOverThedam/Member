package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.dao.CouponDao;
import com.thelittlegym.mobile.dao.UserDao;
import com.thelittlegym.mobile.entity.Coupon;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by hibernate on 2017/3/21.
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CouponDao couponDao;

    @Override
    public List<User> getUserList() {
        return userDao.findAll();
    }

    @Override
    public Page<User> getUserPageList(Pageable pageable) {
        Date dtBegin = new Date();
        Date dtEnd = new Date();
        try {
            dtBegin= new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
            dtEnd = new SimpleDateFormat("yyyy-MM-dd").parse("2017-10-17");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return userDao.getUserPageListByDate(dtBegin,dtEnd,pageable);
    }

    @Override
    public Page<User> getUserPageListForSearch(String username, Pageable pageable) {
        return userDao.findAllByUsernameLike(username,pageable);
    }

    @Override
    public ResultEnum deleteOneUser(Integer id) {
        User user = new User();
        try {
            user = userDao.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user != null){
            user.setIsDelete(true);
            userDao.save(user);

            return ResultEnum.DEL_SUCCESS;
        }else{

            return ResultEnum.DEL_FAIL;
        }
    }

    @Override
    public User getUserById(Integer id)  {
        User user = userDao.getOne(id);
        return user;
    }

    @Override
    public void updateUser(User user)  {
        userDao.save(user);
    }

    @Override
    public Long getTotal()  {
        return userDao.findCount();
    }

    @Override
    public Boolean isReged(String tel)  {
        User u = userDao.findOneByTel(tel);
        if (null == u){
            return false;
        }
        return true;
    }

    @Override
    public Boolean isNum(String tel,Integer num) {
        //临时添加  活动开始时间为8-17 8点
        try {
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String beginDateStr = "2017-08-17 08:00:00";
            String endDateStr = "2017-09-10 23:59:59";
            Date beginDate = sdf.parse(beginDateStr);
            Date endDate = sdf.parse(endDateStr);
            if ( now.getTime() < beginDate.getTime() || now.getTime() > endDate.getTime()){
                return false;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        //1.是否已领取
        Coupon coupon = couponDao.findOneByTelAndType(tel,"2");
        if (null != coupon){
            return false;
        }
        //2.是否前num名会员
        BigInteger count = userDao.findCountBySql(num,tel);
        BigInteger c0 = new BigInteger("0");
        if (count.compareTo(c0) == 1){
            return true;
        }
        return false;
    }

    @Override
    public User getByTel(String tel)  {
        User u = userDao.findOneByTel(tel);
        return u;
    }


}
