package teleDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/teleInfo")
    public String toTeleInfo(){
        return "teleinfo";
    }

    @RequestMapping("/userInfo")
    public String toUserInfo(){
        return "userinfo";
    }

    @RequestMapping("/map")
    public String toMap(){
        return "map";
    }

}
