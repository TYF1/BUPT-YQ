package teleDemo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import teleDemo.entities.GetVo;
import teleDemo.entities.tbInfo;
import teleDemo.entities.tbuser;
import teleDemo.mapper.*;

@Controller
public class pageController {
    @RequestMapping("/test")
    public String test() {
        return "index.html";
    }
}
