package cn.com.bob.base.handle;

import cn.com.bob.base.BobResult;
import cn.com.bob.base.BobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songbo
 * @date 2020/1/14
 * TODO 待后期实现
 */

//@Repository
public class BobHandler {

    private static final Logger logger = LoggerFactory.getLogger(BobHandler.class);

    @Resource
    //private GravityProcess gravityProcess;

    /**
     * 应用框架调度统一入口
     *
     * @param in
     * @return
     */
    public Map handle(String tranCode, Map in) {
        long start = -1;
        start = System.currentTimeMillis();
        Map out;
        try {
            //BobResult result = beforeExecute(tranCode, in);
            BobResult result = new BobResult();
            out = (Map) execute(tranCode, in, result);
            //out = afterExecute(in, result.getReturnMap());
        }catch (Throwable t){
            out = BobResult.newInstance(t).getReturnMap();
        }
        if (logger.isInfoEnabled()) {
            long interval = System.currentTimeMillis() - start;
            logger.info("Output message:[{}]", out);
            logger.info("Execution time:[{}]", interval);
        }
        return out;
    }
    private BobResult execute(String tranCode, Map in, BobResult result){
        if (result == null) {
            //result = gravityProcess.process(tranCode, in);
        }
        return result;
    }

}
