/**
 * @Project Name:MSHD
 * @File Name: hsInfoController.java
 * @Description: 热搜信息页面Controller
 * @HISTORY：
 *    Created   2022.8.29  魏瑾
 *    Modified  2022.8.31  魏瑾
 */

package teleDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teleDemo.entities.GetVo;
import teleDemo.entities.tbWeiboComments;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class weiboInfoController {
    @Resource
    teleDemo.mapper.weiboInfoMapper weiboInfoMapper;

     @GetMapping("/v1/weiboInfo")
    public GetVo getWeiboInfo(HttpServletRequest request) {
         String keyWord = request.getParameter("keyWord");
         List<tbWeiboComments> weiboCommentsList = weiboInfoMapper.getNewest10Weibo(keyWord);
         GetVo<tbWeiboComments> getVo = new GetVo<>(0, "获取数据成功！", 10, weiboCommentsList);
         return getVo;
     }

    @RequestMapping("/v1/weiboSearch")
    public GetVo getWeiboSearch(HttpServletRequest request) {
         String keyWord = request.getParameter("keyWord");

         //todo: 添加脚本调用，执行更新操作
        Process proc;
        List<String> lineList = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec("python teledemo/publicOpinionManage/src/main/resources/weiboSpider/startspider.py " + keyWord);
//            Process p = Runtime.getRuntime().exec("python /home/fanxyd/eqe/jarPackage/teledemo/resources/weiboSpider/startspider.py " + keyWord);
            final InputStream is1 = p.getInputStream();
            Thread thread = new Thread(() -> {
                BufferedReader br = new BufferedReader(new InputStreamReader(is1));
                try{
                    while(br.readLine() != null) ;
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();

            InputStream is2 = p.getErrorStream();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
            while(br2.readLine() != null){}
            p.waitFor();
//            int i = p.exitValue();
//            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<tbWeiboComments> weiboCommentsList = weiboInfoMapper.getNewest10Weibo(keyWord);
        GetVo<tbWeiboComments> getVo = new GetVo<>(0, "获取数据成功！", 10, weiboCommentsList);
        return getVo;
    }
}
