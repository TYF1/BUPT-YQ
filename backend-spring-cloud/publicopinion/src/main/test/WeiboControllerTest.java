import com.tetesense.publicopinion.WeiboController;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class WeiboControllerTest {

    @org.junit.jupiter.api.Test
    void validatePropertyName1() {
        Assertions.assertEquals(true, WeiboController.validatePropertyName("likes"));
    }
    @org.junit.jupiter.api.Test
    void validatePropertyName2() {
        assertEquals(false,WeiboController.validatePropertyName("like"));
    }
}
