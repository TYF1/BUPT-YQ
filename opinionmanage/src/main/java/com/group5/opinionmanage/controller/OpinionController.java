package com.group5.opinionmanage.controller;

import com.group5.opinionmanage.entity.Opinions;
import com.group5.opinionmanage.formentity.GetVo;
import com.group5.opinionmanage.service.OpinionsService;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author 10569
 * @version 1.0
 * @description 响应前端请求
 * @Date 2022/8/24 14:17
 */

@RestController
public class OpinionController {

    @Autowired
    OpinionsService opinionsServiceImpl;

    @GetMapping("/formGet")
    public GetVo getForm(HttpServletRequest request) {
        int limit = Integer.parseInt(request.getParameter("limit"));
        int page = Integer.parseInt(request.getParameter("page"));
        Sort sort = Sort.by(Sort.Order.desc("heat"));
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Opinions> info = opinionsServiceImpl.findAll(pageable);
        Long count = opinionsServiceImpl.count();
        return new GetVo(0, "success", count, info);
    }


    @GetMapping("/ConditionSelect")
    public GetVo findCondition(HttpServletRequest request) {
        String conditionSelect = String.valueOf(request.getParameter("conditionSelect"));
        String conditionInput = String.valueOf(request.getParameter("conditionInput"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        int page = Integer.parseInt(request.getParameter("page"));
        Sort sort = Sort.by(Sort.Order.desc("heat"));
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Opinions> info = null;
        Long count = opinionsServiceImpl.count();
        switch (conditionSelect) {
            case "cxt":
                info = opinionsServiceImpl.findByContext(pageable, conditionInput);
                break;
            case "kw":
                info = opinionsServiceImpl.findByKeyWord(pageable, conditionInput);
                break;
            case "ft":
                if ("积极".equals(conditionInput)) {
                    info = opinionsServiceImpl.findByFeature(pageable, 1);
                } else if ("消极".equals(conditionInput)) {
                    info = opinionsServiceImpl.findByFeature(pageable, 0);
                } else if ("中性".equals(conditionInput)) {
                    info = opinionsServiceImpl.findByFeature(pageable, 2);
                } else {
                    return null;
                }

                break;
            default:
        }
        return new GetVo(0, "success", count, info);
    }

    @GetMapping("/spider")
    public String getNewData(String spiderContent,String spiderCount) throws IOException, InterruptedException {
        String python = "D:\\Anaconda\\anaconda3\\envs\\learning\\python.exe";
        String script = "C:\\Users\\10569\\Desktop\\teledemo\\teledemo\\spider\\run.py";
//        String python="/root/anaconda3/envs/learning/bin/python";
//        String script="/usr/scripts/spider/run.py";
        System.out.println(spiderContent);
        String[] argument = new String[]{python, script, spiderCount, spiderContent};
        Process process = Runtime.getRuntime().exec(argument);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String action = in.readLine();
        int stat = process.waitFor();

        process.destroy();
        in.close();
        System.out.println(stat);
        if (stat == 0) {
            return "finish";
        } else {
            return "false";
        }

    }

}


