package teleDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import teleDemo.entities.GetVo;
import teleDemo.entities.tbInfo;
import teleDemo.mapper.comInfoMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
public class teleinfoController {
    @Resource
    comInfoMapper comInfoMapper;
    @Resource
    teleDemo.mapper.userInfoMapper userInfoMapper;

    @GetMapping("/v1/comInfo")
    public GetVo gettbInfo(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int size = comInfoMapper.getAlltbINfo().size();
        List<tbInfo> tbInfos = comInfoMapper.gettbINfoByPage((page-1)*limit,limit);
        GetVo<tbInfo> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);

        List<tbInfo> tmp = getEffectedLonLat();
        for(tbInfo tmpInfo : tmp){
            tmpInfo.print();
        }
        tmp = getCloseContactsLonLat();
        for(tbInfo tmpInfo : tmp){
            tmpInfo.print();
        }

        return  getVo;
    }

    public List<tbInfo> getEffectedLonLat(){
        List<Integer> effected = userInfoMapper.getAllEffected();
        List<tbInfo> infoList = new ArrayList<>();
        for (Integer integer : effected) {
            List<tbInfo> tmp = comInfoMapper.getEffectedLatLon(integer);
            for (int i = 0; i < tmp.size() - 1; i++) {
                for (int j = tmp.size() - 1; j > i; j--) {
                    if (tmp.get(j).getLat() == tmp.get(i).getLat() &&
                            tmp.get(j).getLon() == tmp.get(i).getLon()) {
                        tmp.remove(j);
                    }
                }
            }
            infoList.addAll(tmp);
        }
        return infoList;
    }

    public List<tbInfo> getCloseContactsLonLat(){
        List<Integer> effected = userInfoMapper.getAllCloseContacts();
        List<tbInfo> infoList = new ArrayList<>();
        for (Integer integer : effected) {
            List<tbInfo> tmp = comInfoMapper.getCloseContactsLatLon(integer);
            for (int i = 0; i < tmp.size() - 1; i++) {
                for (int j = tmp.size() - 1; j > i; j--) {
                    if (tmp.get(j).getLat() == tmp.get(i).getLat() &&
                            tmp.get(j).getLon() == tmp.get(i).getLon()) {
                        tmp.remove(j);
                    }
                }
            }
            infoList.addAll(tmp);
        }
        return infoList;
    }

    public void getLatLon(List<tbInfo> list){
        for(tbInfo tbInfo: list){
            System.out.println(tbInfo.getLat() + " "+ tbInfo.getLon());
        }
    }
}
