package com.group5.opinionmanage.formentity;

import com.group5.opinionmanage.entity.Opinions;
import org.springframework.data.domain.Page;

/**
 * @author 10569
 * @version 1.0
 * @description 返回给前端的表单
 * @Date 2022/8/24 15:20
 */

public class GetVo {
    private Integer code;
    private String msg;
    private Long count;
    private Page<Opinions> data;

    public GetVo(Integer code, String msg, Long count, Page<Opinions> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Page<Opinions> getData() {
        return data;
    }

    public void setData(Page<Opinions> data) {
        this.data = data;
    }
}


