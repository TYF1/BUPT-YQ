package teleDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import teleDemo.entities.GetVo;
import teleDemo.entities.tbuser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
public class userInfoController {
    @Resource
    teleDemo.mapper.userInfoMapper userInfoMapper;

    @GetMapping("/v1/userInfo")
    public GetVo gettbUSer(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int size = userInfoMapper.getAlltbUser().size();
        List<tbuser> tbInfos = userInfoMapper.gettbUserByPage((page-1)*limit,limit);

        List<Integer> tmp = getEffected();
        for(Integer integer : tmp){
            System.out.println(integer);
        }
        tmp = getCloseContacts();
        for(Integer integer : tmp){
            System.out.println(integer);
        }

        return new GetVo<>(0,"获取数据成功！",size,tbInfos);
    }

    public List<Integer> getEffected(){
        return userInfoMapper.getAllEffected();
    }

    public List<Integer> getCloseContacts(){
        return userInfoMapper.getAllCloseContacts();
    }
}
