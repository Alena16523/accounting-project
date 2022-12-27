package com.cydeo.javahedgehogsproject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaHedgehogsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaHedgehogsProjectApplication.class, args);
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}
