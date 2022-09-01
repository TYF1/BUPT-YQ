package teleDemo.controller;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import teleDemo.entities.*;
import teleDemo.mapper.*;

import java.util.*;

@RestController
public class teleinfoController {
    @Resource
    comInfoMapper comInfoMapper;
    @Resource
    userInfoMapper userInfoMapper;
    @Resource
    datewifiMapper datewifiMapper;

    static boolean analysing = false;

    @GetMapping("/v1/getWifiEvent")
    public GetVo<DateWifi> getWifiEvent(HttpServletRequest request) {
        List<DateWifi> eventList = datewifiMapper.getAllDateWifi();
        return new GetVo<>(0, "获取数据成功", eventList.size(), eventList);
    }

    @GetMapping("/v1/getWifiEventDetail")
    public GetVo<tbuser> getWifiEventDetail(HttpServletRequest request) {
        String date = request.getParameter("date");
        String wifi = request.getParameter("wifi");
        int status = Integer.parseInt(request.getParameter("status"));
        List<tbuser> userList = userInfoMapper.getUserByDateAndWifi(date, date + '%', "%\"" + wifi + "\"%", status);
        return new GetVo<>(0, "获取数据成功", userList.size(), userList);
    }

    @GetMapping("/v1/getWifiEventDetail2")
    public GetVo<tbuser> getWifiEventDetail2(HttpServletRequest request) {
        String date = request.getParameter("date");
        String wifi = request.getParameter("wifi");
        int status = Integer.parseInt(request.getParameter("status"));
        List<tbuser> userList = userInfoMapper.getUserByDateAndWifi2(date + '%', "%\"" + wifi + "\"%", status + 1);
        return new GetVo<>(0, "获取数据成功", userList.size(), userList);
    }

    @GetMapping("/v1/getStatusCount")
    public GetVo<StatusCount> getStatusCount(HttpServletRequest request) {
        List<StatusCount> count = userInfoMapper.getStatusCount();
        return new GetVo<>(0, "获取数据成功", count.size(), count);
    }

    @GetMapping("/v1/analyse")
    public GetVo<tbInfo> analyse(HttpServletRequest request){
        if(!analysing) {
            Analyse analyse = new Analyse();
            analyse.start();
            return new GetVo<>(0,"开始分析", 0 , null);
        }
        return new GetVo<>(0,"正在分析", 0 , null);
    }
    class Analyse extends Thread {
        @Override
        public void run() {
            analysing = true;
            //划分密接
            List<tbuser> userList1 = userInfoMapper.getAllByStatus(1);
            for (tbuser user : userList1) {
                List<Date1> DateList = comInfoMapper.getDateById(user.getId());
                System.out.println(DateList.size());
                for (Date1 date : DateList) {
                    List<tbInfo> ListOneDay = comInfoMapper.getInfoByDateAndId(user.getId(), date.getDate().toString() + "%");
                    Set<String> WifiList = new LinkedHashSet<>();
                    for(tbInfo item : ListOneDay) {
                        JSONArray Wifi = new JSONArray(item.getWifiInfo());
                        for(int i=0;i<Wifi.length();i++) {
                            WifiList.add((String) Wifi.getJSONObject(i).get("热点名称"));
                        }
                    }
                    System.out.println(WifiList);
                    for(String wifi : WifiList){
                        int count = userInfoMapper.updateStatus(date.getDate().toString(), date.getDate().toString() + "%", "%\"" + wifi + "\"%");
                        if(count>0)
                            datewifiMapper.insertDateWifi(date.getDate().toString(), wifi, 1);
                        System.out.println(date.getDate().toString()+wifi);
                    }
                    System.out.println(date.getDate());
                }
            }
            System.out.println("end 1");
            //划分次密接
            List<tbuser> userList2 = userInfoMapper.getAllByStatus(2);
            for (tbuser user : userList2) {
                List<Date1> DateList2 = comInfoMapper.getDateById2(user.getId());
                System.out.println(DateList2.size());
                for (Date1 date : DateList2) {
                    List<tbInfo> ListOneDay = comInfoMapper.getInfoByDateAndId(user.getId(), date.getDate().toString() + "%");
                    Set<String> WifiList = new LinkedHashSet<>();
                    for(tbInfo item : ListOneDay) {
                        JSONArray Wifi = new JSONArray(item.getWifiInfo());
                        for(int i=0;i<Wifi.length();i++) {
                            WifiList.add((String) Wifi.getJSONObject(i).get("热点名称"));
                        }
                    }
                    System.out.println(WifiList);
                    for(String wifi : WifiList){
                        int count = userInfoMapper.updateStatus2(date.getDate().toString(), date.getDate().toString() + "%", "%\"" + wifi + "\"%");
                        if(count>0)
                            datewifiMapper.insertDateWifi(date.getDate().toString(), wifi, 2);
                        System.out.println(date.getDate().toString()+wifi);
                    }
                    System.out.println(date.getDate());
                }
            }
            System.out.println("end 2");
            analysing = false;
        }
    }
}
