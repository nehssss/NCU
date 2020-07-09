package com.shen.dao;

import com.shen.pojo.Operate;
import java.util.List;

public interface OperateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Operate record);

    Operate selectByPrimaryKey(String id);

    List<Operate> selectAll();

    int updateByPrimaryKey(Operate record);
}