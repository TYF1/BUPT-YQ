package teleDemo.service;

import org.springframework.stereotype.Service;
import teleDemo.entities.TbInfo;
import teleDemo.mapper.ComInfoMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 * @Project Name:trackmanage
 * @File Name: TrackService
 * @Description: 用于查询并处理轨迹数据
 * @ HISTORY：
 *    Created   2022.8.24  WYJ
 *    Modified  2022.8.24  WYJ
 */
@Service
public class TrackService {
    @Resource
    ComInfoMapper comInfoMapper;

    public ArrayList<ArrayList<Double>> gettbInfo(int userId) {
        List<TbInfo> tbInfos = comInfoMapper.gettbInfoById(userId);
        if (tbInfos.size() < 1) {
            return new ArrayList<>();
        }
        ArrayList<ArrayList<Double>> list = new ArrayList<>(tbInfos.size() * 2);
        for (TbInfo info : tbInfos) {
            ArrayList<Double> tmp = new ArrayList<>();
            tmp.add(info.getLon());
            tmp.add(info.getLat());
            list.add(tmp);
        }
        return list;
    }
}
