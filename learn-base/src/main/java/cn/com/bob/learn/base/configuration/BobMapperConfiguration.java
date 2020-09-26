package cn.com.bob.learn.base.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"cn.com.bob.learn.*"})
public class BobMapperConfiguration {
}
