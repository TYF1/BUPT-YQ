package com.group5.opinionmanage.service;

import com.group5.opinionmanage.entity.Opinions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 10569
 * @version 1.0
 * @description     提供的服务接口
 * @Date 2022/8/24 15:17
 */

public interface OpinionsService {
    
    /**
     * @author qby
     * @Description 根据id查找
     * @Date 13:17 2022/8/26
     * @Param [oid]
     * @return com.group5.opinionmanage.entity.Opinions 
    */
    Opinions findByOid(Integer oid);
    /**
     * @author qby
     * @Description 按照文本内容模糊查找舆情信息，并且进行分页
     * @Date 13:17 2022/8/26
     * @Param [pageable, context]
     * @return org.springframework.data.domain.Page<com.group5.opinionmanage.entity.Opinions> 
    */
    Page<Opinions> findByContext(Pageable pageable,String context);
    /**
     * @author zdh
     * @Description 按分页的方式查找所有数据
     * @Date 13:20 2022/8/26
     * @Param [pageable]
     * @return org.springframework.data.domain.Page<com.group5.opinionmanage.entity.Opinions>
    */
    Page<Opinions> findAll(Pageable pageable);
    /**
     * @author zdh
     * @Description repository的count方法
     * @Date 13:20 2022/8/26
     * @Param []
     * @return java.lang.Long 
    */
    Long count();

    /**
     * @author qby
     * @Description 按照关键字进行模糊查询并返回分页数据
     * @Date 13:21 2022/8/26
     * @Param [pageable, keyword]
     * @return org.springframework.data.domain.Page<com.group5.opinionmanage.entity.Opinions>
    */
    Page<Opinions> findByKeyWord(Pageable pageable,String keyword);

    Page<Opinions> findByFeature(Pageable pageable, Integer feature);
}


