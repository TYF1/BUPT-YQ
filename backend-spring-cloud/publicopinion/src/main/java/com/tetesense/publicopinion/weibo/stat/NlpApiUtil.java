package com.tetesense.publicopinion.weibo.stat;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.tetesense.publicopinion.weibo.WeiboController;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NlpApiUtil {
    // webapi接口地址
    private static final String URL = "https://ltpapi.xfyun.cn/v2/sa";
    // 应用ID
    private static final String APPID = "0104f60b";
    // 接口密钥
    private static final String API_KEY = "13fd79e3c04b64bf205f7248eb010ab0";
    // 长度限制
    private static final int MAX_LENGTH = 500;

    private static final String TYPE = "dependent";

    private static final Logger log = LoggerFactory.getLogger(WeiboController.class);

    public static SentimentType getSentiment(String text) {
        String result = getSentimentAnalysisResult(text);

        // parse json
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject.getString("desc").equals("success")) {
            Integer sentiment = jsonObject.getJSONObject("data").getInteger("sentiment");
            if (sentiment == null) {
                log.error("解析API返回结果有问题，result: " + result);
                return null;
            }
            return switch (sentiment) {
                case 0 -> SentimentType.NEUTRAL;
                case 1 -> SentimentType.POSITIVE;
                case -1 -> SentimentType.NEGATIVE;
                default -> null;
            };
        } else {
            log.error("API 返回不合法：" + result);
            return null;
        }
    }

    /**
     * 调用科大讯飞API获取情感分析结果
     */
    public static String getSentimentAnalysisResult(String text) {
        if (text.getBytes().length > MAX_LENGTH) { // 字节数限制检查
            text = text.substring(0,125);
        }
        Map<String, String> header = buildHttpHeader();
        return HttpUtil.sendPost(URL, header, "text=" + URLEncoder.encode(text, StandardCharsets.UTF_8));
    }

    /**
     * 将字符串截断到满足API的最大字节限制
     */
    public static String truncateWhenUTF8(String s, int maxBytes) {
        int b = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // ranges from http://en.wikipedia.org/wiki/UTF-8
            int skip = 0;
            int more;
            if (c <= 0x007f) {
                more = 1;
            }
            else if (c <= 0x07FF) {
                more = 2;
            } else if (c <= 0xd7ff) {
                more = 3;
            } else if (c <= 0xDFFF) {
                // surrogate area, consume next char as well
                more = 4;
                skip = 1;
            } else {
                more = 3;
            }

            if (b + more > maxBytes) {
                return s.substring(0, i);
            }
            b += more;
            i += skip;
        }
        return s;
    }

    /**
     * 组装http请求头
     */
    private static Map<String, String> buildHttpHeader() {
        String curTime = System.currentTimeMillis() / 1000L + "";
        String param = "{\"type\":\"" + TYPE +"\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes(StandardCharsets.UTF_8)));
        String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", APPID);
        return header;
    }
}
