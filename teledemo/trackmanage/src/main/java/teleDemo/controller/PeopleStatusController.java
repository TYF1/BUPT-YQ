package teleDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teleDemo.entities.GetVo;
import teleDemo.entities.PostVo;
import teleDemo.entities.TbUser;
import teleDemo.mapper.UserInfoMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project Name:trackmanage
 * @File Name: PeopleStatusController
 * @Description: 进行用户信息的管理
 * @ HISTORY：
 *    Created   2022.8.22  ZH
 *    Modified  2022.8.23  WYJ
 */
@RestController
public class PeopleStatusController {

    @Resource
    UserInfoMapper userMapper;
    @GetMapping("/getuserpage")
    public GetVo<TbUser> getuserpage(@RequestParam(value = "searchId",defaultValue = "0") int searchId, @RequestParam("page")int page, @RequestParam("limit")int limit){
        GetVo<TbUser> tbuserGetVo;
        if(searchId==0){
            int size = userMapper.getAlltbUser().size();
            List<TbUser> tbInfos = userMapper.gettbUserByPage((page-1)*limit,limit);
            tbuserGetVo = new GetVo<>(0, "获取数据成功！", size, tbInfos);
        }else {
            TbUser user = userMapper.gettbUserById(searchId);
            List<TbUser> tbInfos = new ArrayList<>();
            if(user!=null){
                tbInfos.add(user);
            }
            tbuserGetVo = new GetVo<>(0, "获取数据成功！", tbInfos.size(), tbInfos);
        }
        return tbuserGetVo;
    }

    @PostMapping("/changeuserstatus")
    public PostVo<TbUser> changeuserstatus(@RequestParam("id")String id, @RequestParam("phoneNumber")String phoneNumber, @RequestParam("status")String status ){
        int ret = userMapper.changeUser(String.valueOf(status),Integer.valueOf(id));
        PostVo<TbUser> tbuserPostVo;
        if(ret==1){
            tbuserPostVo = new PostVo<>(0, "修改数据成功！", null);
        }else {
            tbuserPostVo = new PostVo<>(1, "修改数据失败！", null);
        }
        return tbuserPostVo;
    }

    @PostMapping("/deleteuser")
    public PostVo<TbUser> deleteuser(@RequestParam("id")int id){
        int ret = userMapper.deleteUserById(id);
        PostVo<TbUser> tbuserDeleteVo;
        if(ret==1){
            tbuserDeleteVo = new PostVo<>(0, "删除数据成功！", null);
        }else {
            tbuserDeleteVo = new PostVo<>(1, "删除数据失败！", null);
        }
        return tbuserDeleteVo;
    }
}

