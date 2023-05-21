package com.lightdevel.smg;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmgConsumer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SmgConsumer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while(true) {
            Thread.sleep(5000L);
        }
    }
}
