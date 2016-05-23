package com.challengeaccepted;

import org.apache.juli.FileHandler;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }
}