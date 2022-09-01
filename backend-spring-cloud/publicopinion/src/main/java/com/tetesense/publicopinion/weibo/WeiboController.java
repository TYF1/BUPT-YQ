package com.tetesense.publicopinion.weibo;

import com.tetesense.publicopinion.weibo.crawler.SpringUtil;
import com.tetesense.publicopinion.weibo.post.PostType;
import com.tetesense.publicopinion.weibo.post.WeiboPostRequest;
import com.tetesense.publicopinion.weibo.post.WeiboPostService;
import com.tetesense.publicopinion.weibo.stat.WeiboStatResponseBody;
import com.tetesense.publicopinion.weibo.stat.WeiboStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

@RestController
@RequestMapping("/publicopinion")
@CrossOrigin
public record WeiboController(WeiboPostService weiboPostService,
                              WeiboStatisticsService weiboStatisticsService) {
    public static final Logger log = LoggerFactory.getLogger(WeiboController.class);
    public static final Field[] fields = WeiboBody.class.getDeclaredFields();
    private static WeiboPostRequest lastPostRequest = null;

    public static boolean isValidPropertyName(String property) {
        if (property == null) return true; // 允许无property参数
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
        lastPostRequest = new WeiboPostRequest("type", type.toString());

        PostType postType = PostType.values()[type];
        if (!isValidPropertyName(property)) {
            property = "likes"; // 默认排序
            log.warn("property参数有误，按照likes排序");
        }
        return weiboPostService.getPostsOfType(page, limit, property, postType);
    }

    @GetMapping("/time")
    public Page<WeiboBody> getPostsByDate(@RequestParam Integer page,
                               @RequestParam Integer limit,
                               @RequestParam Integer since_day,
                               @RequestParam(required = false) String property) {
        log.info("获取特定时间微博: page: {}, limit: {}, since_day: {}, property: {}", page, limit, since_day, property);
        lastPostRequest = new WeiboPostRequest("time", since_day.toString());

        if (!isValidPropertyName(property)) {
            property = "createdAt"; //  排序的依据
            log.warn("property参数错误，按照createdAt排序");
        }
        return weiboPostService.getPostsByDate(page, limit, property, since_day);
    }

    @GetMapping("/keyword")
    public Page<WeiboBody> getPostsByKeyword(@RequestParam Integer page,
                               @RequestParam Integer limit,
                               @RequestParam String keyword,
                               @RequestParam(required = false) String property) {
        log.info("获取特定关键字微博: page: {}, limit: {}, keyword: {}, property: {}", page, limit, keyword, property);
        lastPostRequest = new WeiboPostRequest("keyword", keyword);

        if (!isValidPropertyName(property)) {
            property = "likes"; // 默认排序
            log.warn("property参数有误，按照likes排序");
        }
        return weiboPostService.getPostsByKeyword(page, limit, property, keyword);
    }

    @GetMapping("/user")
    public Page<WeiboBody> getPostsByUser(@RequestParam Integer page,
                                  @RequestParam Integer limit,
                                  @RequestParam String user,
                                  @RequestParam(required = false) String property) {
        log.info("获取特定用户微博: page: {}, limit: {}, user: {}", page, limit, user);
        lastPostRequest = new WeiboPostRequest("user", user);

        if (!isValidPropertyName(property)) {
            property = "likes"; // 默认排序
            log.warn("property参数有误，按照likes排序");
        }
        return weiboPostService.getPostsByUser(page, limit, property, user);
    }

    @GetMapping("/stats")
    public WeiboStatResponseBody getStats() {
        log.info("获取统计数据");
        return weiboStatisticsService.getStats(lastPostRequest);
    }
}
