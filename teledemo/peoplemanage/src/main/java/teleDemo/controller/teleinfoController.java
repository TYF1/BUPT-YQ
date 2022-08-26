package teleDemo.controller;

import org.springframework.web.bind.annotation.*;
import teleDemo.entities.GetVo;
import teleDemo.entities.tbInfo;
import teleDemo.entities.tbRelation;
import teleDemo.entities.tbuser;
import teleDemo.mapper.comInfoMapper;
import teleDemo.mapper.relationInfoMapper;
import teleDemo.mapper.userInfoMapper;
import teleDemo.service.InfoService;
import teleDemo.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
public class teleinfoController {
    @Resource
    comInfoMapper comInfoMapper;
    @Resource
    userInfoMapper userInfoMapper;
    @Resource
    relationInfoMapper relationInfoMapper;

    @Resource
    UserService userservice;
    @Resource
    InfoService infoservice;
    @Resource
    teleDemo.service.relationService relationService;

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
        int size = userInfoMapper.getAlltbUser().size();
        List<tbuser> tbInfos = userInfoMapper.gettbUserByPage((page-1)*limit,limit);
        GetVo<tbuser> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);
        return  getVo;
    }

    @GetMapping("/v1/userRelationInfo")
    public List<tbRelation> gettbRelation(HttpServletRequest request){
        List<tbRelation> relationInfo = relationInfoMapper.getAlltbRelation();
        int size = relationInfo.size();
        //GetVo<tbRelation> getVo = new GetVo<>(0,"获取数据成功！",size,relationInfo);
        return relationInfo;
    }
    //  增加用户
    @PostMapping("/v1/adduserinfo")
    public String AddUserInfo(@RequestBody tbuser user){
        userservice.Add(user);
        // userInfoMapper.add(user);
        return "add success";
    }

    // 删除用户
    @RequestMapping("/v1/deleteuserinfo")
    public String deleteUserInfo(@RequestParam Integer id){
        userservice.delete(id);
        return "delete success";
    }

    // 更改用户信息
    @PostMapping("/v1/updateuserinfo")
    public String updateUserInfo(@RequestParam Integer id, @RequestParam String phoneNumber, @RequestParam String status){
        tbuser user = new tbuser(id, phoneNumber, status);
        userservice.update(user);
        return "update success!";
    }

    //  查
    @GetMapping("/v1/finduserinfo")
    public GetVo findUserInfo(int id){
        List<tbuser> user = userservice.find(id);
        GetVo<tbuser> getVo = new GetVo<>(0,"获取数据成功！",1,user);
        return getVo;
    }

    //  查（改）
    @GetMapping("/v1/finduserbyid")
    public tbuser findUserById(int id){
        tbuser user = userservice.findUserById(id);
        return user;
    }

//
//
//    @GetMapping("/v1/getinfobylac")
//    public GetVo getInfoByLac(String lac){
//        double X = 39.963096;
//        double Y = 116.350591;
//        List<tbInfo> tbinfo = infoservice.getInfoByLac(lac,X,Y);
//        int size = tbinfo.size();
//        GetVo<tbInfo> getVo = new GetVo<>(0,"获取数据成功！",size,tbinfo);
//        return getVo;
//    }

    //  根据确诊人员的地区码以及经纬度计算密接
    @GetMapping("/v1/getconfirmed")
    public GetVo getConfirmed() throws ParseException {
        List<tbuser> users = userservice.findConfirmed();
        int id = users.get(0).getId();
        //  List<tbInfo> infos = comInfoMapper.getInfoById(id);

        //  获取确诊人员的最后一次上报位置
        tbInfo infos = infoservice.getNewInfo(id);

        String lac = infos.getLac();
        double X = infos.getLat();
        double Y = infos.getLon();
        List<tbInfo> tbinfo2 = infoservice.getInfoByLac(lac, X, Y);

//        for (int x = 0; x < tbinfo2.size(); x++) {
//            for (int j = 0; j < tbinfo2.size(); j++) {
//                if(x!=j && tbinfo2.get(x).getUserId()==tbinfo2.get(j).getUserId()) {
//                    tbinfo2.remove(tbinfo2.get(j));
//                }
//            }
//
//        }
        tbinfo2 = infoservice.dupRemove(tbinfo2);
        tbinfo2 = infoservice.dupRemove(tbinfo2);


        int size = tbinfo2.size();

        //  更新密接人员
        //@Todo 打包到Service
        for(int i=0;i<size;i++){
            int temp_id = tbinfo2.get(i).getUserId();
            tbuser tbuser = userservice.findUserById(temp_id);
            if(temp_id != id){
                tbuser temp = new tbuser(temp_id, tbuser.getPhoneNumber(), "2");
                userservice.update(temp);
            }

        }
        GetVo<tbInfo> getVo = new GetVo<>(0,"获取成功！",size, tbinfo2);
        return getVo;

    }

}
