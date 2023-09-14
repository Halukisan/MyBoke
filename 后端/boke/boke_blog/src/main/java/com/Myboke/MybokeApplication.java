package com.Myboke;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("com.Myboke.*")
@MapperScan("com.Myboke.mapper")
@ComponentScan("com.Myboke.controller")
@EnableScheduling
@SpringBootApplication()
@EnableSwagger2
public class MybokeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybokeApplication.class,args);
    }
}
