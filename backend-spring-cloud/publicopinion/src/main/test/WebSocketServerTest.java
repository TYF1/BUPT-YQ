import com.tetesense.publicopinion.PublicOpinionApplication;
import com.tetesense.publicopinion.weibo.crawler.WebSocketServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PublicOpinionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketServerTest {
    @Autowired
    WebSocketServer server;

    public static void main(String[] args) {
        String filePath = "E:\\user\\Desktop\\面向领域2\\weibo_crawler\\start.py";
        String [] para = new String[] {"E:\\user\\Desktop\\中软\\代码\\venv\\Scripts\\python", filePath, "人民网", "2022-8-30", "火箭"};
        System.out.println(Arrays.toString(para));

    }
    @Test
    void invokePy() {
        String filePath = "E:\\user\\Desktop\\面向领域2\\weibo_crawler\\start.py";
        String [] para = new String[] {"E:\\user\\Desktop\\中软\\代码\\venv\\Scripts\\python", filePath, "人民网", "2022-8-29", "火箭"};
        System.out.println(Arrays.toString(para));
        String result = server.invokePy(para);
        System.out.println("[DEBUG]:"+result);

    }
}
