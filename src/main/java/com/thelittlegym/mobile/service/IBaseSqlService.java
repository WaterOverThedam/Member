package com.thelittlegym.mobile.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by TONY on 2017/8/26.
 */

public interface IBaseSqlService {
    public List queryBySql(String sql);
    public int excuteBySql(String sql);
}
