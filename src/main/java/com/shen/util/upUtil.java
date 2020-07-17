package com.shen.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;

public class upUtil {
    // 静态的方法
    public  static  void  upfile(MultipartFile file, HttpServletRequest request){

        String realPath = request.getServletContext().getRealPath("/WEB-INF/imgs/");
        //D:\wj\ideaProject\DouYinProject\target\DouYinProject-1.0-SNAPSHOT\WEB-INF\video\
        System.out.println("当前服务器路径是"+realPath);

        // 拷贝到当前  项目的文件夹
        String  basePath = "D:/code/java/cartest/src/main/webapp/WEB-INF/imgs/";
        File file1 = new File(realPath);
        File   file2  = new File(basePath);
        // 判断文件夹是否存在
        if(!file1.exists()){
            file1.mkdirs();

        }
        if(!file2.exists()){
            file2.mkdirs();
        }
        // 获取文件名
        String orgName = file.getOriginalFilename();
        System.out.println("文件的原始路径是"+orgName);
        try{
            // 拷贝文件
            // 参数1   文件的额路径  参数2  往文件末尾追加新内容
            // 文件字节输出流
            FileOutputStream fos1 = new FileOutputStream(realPath+orgName,false);
            FileOutputStream  fos2 = new FileOutputStream(basePath+orgName,false);
            // file 类型是MultipartFile  可以转化成字节数组
            fos1.write(file.getBytes());
            fos2.write(file.getBytes());
            // 刷新缓存  把缓存内容 刷到文件当中
            fos1.flush();
            fos2.flush();

            fos1.close();
            fos2.close();

        }
        catch(Exception  e){
            e.printStackTrace();
        }


    }
}
