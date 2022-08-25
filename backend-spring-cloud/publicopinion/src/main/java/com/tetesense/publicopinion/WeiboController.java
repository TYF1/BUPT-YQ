package com.tetesense.publicopinion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/publicopinion")
@CrossOrigin
public record WeiboController(WeiboService weiboService) {
    public static final Logger log = LoggerFactory.getLogger(WeiboController.class);
    public static final List<Field> fields = Arrays.stream(WeiboBody.class.getDeclaredFields()).toList();

    public static boolean validatePropertyName(String property) {
        for (Field f : fields) {
            if (f.getName().equals(property)) return true;
        }
        return false;
    }

    @GetMapping("/type")
    public Page<WeiboBody> getPostsOfType(@RequestParam Integer page,
        @RequestParam Integer limit,
        @RequestParam Integer type,
        @RequestParam(required = false) String property) {

        log.info("获取特定类型微博: page: {}, limit: {}, type: {}, property: {}", page, limit, type, property);
        PostType postType = PostType.values()[type];
        if (property == null || !validatePropertyName(property)) {
            property = "likes"; // 默认排序
            log.error("property参数有误，按照likes排序");
        }
        Page<WeiboBody> weibos = weiboService.getPostsOfType(page, limit, property, postType);
        return weibos;
    }

    @GetMapping("/time")
    public Page<WeiboBody> getPostsByDate(@RequestParam Integer page,
                               @RequestParam Integer limit,
                               @RequestParam Integer since_day,
                               @RequestParam(required = false) String property) {
        log.info("获取特定时间微博: page: {}, limit: {}, since_day: {}, property: {}", page, limit, since_day, property);
        if (property == null || !validatePropertyName(property)) {
            property = "id"; //  排序的依据
        }
        return weiboService.getPostsByDate(page, limit, property, since_day);
    }

    @GetMapping("/keyword")
    public Page<WeiboBody> getPostsByKeyword(@RequestParam Integer page,
                               @RequestParam Integer limit,
                               @RequestParam String keyword,
                               @RequestParam(required = false) String property) {
        log.info("获取特定关键字微博: page: {}, limit: {}, keyword: {}, property: {}", page, limit, keyword, property);
        if (property == null || !validatePropertyName(property)) {
            property = "id"; //  排序的依据
        }
        return weiboService.getPostsByKeyword(page, limit, property, keyword);
    }

    @GetMapping("/user")
    public Page<WeiboBody> getPostsByUser(@RequestParam Integer page,
                                  @RequestParam Integer limit,
                                  @RequestParam String user,
                                  @RequestParam(required = false) String property) {
        log.info("获取特定用户微博: page: {}, limit: {}, user: {}", page, limit, user);
        if (property == null) {
            property = "id"; //  排序的依据
        }
        return weiboService.getPostsByKeyword(page, limit, property, user);
    }

}
