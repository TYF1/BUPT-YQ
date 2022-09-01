/**
  @Project Name:MSHD
 * @File Name: ApplicationContextConfig.java
 * @Description: 配置文件
 * @HISTORY：
 *    Created   2022.8.24  魏瑾
 */


package teleDemo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate ();
    }
}
