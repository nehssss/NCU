package com.shen.controller;

import com.shen.pojo.Result;
import com.shen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        // 清空session
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        return "/login";
    }
    //  发送验证码
    @RequestMapping("/userLogin")
    @ResponseBody
    public Result login1(String username, String password, String code, HttpServletRequest request){
        Result result = new Result();
        System.out.println(username+"...."+password+"..."+code);
        //调用登录方法传入用户名 密码 验证码，还有请求
        //调用 service
        result = userService.login(username,password,code,request);
        return result;
    }
}
