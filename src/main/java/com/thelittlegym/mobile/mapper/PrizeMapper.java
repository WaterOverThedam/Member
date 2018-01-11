package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Prize;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PrizeMapper {
    public Integer addPrizeBatch(List<Prize> prizeList);

    @Select("select id,name,used from prize where tel=#{tel}")
    public List<Prize> getPrizes(@Param("tel") String tel);

    @Update("update prize set used=1 where id=${id}")
    public Integer awardPrize(@Param("id") Integer id);


}

