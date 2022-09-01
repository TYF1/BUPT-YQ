import com.tetesense.publicopinion.PublicOpinionApplication;
import com.tetesense.publicopinion.weibo.WeiboController;
import com.tetesense.publicopinion.weibo.post.PostType;
import com.tetesense.publicopinion.weibo.post.WeiboPostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PublicOpinionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeiboControllerTest {

    final String[] properties = new String[]{"likes"};

    @Autowired
    WeiboController controller;
    @Autowired
    WeiboPostService service;

    @org.junit.jupiter.api.Test
    void validatePropertyName1() {
        assertTrue(WeiboController.isValidPropertyName("likes"));
    }
    @org.junit.jupiter.api.Test
    void validatePropertyName2() {
        assertFalse(WeiboController.isValidPropertyName("like"));
    }


    @Test
    void getPostsOfType() {
        for (var type : PostType.values())
            for (var pro : properties) {
                var con = controller.getPostsOfType(1, 3, type.getType(), pro);
                var ser = service.getPostsOfType(1, 3, pro, type);
                assertEquals(con, ser);
            }
    }

    @Test
    void getPostsByDate() {
        for (var data : new int[]{0, 10, 100, 1000})
            for (var pro : properties) {
                var con = controller.getPostsByDate(1, 3, data, pro);
                var ser = service.getPostsByDate(1, 3, pro, data);
                assertEquals(con, ser);
            }
    }

    @Test
    void getPostsByKeyword() {
        for (var keyword : new String[]{"北京", "重庆"})
            for (var pro : properties) {
                var con = controller.getPostsByKeyword(1, 3, keyword, pro);
                var ser = service.getPostsByKeyword(1, 3, pro, keyword);
                assertEquals(con, ser);
            }
    }

    @Test
    void getPostsByUser() {
        for (var user : new String[]{"北京日报", "重庆发布", "人民网"})
            for (var pro : properties) {
                var con = controller.getPostsByUser(1, 3, user, pro);
                var ser = service.getPostsByUser(1, 3, pro, user);
                con.toList().forEach(System.out::println);
                ser.toList().forEach(System.out::println);
                System.out.println(ser.toList().size());
                assertThat(con).isEqualTo(ser);
            }
    }
}
