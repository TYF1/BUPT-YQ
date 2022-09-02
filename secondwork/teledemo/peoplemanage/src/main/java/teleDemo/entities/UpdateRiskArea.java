package teleDemo.entities;

import teleDemo.mapper.comInfoMapper;
import teleDemo.mapper.userInfoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateRiskArea {
    private comInfoMapper comInfoMapper;
    private userInfoMapper userInfoMapper;

    class location {
        private double lat;
        private double lon;

        public location(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }


        public int hashCode() {
            return Objects.hash(lat, lon);
        }

        public boolean impactArea(Object object) {
            if (this == object) return true;
            location location = (UpdateRiskArea.location) object;
            return (lat - location.lat) < 0.01 && (lon - location.lon) < 0.01;
        }

    }

    private int cnt;
    private List<Integer> hasUpdate;

    public void UpdateRiskArea(teleDemo.mapper.comInfoMapper comInfoMapper, teleDemo.mapper.userInfoMapper userInfoMapper) {
        this.cnt = 0;
        this.comInfoMapper = comInfoMapper;
        this.userInfoMapper = userInfoMapper;
        this.hasUpdate = new ArrayList<>();
    }

    public int updateUserInfo(int id, String ASTATUS) {

        if (hasUpdate.contains(id)) {
            String areaState = userInfoMapper.selectUserStatus(id);
            if (Integer.valueOf(areaState) > Integer.valueOf(ASTATUS)) {
                if (userInfoMapper.updateUserStatus(ASTATUS, id)) {
                    hasUpdate.add(id);
                    cnt++;
                    System.out.println("成功更新用户区域状态,用户" + id + "的状态更新为" + ASTATUS);
                } else {
                    System.out.println("更新用户状态失败");
                    return id;
                }
            }
        } else {
            //第一次修改是所有的都可以update成功
            if (userInfoMapper.updateUserStatus(ASTATUS, id)) {
                hasUpdate.add(id);
                cnt++;
                System.out.println("成功更新用户状态,用户" + id + "的状态更新为" + ASTATUS);
            } else {
                System.out.println("更新区域状态失败");
                return id;
            }
        }
        //如果是确诊
        if (ASTATUS.equals("1")) {
            findHighRiskArea(id, "1");
        }
        return cnt;
    }

    public void findHighRiskArea(int id, String ASTATUS) {
        List<tbInfo> tbInfoList = comInfoMapper.getAlltbINfo();
        List<Integer> id_list = new ArrayList<Integer>();
        findHighRiskUserBylocation(tbInfoList, id_list);
        for (int i : id_list) {
            updateUserInfo(i, ASTATUS);
        }
    }

    public void findHighRiskUserBylocation(List<tbInfo> tbInfoList, List<Integer> id_list) {
        List<location> locationList = new ArrayList<>();
        List<Integer> id_locate = new ArrayList<Integer>();
        for (location i : locationList) {
            List<Integer> list_tmp = comInfoMapper.getUserIdListByLatAndLon(i.lat - 0.01, i.lat + 0.01, i.lon - 0.01, i.lon + 0.01);
            for (Integer id : list_tmp) {
                if (!id_list.contains(id)) {
                    id_list.add(id);
                }
            }
        }
    }


}
