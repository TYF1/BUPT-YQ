package teleDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teleDemo.entities.GetVo;
import teleDemo.entities.TbUser;
import teleDemo.mapper.UserInfoMapper;
import teleDemo.service.TrackService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project Name:trackmanage
 * @File Name: TrackController
 * @Description: 用于输出轨迹数据
 * @ HISTORY：
 * Created   2022.8.23  WYJ
 * Modified  2022.8.23  WYJ
 */
@RestController
public class TrackController {
    @Resource
    UserInfoMapper userMapper;

    @Resource
    TrackService trackService;

    private final String[] DEFINITE_NOT_HEALTH = {"1", "2", "3"};

    @GetMapping("/gettrack")
    public GetVo gettbInfo(@RequestParam("searchId") int userId) {
        TbUser user = userMapper.gettbUserById(userId);
        ArrayList<ArrayList<ArrayList<Double>>> ret = new ArrayList<>();
        boolean canGetTrack = DEFINITE_NOT_HEALTH[0].equals(user.getStatus()) ||
                DEFINITE_NOT_HEALTH[1].equals(user.getStatus()) ||
                DEFINITE_NOT_HEALTH[2].equals(user.getStatus());

        if (canGetTrack) {
            ArrayList<ArrayList<Double>> tbInfos = trackService.gettbInfo(userId);
            GetVo<ArrayList<ArrayList<Double>>> getVo;
            if (tbInfos != null && tbInfos.size() != 0) {
                ret.add(tbInfos);
                int size = tbInfos.size();
                getVo = new GetVo<>(0, String.valueOf(user.getStatus()), size, ret);
                return getVo;
            }
        }
        GetVo<ArrayList<ArrayList<Double>>> getVo = new GetVo<>(-1, String.valueOf(user.getStatus()), 0, ret);
        return getVo;

    }

    @GetMapping("/getalltrack")
    public GetVo getAlltbInfo(@RequestParam("status") String status) {
        ArrayList<ArrayList<ArrayList<Double>>> ret = new ArrayList<>();
        List<TbUser> list = userMapper.getAlltbUser();
        for (TbUser user : list) {
            if (status.equals(user.getStatus())) {
                ArrayList<ArrayList<Double>> tmp = trackService.gettbInfo(user.getId());
                if (tmp != null && tmp.size() != 0) {
                    ret.add(tmp);
                }
            }
        }
        int size = ret.size();
        GetVo<ArrayList<ArrayList<Double>>> getVo = new GetVo<>(0, "获取数据成功！", size, ret);
        return getVo;
    }
}
