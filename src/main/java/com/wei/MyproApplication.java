package com.wei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import java.util.TimeZone;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@SuppressWarnings("all")
@SpringBootApplication
@MapperScan("com.wei.mapper")
public class MyproApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    //项目启动时区
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    public static void main(String[] args) {
        SpringApplication.run(MyproApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyproApplication.class);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("500MB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("2500MB");
        return factory.createMultipartConfig();
    }

}
