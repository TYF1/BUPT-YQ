package teleDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import teleDemo.entities.UpdateHelper;
import teleDemo.mapper.comInfoMapper;
import teleDemo.mapper.userInfoMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Controller
public class PageController {
    @Resource
    teleDemo.mapper.comInfoMapper comInfoMapper;
    @Resource
    teleDemo.mapper.userInfoMapper userInfoMapper;
    @GetMapping("/v1/userInfo/update")
    public String updatetbUser(HttpServletRequest request){

        List<Integer> hasUpdate=new ArrayList<Integer>();
        int id = Integer.valueOf(request.getParameter("id"));
        String status = String.valueOf(request.getParameter("status"));
        Date datetime = Date.valueOf(request.getParameter("datetime"));
        UpdateHelper updateHelper=new UpdateHelper(comInfoMapper,userInfoMapper);
        int cnt = updateHelper.updateUserInfo(id,status,datetime);
        System.out.println("成功更新了"+cnt+"条纪录");
        return "redirect:http://localhost:63342/firstwork/demo/teledemo%E5%89%8D%E7%AB%AF/userinfo.html";
    }
}
