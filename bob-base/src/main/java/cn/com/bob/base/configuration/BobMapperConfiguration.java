package cn.com.bob.base.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"cn.com.bob.*"})
public class BobMapperConfiguration {
}
