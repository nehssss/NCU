package com.shen.dao;

import com.shen.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(String id);

    int insert(Users record);

    Users selectByPrimaryKey(String id);

    List<Users> selectAll();

    int updateByPrimaryKey(Users record);

    Users selectUserByname(@Param("username") String username);

}