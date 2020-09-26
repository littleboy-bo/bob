package cn.com.bob.learn.service.mapper;

import cn.com.bob.learn.service.model.BobLearn;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *
 */
//@Repository
public interface BobLearnMapper {

    /**
     * @方法名称: param
     * @方法描述: 获取用户信息
     * @参数与返回说明:
     * @算法描述: 无
     */
    BobLearn getUserByUserId(Map<String, String> param) ;

}
