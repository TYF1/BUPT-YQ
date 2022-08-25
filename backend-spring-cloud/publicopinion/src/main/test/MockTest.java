import com.tetesense.publicopinion.PublicOpinionApplication;
import com.tetesense.publicopinion.WeiboController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = PublicOpinionApplication.class)
public class MockTest {

    @Autowired
    private WeiboController weiboController;
    @Test
    public void testTest() {
        assertThat(weiboController).isNotNull();
    }

}
