package cn.com.bob.learn.base.util;

import cn.com.bob.learn.base.BobContext;
import cn.com.bob.learn.base.constants.BobConstants;
import org.slf4j.MDC;

public class MdcUtils {
    public static void init(){
        MDC.put("platformId", BobContext.getInstance().getPlatformId());
        MDC.put("tranMode", BobConstants.TRAN_MODE_ONLINE);
        MDC.put("seqNo", BobContext.getInstance().getSysHeadData(BobConstants.SEQ_NO));
    }
    public static void clear(){
        MDC.clear();
    }
}
