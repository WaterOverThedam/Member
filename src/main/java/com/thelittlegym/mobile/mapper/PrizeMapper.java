package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Prize;

import java.util.List;

public interface PrizeMapper {
    public Integer addPrizeBatch(List<Prize> prizeList);

}

