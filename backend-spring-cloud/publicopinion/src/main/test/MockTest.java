import com.tetesense.publicopinion.PublicOpinionApplication;
import com.tetesense.publicopinion.weibo.WeiboController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = PublicOpinionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockTest {

    @Autowired
    private WeiboController weiboController;
    @Test
    public void testTest() {
        assertThat(weiboController).isNotNull();
    }

}
