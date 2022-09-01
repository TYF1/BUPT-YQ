package teleDemo.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import teleDemo.config.CreatJwt;
import teleDemo.entities.*;
import teleDemo.mapper.ComInfoMapper;
import teleDemo.mapper.UserLoginMapper;
import teleDemo.mapper.UserRegistMapper;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Project Name:trackmanage
 * @File Name: TestController
 * @Description: 用于测试全局异常处理等
 * @ HISTORY：
 *    Created   2022.8.22  WYJ
 *    Modified  2022.8.23  ZNY
 */
@RestController
public class LoginController {
    @Resource
    ComInfoMapper comInfoMapper;

    @GetMapping("/advice")
    public String test() {
        int i = 1 / 0;
        return "test success";
    }

    @Resource
    UserLoginMapper userLoginMapper;

    @PostMapping("/login")
    public PostVo<String> login(@RequestParam(value="username",defaultValue="")String username, @RequestParam(value ="password",defaultValue="")String password, HttpServletResponse response) {
        List<UserLogin> list=userLoginMapper.getUserLogin(username,password);
        if(!list.isEmpty()){
            Cookie cookie=new Cookie("token", CreatJwt.getoken(list.get(0)));
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new PostVo<String>(0,"登录成功！",null);
        }else{
            Cookie cookie=new Cookie("token", "");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new PostVo<String>(-1,"登录失败！",null);
        }
    }

    @Resource
    UserRegistMapper userRegistMapper;

    @PostMapping("/regist")
    public UserRegistVO regist(@Param("username")String username, @Param("password")String password){
        UserRegistVO result=new UserRegistVO();
        result.setResult(-1);

        UserLogin existUser= userRegistMapper.findUserByName(username);
        if(existUser!=null){
            result.setMsg("用户名已存在");
        }else{
            userRegistMapper.regist(username,password);
            result.setUserName(username);
            result.setPassword(password);
            result.setMsg("注册成功");
            result.setResult(0);
        }
        return result;
    }
}
