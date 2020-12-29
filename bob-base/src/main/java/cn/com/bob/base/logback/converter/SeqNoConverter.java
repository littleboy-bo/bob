package cn.com.bob.base.logback.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.com.bob.base.BobContext;
import cn.com.bob.base.constants.BobConstants;
import cn.com.bob.base.BobContext;
import cn.com.bob.base.constants.BobConstants;

/**
 * logback 自定义转换器
 * @author songbo
 * @date 2020-01-14
 */
public class SeqNoConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        return BobContext.getInstance().getSysHeadData(BobConstants.SEQ_NO);
    }
}
