import com.tetesense.publicopinion.PublicOpinionApplication;
import com.tetesense.publicopinion.weibo.WeiboRepository;
import com.tetesense.publicopinion.weibo.post.PostType;
import com.tetesense.publicopinion.weibo.post.WeiboPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = PublicOpinionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeiboPostServiceTest {

    @Autowired
    WeiboPostService weiboService;
    @Autowired
    WeiboRepository repository;

    @org.junit.jupiter.api.Test
    void getPostsOfType() {
        for (var type : PostType.values()) {
            var ans = weiboService.getPostsOfType(1, 2, "id", type);
            assertNotNull(ans);
            for (var e : ans) {
                if (PostType.keyword[type.getType()] == null) break;
                assertTrue(e.getText().contains(PostType.keyword[type.getType()]));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void getPostsByDate() {
        for(int data : new int[]{0, 10, 100, 1000}) {
            LocalDate date = LocalDate.now().minusDays(data);

            // LocalDate转化为Date
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zdt = date.atStartOfDay(zoneId);
            Date date_ = Date.from(zdt.toInstant());

            var ans = weiboService.getPostsByDate(1,10,"id", data);
            for (var e : ans.toList()) {
                assertTrue(e.getCreatedAt().after(date_));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void getPostsByKeyword() {
        for (String keyword : new String[]{"", "北京", "南京", "重庆"}) {
            var ans = weiboService.getPostsByKeyword(1, 2, "id", keyword);
            assertNotNull(ans);
            for (var e : ans) {
                assertTrue(e.getText().contains(keyword));
            }
        }
    }

}
