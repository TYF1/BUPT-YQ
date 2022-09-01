package teleDemo.service;

import teleDemo.entities.tbuser;
import teleDemo.entities.tbInfo;

import teleDemo.mapper.userInfoMapper;
import teleDemo.mapper.comInfoMapper;

import teleDemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Math;

import static jdk.nashorn.internal.objects.Global.print;
import static sun.misc.Version.println;


@Service
public class InfoService {

    @Autowired
    private comInfoMapper comInfoMapper;

    public UserService userService;

    //@Todo 改进降重函数
    public List<tbInfo> dupRemove(List<tbInfo> tbInfo){

        List<tbInfo> re_tbInfo = tbInfo;
        for (int x = 0; x < re_tbInfo.size(); x++) {
            for (int j = 0; j < re_tbInfo.size(); j++) {
                if(x!=j && re_tbInfo.get(x).getUserId()==re_tbInfo.get(j).getUserId()) {
                    re_tbInfo.remove(re_tbInfo.get(j));
                }
            }
        }
        return re_tbInfo;
    }

    // 通过传入的确诊的患者的 地区码 ，经度 和 纬度 来获取符合密接要求的人群
    public List<tbInfo> getInfoByLac(String lac, double X, double Y){

        double X1 = X;
        double Y1 = Y;

        double R = 6371.0;

        List<tbInfo> tbInfo = comInfoMapper.getInfoByLac(lac);
        int size = comInfoMapper.getInfoByLac(lac).size();
        int i;
        for(i=0;i<tbInfo.size();i++){
            // D=R*arcos [cos (Y1)*cos (Y2)*cos (X1-X2)+sin (Y1)*sin (Y2)]
            // 其中X1,X2为经度，Y1,Y2为纬度； 视计算程序需要转化为弧度（*3.1415926/180）
            // 地球半径为R=6371.0 km
            // 则两点距离d=R*arcos [cos (Y1)*cos (Y2)*cos (X1-X2)+sin (Y1)*sin (Y2)]
            double X2 = tbInfo.get(i).getLat();
            double Y2 = tbInfo.get(i).getLon();
            double D = R*Math.acos(Math.cos(Y1)*Math.cos(Y2)*Math.cos(X1-X2) + Math.sin(Y1)*Math.sin(Y2));

            if(D>0.01){
                tbInfo.remove(i);
            }

        }

        // 去重
        // List<tbInfo> re_tbInfo = dupRemove(tbInfo);




        return tbInfo;
        // return re_tbInfo;
    }

    public tbInfo getNewInfo(int userId) throws ParseException {
        List<tbInfo> info = comInfoMapper.getInfoById(userId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = format.parse("2010-01-01 00:00:00"); //记录最新时间
        int newInfoNum = 0; //记录最新上报位置

        if(info == null)
            return null;
        for(int i = info.size()-1; i>=0; i--){
            boolean before = newDate.before(info.get(i).getDateTime()); //上报时间是否在newDate之后，是则返回TRUE
            if(before == true){
                newDate = info.get(i).getDateTime();
                newInfoNum = i;
                System.out.println(i);
            }
        }
        return info.get(newInfoNum);
    }


    //  更新人员状态
    public List<tbInfo> updateUserStatus(List<tbInfo> tbinfo,int size, int id){
        List<tbInfo> tbinfo2 = tbinfo;
        for (int i = 0; i < size; i++) {
            int temp_id = tbinfo2.get(i).getUserId();
            tbuser tbuser = userService.findUserById(temp_id);
            if (temp_id != id) {
                tbuser temp = new tbuser(temp_id, tbuser.getPhoneNumber(), "2");
                userService.update(temp);
            }

        }

        return tbinfo2;
    }
}
