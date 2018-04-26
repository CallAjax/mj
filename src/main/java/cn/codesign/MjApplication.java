package cn.codesign;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@ComponentScan(basePackages = "cn.**.config,cn.**.controller,cn.**.impl,cn.**.task")
@MapperScan("cn.**.mapper")
@EnableScheduling
@EnableCaching
@EnableAutoConfiguration
public class MjApplication {

    public static void main(String[] args) {
        SpringApplication.run(MjApplication.class, args);
    }
}

