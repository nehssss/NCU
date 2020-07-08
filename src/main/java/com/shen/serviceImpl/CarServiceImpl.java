package com.shen.serviceImpl;

import com.shen.dao.CarInfoMapper;
import com.shen.pojo.CarInfo;
import com.shen.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    CarInfoMapper carInfoMapper;
    @Override
    public void del(String[] ids) {
        for (int i=0;i<ids.length;i++) {
            carInfoMapper.deleteByPrimaryKey(ids[i]);
        }

    }
}
