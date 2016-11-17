package com.challengeaccepted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.challengeaccepted.security")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}