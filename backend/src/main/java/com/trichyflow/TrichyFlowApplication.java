package com.trichyflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrichyFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrichyFlowApplication.class, args);
        System.out.println("===============================================================");
        System.out.println("  TRICHYFLOW: Intelligent City Traffic Coordination Platform   ");
        System.out.println("  Local Portal: http://localhost:8080                          ");
        System.out.println("  REST APIs:    http://localhost:8080/api                      ");
        System.out.println("===============================================================");
    }
}
