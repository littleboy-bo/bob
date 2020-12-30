package cn.com.bob.base.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author songbo
 * @version V1.0
 * 获取Spring容器上下文 以静态变量保存Spring ApplicationContext
 * @date 20201230
 *
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.context = applicationContext;
    }


    /**
     * @return context:return the property context
     */
    public static ApplicationContext getContext(){
        return context;
    }

}
