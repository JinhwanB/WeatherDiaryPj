package com.jh.weatherdiarypj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WeatherdiarypjApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherdiarypjApplication.class, args);
    }

}
