package cn.com.bob.learn.service.service;

import cn.com.bob.learn.service.mapper.BobLearnMapper;
import cn.com.bob.learn.service.model.BobLearn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class BobLearnService {

    @Resource
    BobLearnMapper bobLearnMapper;
    /**
     * 根据ifp系统用户号查询平台用户信息
     * @param userId
     * @return
     */
    public BobLearn getUserByUserId(String userId){
        Map<String , String> param = new HashMap<>();
        param.put("userId",userId);
        return bobLearnMapper.getUserByUserId(param);
    }

}
