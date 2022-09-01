package teleDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/")
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @RequestMapping("/teleinfo")
    public String teleinfo() {
        return "/teleinfo";
    }



    @RequestMapping("/telePie")
    public String telePie() {
        return "/telePie";
    }

    @RequestMapping("/telerelation")
    public String telerelation() {
        return "/telerelation";
    }

    @RequestMapping("/unicode")
    public String unicode() {
        return "/unicode";
    }

    @RequestMapping("/userinfo")
    public String userinfo() {
        return "/userinfo";
    }

//    @GetMapping("userinfo")
//    public String demo1() {
//        return "/userinfo";
//    }

}