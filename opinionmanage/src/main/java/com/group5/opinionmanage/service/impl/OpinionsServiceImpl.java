package com.group5.opinionmanage.service.impl;

import com.group5.opinionmanage.dao.OpinionsRepository;
import com.group5.opinionmanage.entity.Opinions;
import com.group5.opinionmanage.service.OpinionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 10569
 * @version 1.0
 * @description     提供的服务的实现
 * @Date 2022/8/24 15:18
 */
@Service("OpinionsService")
public class OpinionsServiceImpl implements OpinionsService {
    @Autowired
    private OpinionsRepository opinionsRepository;

    @Override
    public Opinions findByOid(Integer oid) {
        return opinionsRepository.findByOid(oid);
    }

    @Override
    public Page<Opinions> findByContext(Pageable pageable,String context) {
        return opinionsRepository.findByContextLike(pageable,context);
    }

    @Override
    public Page<Opinions> findAll(Pageable pageable) {
        return opinionsRepository.findAllByOrderByHeatDesc(pageable);
    }

    @Override
    public Long count() {
        return opinionsRepository.count();
    }


    @Override
    public Page<Opinions> findByKeyWord(Pageable pageable,String keyword) {
        return opinionsRepository.findByKeywordLike(pageable,keyword);
    }

    @Override
    public Page<Opinions> findByFeature(Pageable pageable, Integer feature) {
        return opinionsRepository.findByFeature(pageable,feature);
    }
}


