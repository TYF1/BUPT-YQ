package teleDemo.mapper;

import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import teleDemo.Trackmanage10003;
import teleDemo.entities.TbUser;
import teleDemo.entities.UserLogin;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest(classes = Trackmanage10003.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserInfoMapperTest {
    @Resource
    UserInfoMapper userInfoMapper;

    @Test
    public void getAlltbUser() {
        List<TbUser> listInfo = userInfoMapper.getAlltbUser();
        assertThat(listInfo.size(), notNullValue());
    }

    @Test
    public void gettbUserByPage() {
        int pageNum = 2;
        int limitNum = 500;

        List<TbUser> listInfo = userInfoMapper.gettbUserByPage(pageNum, limitNum);
        assertThat(listInfo.size(), notNullValue());
    }

    @Test
    public void gettbUserById() {
        int userId = 3;
        TbUser getUser = userInfoMapper.gettbUserById(userId);
        assertThat(getUser.getPhoneNumber(), is("08088507132"));
    }

    @Test
    public void deleteUserById() {
        int userId = 5;
        userInfoMapper.deleteUserById(userId);
        TbUser deleteUser = userInfoMapper.gettbUserById(userId);
        assertThat(deleteUser, nullValue());

    }
}
