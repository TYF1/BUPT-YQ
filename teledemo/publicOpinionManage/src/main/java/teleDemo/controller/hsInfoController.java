/**
 * @Project Name:MSHD
 * @File Name: hsInfoController.java
 * @Description: 热搜信息页面Controller
 * @HISTORY：
 *    Created   2022.8.24  魏瑾
 *    Modified  2022.8.29  魏瑾
 *    Modified  2022.8.31  魏瑾
 */

package teleDemo.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import teleDemo.entities.GetVo;
import teleDemo.entities.tbHotSearch;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class hsInfoController {
    @Resource
    teleDemo.mapper.hsInfoMapper hsInfoMapper;

    @GetMapping("/v1/hsInfo")
    public GetVo getHsInfo(HttpServletRequest request) {
        List<tbHotSearch> tbHotSearchList = hsInfoMapper.getTop10HotSearch();
        GetVo<tbHotSearch> getVo = new GetVo<>(0, "获取数据成功！",10, tbHotSearchList);
        return getVo;
    }

    @RequestMapping("/v1/hsUpdate")
    public String updateHsDB(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Process proc;
        List<String> lineList = new ArrayList<>();
        List<String> errorList = new ArrayList<>();

        try {
            proc = Runtime.getRuntime().exec("python teledemo/publicOpinionManage/src/main/resources/weiboHotSearch/hsTop10.py");
//            proc = Runtime.getRuntime().exec("python /home/fanxyd/eqe/jarPackage/teledemo/resources/weiboHotSearch/hsTop10.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
//                System.out.println(line);
                lineList.add(line);
            }

            String err = null;
            while ((err = error.readLine()) != null) {
                errorList.add(err);
            }
            if (errorList.size() != 0) {
                return "failed";
            }

            in.close();
            error.close();
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(lineList.size());
    }
}
