package com.tetesense.publicopinion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public record WeiboService(WeiboRepository weiboRepository) {
    public Page<WeiboBody> getPostsOfType(Integer page, Integer limit, String property, PostType type) {
        Sort sort = Sort.by(Sort.Direction.DESC, property);
        Pageable paging = PageRequest.of(page, limit, sort);
        return weiboRepository.findAll(paging); // TODO
    }

    public Page<WeiboBody> getPostsByDate(Integer page, Integer limit, String property, Integer since_day) {
        Sort sort = Sort.by(Sort.Direction.DESC, property);
        Pageable paging = PageRequest.of(page, limit, sort);
        LocalDate date = LocalDate.now().minusDays(since_day);

        // LocalDate转化为Date
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atStartOfDay(zoneId);

        Date date_ = Date.from(zdt.toInstant());

        return weiboRepository.findAllByCreatedAtAfter(paging, date_); // TODO
    }

    public Page<WeiboBody> getPostsByKeyword(Integer page, Integer limit, String property, String keyword) {
        Sort sort = Sort.by(Sort.Direction.DESC, property);
        Pageable paging = PageRequest.of(page, limit, sort);
        return weiboRepository.findAllByTextContaining(paging, keyword); // TODO
    }

    public Page<WeiboBody> getPostsByUser(Integer page, Integer limit, String property, String userName) {
        Sort sort = Sort.by(Sort.Direction.DESC, property);
        Pageable paging = PageRequest.of(page, limit, sort);
        return weiboRepository.findAllByUsername(paging, userName); // TODO
    }
}
