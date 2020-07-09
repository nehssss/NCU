package com.shen.controller;

//import cn.dsna.util.images.ValidateCode;
//import com.shen.dao.NowdataMapper;
//import com.work.virus.pojo.Nowdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 *  负责页面的跳转，登录注册等行为，在主页访问页面等
 */
@Controller
@RequestMapping("/home")
public class HomeController {
//    @Autowired
//    NowdataMapper nowdataMapper;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        // 清空session
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        return "/login";
    }
    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    @RequestMapping("/newsTable")
    public  String newsTable(){
        return  "/news_table";
    }


}
