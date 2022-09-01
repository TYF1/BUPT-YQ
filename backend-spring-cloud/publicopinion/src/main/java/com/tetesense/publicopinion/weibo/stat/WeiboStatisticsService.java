package com.tetesense.publicopinion.weibo.stat;

import com.tetesense.publicopinion.weibo.WeiboBody;
import com.tetesense.publicopinion.weibo.WeiboController;
import com.tetesense.publicopinion.weibo.WeiboRepository;
import com.tetesense.publicopinion.weibo.post.PostType;
import com.tetesense.publicopinion.weibo.post.WeiboPostRequest;
import com.tetesense.publicopinion.weibo.post.WeiboPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * 获取微博相关统计数据的Service
 */
@Service
public record WeiboStatisticsService(WeiboPostService weiboPostService, WeiboRepository weiboRepository) {
    public static final int TOTAL = 50;
    public static final Logger log = LoggerFactory.getLogger(WeiboController.class);
    private static final Map<WeiboBody, SentimentType> cache = new WeakHashMap<>();

    public WeiboStatResponseBody getStats(WeiboPostRequest lastPostRequest) {
        // get all the posts according to the last request
        List<WeiboBody> weibos = null;
        if (lastPostRequest == null) { // 之前没有访问过
            weibos = weiboPostService.getPostsOfType(0, TOTAL, null, PostType.ALL).stream().toList();
        } else {
            String keyword = lastPostRequest.keyword();
            switch (lastPostRequest.type()) {
                case "type" -> {
                    PostType postType = PostType.values()[Integer.parseInt(keyword)];
                    weibos = weiboPostService.getPostsOfType(0, TOTAL, null, postType).stream().toList();
                }
                case "time" -> weibos = weiboPostService.getPostsByDate(0, TOTAL, null, Integer.parseInt(keyword)).stream().toList();
                case "user" -> weibos = weiboPostService.getPostsByUser(0, TOTAL, null, keyword).stream().toList();
                case "keyword" -> weibos = weiboPostService.getPostsByKeyword(0, TOTAL, null, keyword).stream().toList();
            }
        }

        // get statistics through iFlyTech's API
        int negative = 0;
        int positive = 0;
        int neutral = 0;

        assert weibos != null;
        for (final var weibo : weibos) {
            SentimentType sentimentType;
            // try to find in cache
            if (cache.containsKey(weibo)) {
                log.info("Cache hit!");
                sentimentType = cache.get(weibo);
            } else {
                Optional<WeiboBody> wb = weiboRepository.findById(weibo.getId());
                if (wb.isEmpty()) {
                    log.info("数据库中不存在微博:" + weibo);
                    break;
                } else {
                    if (wb.get().getSentiment_type() == null) {
                        log.info("Cache miss! Running Sentiment analysis...");
                        sentimentType = NlpApiUtil.getSentiment(weibo.getText());
                        if (sentimentType == null) {
                            log.error("无法获取情感分析结果, weibo: " + weibo);
                            continue;
                        }
                        log.info("获取分析结果：" + sentimentType + "存入数据库");
                        weibo.setSentiment_type(sentimentType.ordinal());
                        weiboRepository.save(weibo);
                        cache.put(weibo, sentimentType);
                    } else {
                        log.info("数据库中存在，调取数据");
                        sentimentType = SentimentType.values()[wb.get().getSentiment_type()];
                    }
                }
            }
            switch (sentimentType) {
                case NEGATIVE -> negative++;
                case NEUTRAL -> neutral++;
                case POSITIVE -> positive++;
            }
        }

        // return response body
        return WeiboStatResponseBody.builder()
                .negative(negative)
                .positive(positive)
                .neutral(neutral)
                .total(TOTAL)
                .build();
    }
}
