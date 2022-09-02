package teleDemo.controller;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import teleDemo.entities.*;
import teleDemo.entities.Record;
import teleDemo.mapper.*;
import teleDemo.util.JsonFormatTool;
import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
public class teleinfoController {

    @Resource
    comInfoMapper comInfoMapper;
    @Resource
    userInfoMapper userInfoMapper;
    @Resource
    areainfoMapper areainfoMapper;
    @Resource
    updateRecordMapper updateRecordMapper;

    @GetMapping("/v1/comInfo")
    public GetVo gettbInfo(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int size = comInfoMapper.getAlltbINfo().size();
        List<tbInfo> tbInfos = comInfoMapper.gettbINfoByPage((page-1)*limit,limit);
        GetVo<tbInfo> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);
        return  getVo;
    }

    @GetMapping("/v1/comInfo/RiskArea")
    public void updateArea(){


    }
    @GetMapping("/v1/userInfo")
    public GetVo gettbUSer(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int size = userInfoMapper.getAlltbUser().size();
        List<tbuser> tbInfos = userInfoMapper.gettbUserByPage((page-1)*limit,limit);
        GetVo<tbuser> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);
        try {
            File file = new File("http://120.48.172.18:8000/package.json");
            if(!file.exists()){
                file.createNewFile();
            }else {
                file.delete();
                file.createNewFile();
            }
            List<tbarea> area = areainfoMapper.getArea();
            List<tbareaVo> tbareaVos =new ArrayList<>();
            for (tbarea tbarea : area) {
                tbareaVo tbareaVo=new tbareaVo();
                tbareaVo.setLat(tbarea.getLat());
                tbareaVo.setLon(tbarea.getLon());
                int num = tbarea.getNum();
                if (num>=50){
                    tbareaVo.setStatus(1);
                }else{
                    tbareaVo.setStatus(2);
                }
                tbareaVos.add(tbareaVo);
            }
            GetVo<tbareaVo> tbareaGetVo=new GetVo<>();
            tbareaGetVo.setCode(0);
            tbareaGetVo.setData(tbareaVos);
            JSONObject jsonObject=new JSONObject(tbareaGetVo);
            String jsonString = JsonFormatTool.formatJson(jsonObject.toString());
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(jsonString);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return  getVo;
    }
    @GetMapping("/v1/record_Info")
    public GetVo gettbRecords(HttpServletRequest request){
        //generateData();
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int size = updateRecordMapper.getAllRecord().size();
        List<teleDemo.entities.Record> Records = updateRecordMapper.gettbRecordByPage((page-1)*limit,limit);
        GetVo<Record> getVo = new GetVo<>(0,"获取数据成功！",size,Records);
        return  getVo;
    }

    private void generateData() {
        for(int i=0;i<1000;i++)		 //控制产生的随机数的个数
        {
            Random random=new Random();	 //使用Random函数产生随机数；
            comInfoMapper.insertSomeData(random.nextInt(1500),new Date(2022,8,31), 39.5+random.nextDouble()%0.1, 116.2+random.nextDouble()%0.2 ,random.nextInt(200));
        }
    }

}
