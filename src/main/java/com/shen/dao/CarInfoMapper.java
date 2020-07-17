package com.shen.dao;

import com.shen.pojo.CarInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CarInfo record);

    CarInfo selectByPrimaryKey(String id);

    List<CarInfo> selectAll();

    int updateByPrimaryKey(CarInfo record);

    List<CarInfo> selectByType(@Param("type") String type);
}