package com.shen.controller;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.shen.dao.CarInfoMapper;
import com.shen.dao.OperateMapper;
import com.shen.pojo.CarInfo;
import com.shen.pojo.Operate;
import com.shen.pojo.Result;
import com.shen.pojo.Users;
import com.shen.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

        @Autowired
    OperateMapper operateMapper;


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
            Operate op=new Operate();

            try{
                String  id = UUID.randomUUID().toString();
                op.setId(id+new Date());
                op.setOpName(user.getUsername());
                op.setOpTime(new Date());
                op.setOpType("add");
                operateMapper.insert(op);
                carInfo.setId(id);
                carInfo.setUserId(user.getNickname());
                carInfo.setAdddate(new Date());
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
        HttpSession session = request.getSession();

        Users user=(Users)session.getAttribute("user");
        System.out.println("传过来的数组值是"+ Arrays.toString(ids));
        Operate op=new Operate();
        try{

            String  id = UUID.randomUUID().toString();
            op.setId(id+new Date());
            op.setOpName(user.getUsername());
            op.setOpTime(new Date());
            op.setOpType("del");
            operateMapper.insert(op);
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
