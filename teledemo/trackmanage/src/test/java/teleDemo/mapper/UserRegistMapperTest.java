package teleDemo.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import teleDemo.Trackmanage10003;
import teleDemo.entities.UserLogin;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= Trackmanage10003.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRegistMapperTest {

    @Resource
    UserRegistMapper userRegistMapper;

    @Test
    public void findUserByNameAndRegist() {
        String username="test10003";
        String password ="123";

        userRegistMapper.regist(username,password);
        UserLogin user = userRegistMapper.findUserByName(username);
        assertThat(user.getUserName(), is(username));
        assertThat(user.getPassword(), is(password));

        userRegistMapper.delete(username,password);
        UserLogin userDeleted = userRegistMapper.findUserByName(username);

        assertThat(userDeleted, nullValue());

    }

}