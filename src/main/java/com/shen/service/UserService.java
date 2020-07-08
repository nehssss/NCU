package com.shen.service;

import com.shen.pojo.Result;

import javax.servlet.http.HttpServletRequest;


public interface UserService {
    //图形验证码
    Result login(String username, String password, String code, HttpServletRequest request);

}
