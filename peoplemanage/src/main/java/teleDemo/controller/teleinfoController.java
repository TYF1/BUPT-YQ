package teleDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import teleDemo.entities.GetVo;
import teleDemo.entities.tbInfo;
import teleDemo.entities.tbuser;
import teleDemo.mapper.*;

import java.util.List;

@RestController
public class teleinfoController {
    @Resource
    comInfoMapper comInfoMapper;
    @Resource
    userInfoMapper userInfoMapper;

    @GetMapping("/v1/comInfo")
    public GetVo gettbInfo(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int size = comInfoMapper.getAlltbINfo().size();
        List<tbInfo> tbInfos = comInfoMapper.gettbINfoByPage((page-1)*limit,limit);
        GetVo<tbInfo> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);
        return  getVo;
    }

    @GetMapping("/v1/userInfo")
    public GetVo gettbUSer(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        String status = request.getParameter("status");
        int size = userInfoMapper.getAlltbUser(status).size();
        List<tbuser> tbInfos = userInfoMapper.gettbUserByPage(status,(page-1)*limit,limit);
        GetVo<tbuser> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);
        return  getVo;
    }

    @PostMapping("/v1/userDel")
    public GetVo userDel(HttpServletRequest request, Integer id){
        userInfoMapper.delUser(id);
        GetVo<tbuser> getVo = new GetVo<>(0,"成功！");
        return  getVo;
    }

    @PostMapping("/v1/userAdd")
    public GetVo userAdd(HttpServletRequest request, String phoneNumber, String status){
        userInfoMapper.userAdd(phoneNumber,status);
        GetVo<tbuser> getVo = new GetVo<>(0,"成功！");
        return  getVo;
    }

    @PostMapping("/v1/userUpdate")
    public GetVo userUpdate(HttpServletRequest request,Integer id, String phoneNumber, String status){
        userInfoMapper.updateUser(id,phoneNumber,status);
        GetVo<tbuser> getVo = new GetVo<>(0,"成功！");
        return  getVo;
    }
}
