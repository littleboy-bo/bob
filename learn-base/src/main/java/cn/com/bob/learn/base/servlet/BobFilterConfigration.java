package cn.com.bob.learn.base.servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Filter;


/**
 * @author songbo
 * @date 2020-01-10
 * bob-learn 统一 过滤器 配置
 */
@Configuration
public class BobFilterConfigration {
    @Bean
    public FilterRegistrationBean bobFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(this.BobFilter());
        //filterRegistrationBean.setFilter(new BobFilter(bobHandler));
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.setName("bobFilter");
        return filterRegistrationBean;
    }

    @Bean
    public Filter BobFilter(){
        return new BobFilter();
    }
}
