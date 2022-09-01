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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest(classes= Trackmanage10003.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserLoginMapperTest {
    @Resource
    UserLoginMapper userLoginMapper;

    @Test
    public void getUserByNameAndLogin() {
        List<UserLogin> listSearch = userLoginMapper.getUserByName("admin");
        assertThat(listSearch.size(), is(1));

        List<UserLogin> listLogin = userLoginMapper.getUserLogin(listSearch.get(0).getUserName(),listSearch.get(0).getPassword());
        assertThat(listLogin.size(), is(1));
    }
}
