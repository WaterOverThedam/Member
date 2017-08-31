package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.dao.ThemeDao;
import com.thelittlegym.mobile.entity.Theme;
import com.thelittlegym.mobile.service.IThemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by TONY on 2017/8/26.
 */
@Service
@Slf4j
public class ThemeServiceImpl implements IThemeService {
    private ThemeDao themeDao;
    @Override
    public void save(Theme theme) {
        try {
            themeDao.save(theme);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
