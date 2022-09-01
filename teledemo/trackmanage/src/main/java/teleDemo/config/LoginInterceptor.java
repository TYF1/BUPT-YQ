package teleDemo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import teleDemo.entities.UserLogin;
import teleDemo.mapper.UserLoginMapper;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @Project Name:trackmanage
 * @File Name: LoginInterceptor
 * @Description: 登录拦截器
 * @ HISTORY：
 * Created   2022.8.22  WYJ
 * Modified  2022.8.22  WYJ
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final String FRONT_URL = "http://47.107.228.5";
    private static final String LOGIN = "login";
    private static final String HOME_PAGE = "index";
    private static final String REGISTER = "regist";
    private static final String USERNAME = "username";
    @Resource
    UserLoginMapper userLoginMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //需要把Cookie发到服务端
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //允许跨域的域名
        response.setHeader("Access-Control-Allow-Origin", FRONT_URL);
        //表明服务器支持的头信息字段
        response.setHeader("Access-Control-Allow-Headers", "content-type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String uri = request.getRequestURI();
        boolean notRequireLogin = uri.contains(LOGIN) || uri.contains(HOME_PAGE) || uri.contains(REGISTER)||uri.contains(USERNAME);
        if (notRequireLogin) {
            return true;
        }

        //判断用户是否登录
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    UserLogin user = CreatJwt.getUser(token);
                    List<UserLogin> list = userLoginMapper.getUserByName(user.getUserName());
                    if (list != null && list.size() != 0) {
                        return true;
                    }
                }
            }
        }
        // 未登录，拦截
        String url = "";
        url = request.getScheme() +"://" + request.getServerName()
                + ":" +request.getServerPort()
                + request.getServletPath();
        response.setHeader("message", "Not logged in, please log in and use the function");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}