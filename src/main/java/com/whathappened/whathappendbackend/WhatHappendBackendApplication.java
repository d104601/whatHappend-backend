package com.whathappened.whathappendbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class WhatHappendBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(WhatHappendBackendApplication.class, args);
    }

}
