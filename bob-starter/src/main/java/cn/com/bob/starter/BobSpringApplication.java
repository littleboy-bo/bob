package cn.com.bob.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = {"cn.com.bob.base.exception"})
@ComponentScan(basePackages = {"cn.com.bob"})
@EnableConfigurationProperties()
//@EnableFeignClients
public class BobSpringApplication {

    private static final Logger log = LoggerFactory.getLogger(BobSpringApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication application = new SpringApplication(BobSpringApplication.class);
        ConfigurableApplicationContext context = application.run(args);
        Environment env = context.getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null){
            protocol = "https";
        }
        log.info("\n-------------------------------------------------------------\n\t" +
                          "Application '{}' is running! Access URLs:\n\t" +
                          "Local: \t\t{}://localhost:{}\n\t" +
                          "External: \t{}://{}:{}\n\t" +
                          "Profile(s):\t{}\n-------------------------------------------------------------",
                //集成spring-cloud-context后生效
                //env.getProperty("spring.application.name"),
                env.getProperty("bob.application.systemId"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getActiveProfiles());

        String configServerStatus = env.getProperty("configserver.status");
        log.info("\n-------------------------------------------------------------\n\t" +
                       "Config Server: \t{}\n-------------------------------------------------------------",
                configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);

        //SpringApplication.run(BobSpringApplication.class,args);

    }

}