package com.group5.opinionmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 10569
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class OpinionmanageApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpinionmanageApplication.class, args);
    }

}
