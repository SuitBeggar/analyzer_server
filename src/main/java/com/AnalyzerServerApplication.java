package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @Author:bella
 * @Date:2019/12/222:46
 * @Version:
 **/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@ComponentScan(value = "jieba")
public class AnalyzerServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnalyzerServerApplication.class, args);
    }
}
