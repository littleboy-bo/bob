package cn.com.bob.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"cn.com.bob.base.exception"})
@ComponentScan(basePackages = {"cn.com.bob"})
@EnableConfigurationProperties()
//@EnableFeignClients
public class BobSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(BobSpringApplication.class,args);
    }

}