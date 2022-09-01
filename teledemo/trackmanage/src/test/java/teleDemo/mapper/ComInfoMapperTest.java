package teleDemo.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import teleDemo.Trackmanage10003;
import teleDemo.entities.TbInfo;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Trackmanage10003.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ComInfoMapperTest {

    @Resource
    ComInfoMapper comInfoMapper;

    @Test
    public void gettbInfoById() {
        int userId = 2;
        List<TbInfo> listInfo = comInfoMapper.gettbInfoById(userId);
        assertThat(listInfo.size(),notNullValue());
    }
}