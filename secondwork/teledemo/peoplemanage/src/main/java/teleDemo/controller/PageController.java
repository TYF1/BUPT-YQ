package teleDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import teleDemo.entities.UpdateHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
    @Resource
    teleDemo.mapper.comInfoMapper comInfoMapper;
    @Resource
    teleDemo.mapper.userInfoMapper userInfoMapper;
    @Resource
    teleDemo.mapper.areainfoMapper areainfoMapper;
    @Resource
    teleDemo.mapper.updateRecordMapper updateRecordMapper;
    @GetMapping("/v1/userInfo/update")
    public String updatetbUser(HttpServletRequest request){

        List<Integer> hasUpdate=new ArrayList<Integer>();
        int id = Integer.valueOf(request.getParameter("id"));
        String status = String.valueOf(request.getParameter("status"));
        Date datetime = Date.valueOf(request.getParameter("datetime"));
        UpdateHelper updateHelper=new UpdateHelper(comInfoMapper,userInfoMapper,updateRecordMapper,areainfoMapper);
        Map<Integer,String> result_map = updateHelper.updateUserInfo(id,status,datetime);
        if(result_map!=null)
            for(Map.Entry<Integer,String> entry: result_map.entrySet()){
                updateRecordMapper.addRecord(entry.getKey(),updateHelper.pre_status_map.get(entry.getKey()),entry.getValue(),new java.sql.Date(System.currentTimeMillis()));
            }
        System.out.println("成功更新了"+result_map.size()+"条纪录");
        return "redirect:http://120.48.172.18:8000/userinfo.html";
    }
}
