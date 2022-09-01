package com.tetesense.publicopinion;

import com.tetesense.publicopinion.weibo.crawler.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.module.Configuration;

@SpringBootApplication
public class PublicOpinionApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublicOpinionApplication.class);
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(PublicOpinionApplication.class, args);
//        WebSocketServer.setApplicationContext(applicationContext);
    }

}
