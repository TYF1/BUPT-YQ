package com.tetesense.publicopinion.weibo.post;

import com.tetesense.publicopinion.weibo.WeiboBody;
import com.tetesense.publicopinion.weibo.WeiboRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public record WeiboPostService(WeiboRepository weiboRepository) {
    public Page<WeiboBody> getPostsOfType(Integer page, Integer limit, String property, PostType type) {
        Pageable paging;
        if (property == null) {
            paging = PageRequest.of(page, limit);
        } else {
            Sort sort = Sort.by(Sort.Direction.DESC, property);
            paging = PageRequest.of(page, limit, sort);
        }
        return null != PostType.keyword[type.getType()]?
                weiboRepository.findAllByTextContaining(paging, PostType.keyword[type.getType()]):
                weiboRepository.findAll(paging);
//        return switch (type) {
//            case LOCKDOWNS -> getPostsByKeyword(page, limit, property, "停");
//            case REOPENING -> getPostsByKeyword(page, limit, property, "复");
//            case RUMORS -> getPostsByKeyword(page, limit, property, "谣");
//            case ALL -> getPostsByKeyword(page, limit, property, "");
//        };
    }

    public Page<WeiboBody> getPostsByDate(Integer page, Integer limit, String property, Integer since_day) {
        Pageable paging;
        if (property == null) {
            paging = PageRequest.of(page, limit);
        } else {
            Sort sort = Sort.by(Sort.Direction.DESC, property);
            paging = PageRequest.of(page, limit, sort);
        }
        LocalDate date = LocalDate.now().minusDays(since_day);

        // LocalDate转化为Date
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atStartOfDay(zoneId);

        Date date_ = Date.from(zdt.toInstant());

        return weiboRepository.findAllByCreatedAtAfter(paging, date_);
    }

    public Page<WeiboBody> getPostsByKeyword(Integer page, Integer limit, String property, String keyword) {
        Pageable paging;
        if (property == null) {
            paging = PageRequest.of(page, limit);
        } else {
            Sort sort = Sort.by(Sort.Direction.DESC, property);
            paging = PageRequest.of(page, limit, sort);
        }
        return weiboRepository.findAllByTextContaining(paging, keyword);
    }

    public Page<WeiboBody> getPostsByUser(Integer page, Integer limit, String property, String userName) {
        Pageable paging;
        if (property == null) {
            paging = PageRequest.of(page, limit);
        } else {
            Sort sort = Sort.by(Sort.Direction.DESC, property);
            paging = PageRequest.of(page, limit, sort);
        }
        return weiboRepository.findAllByUsername(paging, userName);
    }
}
