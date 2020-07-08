package com.shen.controller;
import com.shen.dao.CarInfoMapper;
import com.shen.pojo.CarInfo;
import com.shen.pojo.Result;
import com.shen.pojo.Users;
import com.shen.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/car")
public class CarController {
        @Autowired
        CarInfoMapper carInfoMapper;

    @Autowired
    CarService carService;


    @RequestMapping("/show")
        @ResponseBody
        public Result carAll() {
            Result result = new Result();
            try {
                List<CarInfo> list = carInfoMapper.selectAll();
                System.out.println(list);
                if (list.size() > 0) {
                    result.setItem(list);
                    result.setMessage("success");
                    result.setTotal(list.size());
                } else {
                    result.setMessage("error");
                }
            } catch (Exception e) {
                result.setMessage("error");
            }
            return result;

        }
        @RequestMapping("/add")
        @ResponseBody
        public  Result add(CarInfo carInfo  ,HttpServletRequest request){
            Result  result = new Result();
            HttpSession session = request.getSession();

            Users user=(Users)session.getAttribute("user");
            System.out.println(user.getPassword());
            System.out.println(carInfo);
            // 自己加一个 随机的id  用UUID
            //  video 添加一个日期  （当天日期）
            //    userId  用 "admin"代替
            // getVideoPath()  需要拼上  "video/" 再传给数据库
            // 继续完成添加功能
            // 调用insert  方法
            try{
                String  id = UUID.randomUUID().toString();

                carInfo.setId(id);
                carInfo.setUserId(user.getNickname());
                carInfoMapper.insert(carInfo);
                result.setMessage("success");
                result.setStatus("200");
                return result;
            }
            catch(Exception e){
                e.printStackTrace();
                result.setMessage("error");
                return result;
            }

        }

    @RequestMapping("/delete")
    @ResponseBody
    public  Result delete(String[] ids,HttpServletRequest request){
        Result  result = new Result();
        System.out.println("传过来的数组值是"+ Arrays.toString(ids));
        try{
            carService.del(ids);
            // 删除成功
            result.setMessage("success");
            result.setStatus("200");
        }
        catch(Exception e){
            result.setMessage("数据操作异常");
            result.setStatus("500");
            // z打印一下异常
            e.printStackTrace();

        }
        return result;
    }

}
