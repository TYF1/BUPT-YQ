package teleDemo.config;


import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;
/**
 * @Project Name:trackmanage
 * @File Name: MyMvcConfig
 * @Description: 配置控制器、拦截器等
 * @ HISTORY：
 *    Created   2022.8.22  TOM
 *    Modified  2022.8.22  WYJ
 */
@Configuration
public class MyMvcConfig extends WebMvcAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }

    /**
     * 控制器配置
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/toIndexPage").setViewName("/index");
        registry.addViewController("/").setViewName("/index");
    }



    /**
     * 拦截器配置
     */

    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册Interceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(getMyInterceptor());
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //添加不拦截路径
        registration.excludePathPatterns(
                //html静态资源
                "/**/*.html",
                //js静态资源
                "/**/*.js",
                //css静态资源
                "/**/*.css"
        );
    }
}
