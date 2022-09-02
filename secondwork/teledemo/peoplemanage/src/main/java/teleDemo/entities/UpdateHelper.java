package teleDemo.entities;

import teleDemo.mapper.comInfoMapper;
import teleDemo.mapper.userInfoMapper;
import teleDemo.mapper.areainfoMapper;
import teleDemo.mapper.updateRecordMapper;

import java.sql.Date;
import java.util.*;

public class UpdateHelper {
    class latAndLon {
        private double lat;
        private double lon;

        public latAndLon(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public String toString() {
            return "latAndLon{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            latAndLon latAndLon = (latAndLon) o;
            return Math.abs(lat - latAndLon.lat) < 0.01 && Math.abs(lon - latAndLon.lon) < 0.01;
        }

        @Override
        public int hashCode() {
            return Objects.hash(lat, lon);
        }
    }

    private int cnt;
    private List<Integer> hasUpdate;

    public UpdateHelper(teleDemo.mapper.comInfoMapper comInfoMapper, teleDemo.mapper.userInfoMapper userInfoMapper, teleDemo.mapper.updateRecordMapper updateRecordMapper,teleDemo.mapper.areainfoMapper areainfoMapper) {
        this.cnt = 0;
        this.comInfoMapper = comInfoMapper;
        this.userInfoMapper = userInfoMapper;
        this.updateRecordMapper = updateRecordMapper;
        this.areainfoMapper = areainfoMapper;
        this.hasUpdate = new ArrayList<>();
    }

    comInfoMapper comInfoMapper;
    userInfoMapper userInfoMapper;
    areainfoMapper areainfoMapper;
    updateRecordMapper updateRecordMapper;
    Map<Integer,String> result_map=new TreeMap<>();
    public Map<Integer,String> pre_status_map=new TreeMap<>();


    public Map<Integer, String> updateUserInfo(int id, String status, Date datetime) {
        if(hasUpdate.contains(id)){
            String tmp_stat = userInfoMapper.selectUserStatus(id);
            if(Integer.valueOf(tmp_stat)>Integer.valueOf(status)){
                if(userInfoMapper.updateUserStatus(status,id)){
                    hasUpdate.add(id);
                    if(result_map.containsKey(id)){
                        result_map.replace(id,status);
                    }else{
                        result_map.put(id,status);
                    }
                    System.out.println("成功更新用户状态,用户"+id+"的状态更新为"+status);
                }else{
                    System.out.println("更新用户状态失败");
                    return null;
                }
            }
        }else{
            //第一次修改是所有的都可以update成功
            pre_status_map.put(id,userInfoMapper.selectUserStatus(id));
            if(userInfoMapper.updateUserStatus(status,id)){
                hasUpdate.add(id);
                if(result_map.containsKey(id)){
                    result_map.replace(id,status);
                }else{
                    result_map.put(id,status);
                }
                System.out.println("成功更新用户状态,用户"+id+"的状态更新为"+status);
            }else{
                System.out.println("更新用户状态失败");
                return null;
            }
        }
        //如果是确诊
        if (status.equals("1")) {
            //如果確診 應該先存入數據庫
            Date starttime = new Date(datetime.getYear(), datetime.getMonth(), datetime.getDate() - 1);
            Date endtime = new Date(datetime.getYear(), datetime.getMonth(), datetime.getDate() + 1);
            System.out.println("starttime:" + starttime + "    endtime:" + endtime);
            //找到wifi和gps信息
            List<tbInfo> tbInfoList = comInfoMapper.gettbINfoByUserId(id, starttime, endtime);
            if (status.equals("1")) {
                for (tbInfo tbInfo : tbInfoList) {
                    tbarea tbarea = new tbarea(1, 1, tbInfo.getLat(), tbInfo.getLon());
                    teleDemo.entities.tbarea areaByArea = areainfoMapper.getAreaByArea(tbarea);
                    if (areaByArea != null) {
                        areaByArea.setNum(areaByArea.getNum() + 1);
                        areainfoMapper.updateArea(areaByArea);
                    } else {
                        areainfoMapper.insertArea(tbarea);
                    }

                }
            }
            findMoreContactUser(id, datetime, "2");
        } else if (status.equals("2")) {
            findMoreContactUser(id, datetime, "3");
        }

        return result_map;
    }

    //找到更多和确诊或密接关联的用户
    private void findMoreContactUser(int id, Date datetime, String status) {
        //划定时间范围
        Date starttime = new Date(datetime.getYear(), datetime.getMonth(), datetime.getDate() - 1);
        Date endtime = new Date(datetime.getYear(), datetime.getMonth(), datetime.getDate() + 1);
        System.out.println("starttime:" + starttime + "    endtime:" + endtime);
        //找到wifi和gps信息
        List<tbInfo> tbInfoList = comInfoMapper.gettbINfoByUserId(id, starttime, endtime);
        List<Integer> id_list = new ArrayList<Integer>();
        findContactUserIdByWifi(tbInfoList, id_list, starttime, endtime);
        findContactUserIdByLocate(tbInfoList, id_list, starttime, endtime);
        if(id_list!=null){
            //对每个用户的状态进行修改
            for (int i : id_list) {
                updateUserInfo(i, status, datetime);
            }
        }
    }

    private void findContactUserIdByLocate(List<tbInfo> tbInfoList, List<Integer> id_list, Date starttime, Date endtime) {
        List<Integer> id_list_locate = new ArrayList<Integer>();
        List<latAndLon> latAndLonsList = new ArrayList<>();
        final double D = 0.002;
        //出现过的经纬度
        for (tbInfo info : tbInfoList) {
            double lat = info.getLat();
            double lon = info.getLon();
            latAndLon latAndLon = new latAndLon(lat, lon);
            if (!latAndLonsList.contains(latAndLon)) {
                latAndLonsList.add(latAndLon);
                System.out.println(latAndLon.toString());
            }
        }
        //根据经纬度查id
        for (latAndLon i : latAndLonsList) {
            List<Integer> list_tmp = comInfoMapper.getUserIdListByLatAndLon(i.lat - D, i.lat + D, i.lon - D, i.lon + D);
            for (Integer id : list_tmp) {
                if (!id_list.contains(id)) {
                    id_list.add(id);
                }
            }
        }
    }

    private void findContactUserIdByWifi(List<tbInfo> tbInfoList, List<Integer> id_list, Date starttime, Date endtime) {
        //筛出cid
        List<String> cid_list = new ArrayList<String>();
        for (tbInfo info : tbInfoList) {
            String cid = info.getCid();
            if (!cid_list.contains(cid)) {
                cid_list.add(cid);
                System.out.println("cid=" + cid);
            }
        }
        //根据cid查用户
        for (String cid : cid_list) {
            List<Integer> id_list_wifi = comInfoMapper.getUserIdListByWifi(cid, starttime, endtime);
            for (int i : id_list_wifi) {
                if (!id_list.contains(i)) {
                    id_list.add(i);
                }
            }
        }
    }
}
