package cn.com.bob.learn.service.web;

import cn.com.bob.learn.base.BobContext;
import cn.com.bob.learn.base.aop.annotation.BobService;
import cn.com.bob.learn.base.util.BusiUtils;
import cn.com.bob.learn.base.util.BusinessUtils;
import cn.com.bob.learn.base.util.ObjectUtils;
import cn.com.bob.learn.service.model.BobLearn;
import cn.com.bob.learn.service.service.BobLearnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
public class ControllerTest {


    private static final Logger log = LoggerFactory.getLogger(ControllerTest.class);

    @Resource
    BobLearnService bobLearnService;

    @RequestMapping("/testRequest")
    //@BobService()
    public Map testRequest(){
        log.info("ControllerTest.TestRequest start ...");
        Map retMap = new HashMap();
        String str = "successful!";
        retMap.put("retMsg",str);
        log.info("ControllerTest.TestRequest end ...");

        return retMap;
    }

    @RequestMapping("/testPost")
    @BobService()
    public Map testPost(@RequestBody Map requestParam){
        log.info("ControllerTest.TestPost start ...");
        log.info("请求参数= " + requestParam);
        log.info("BobContext sysHead = " + BobContext.getInstance().getSysHead());
        Map retMap = new HashMap();
        if (BusiUtils.isNotNull(requestParam) && requestParam.size() >0){
            if (requestParam.containsKey("id")) {
                retMap.put("retMsg",requestParam.get("id").toString());
            }
        }else {
            throw BusinessUtils.createBusinessException("B00001");
        }
        log.info("ControllerTest.TestPost end ...");
        return retMap;
    }

    @RequestMapping("/getBobLearnInfo")
    public Map getBobLearnInfo(@RequestBody Map param){
        String userId = param.get("userId").toString();
        BobLearn bobLearn = bobLearnService.getUserByUserId(userId);
        if(BusiUtils.isNull(bobLearn)){
            throw BusinessUtils.createBusinessException("B00010");
        }
        return ObjectUtils.beanToMapNew(bobLearn);
    }
}
