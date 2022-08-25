package teleDemo.controller;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import teleDemo.entities.GetVo;
import teleDemo.entities.PostVo;
import teleDemo.entities.tbInfo;
import teleDemo.entities.tbuser;
import teleDemo.mapper.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<tbInfo> remove_redundancy(List<tbInfo> list){
        List<tbInfo> tbInfos1 = new ArrayList<>();
        Set<Double> lon = new HashSet<>();
        Set<Double> lat = new HashSet<>();
        for(tbInfo i : list){
            if(lat.contains(i.getLat()) && lon.contains(i.getLon())){
                continue;
            }else{
                lat.add(i.getLat());
                lon.add(i.getLon());
                tbInfos1.add(i);
            }
        }
        return tbInfos1;
    }
//新加----------------
    @GetMapping(value = "/v1/get_user_by_id")
    public GetVo get_user_by_id(@RequestParam(value = "user_id") int user_id){
        List<tbInfo> tbInfos = comInfoMapper.getUserInfoById(user_id);
        List<tbInfo> tbInfos1 = remove_redundancy(tbInfos);
        GetVo<tbInfo> getVo = new GetVo<>(0,"获取数据成功！",tbInfos1.size(),tbInfos1);
        return  getVo;
    }

    @GetMapping("/v1/get_all_user_id")
    public GetVo get_all_user_id(HttpServletRequest request){
        List<Integer> tbInfos = comInfoMapper.getAllUserId();
        int size = tbInfos.size();
        GetVo<Integer> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);
        return  getVo;
    }

    @GetMapping("/v1/get_all_user")
    public GetVo get_all_user(HttpServletRequest request){
        List<Integer> all_user_id = comInfoMapper.getAllUserId();
        ArrayList<List<tbInfo>> arrayList = new ArrayList<>();

        for (int i: all_user_id){
            arrayList.add(remove_redundancy(comInfoMapper.getUserInfoById(i)));
        }
        int size = all_user_id.size();
        GetVo<List<tbInfo>> getVo = new GetVo<>(0,"获取数据成功！",size,arrayList);
        return  getVo;
    }


    @RequestMapping(value = "/v1/update_user_status", method = RequestMethod.POST)
    public Boolean update_user_status(@RequestParam(value = "user_id") int user_id, @RequestParam(value = "status") int status){
        Boolean b = comInfoMapper.updateUserStatus(status, user_id);
        return b;
    }




    @RequestMapping(value = "/v1/delete_user", method = RequestMethod.DELETE)
    public Boolean delete_user_status(@RequestParam(value = "user_id") int user_id){
        Boolean b1 = comInfoMapper.deleteUser_tbuser(user_id);
        Boolean b2 = comInfoMapper.deleteUser_tbinfo(user_id);
        return b1 && b2;
    }
    //新加------------



    @GetMapping("/v1/userInfo")
    public GetVo gettbUSer(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int size = userInfoMapper.getAlltbUser().size();
        List<tbuser> tbInfos = userInfoMapper.gettbUserByPage((page-1)*limit,limit);
        GetVo<tbuser> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);
        return  getVo;
    }






}
