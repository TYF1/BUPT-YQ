package teleDemo.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import teleDemo.Trackmanage10003;
import teleDemo.entities.UserLogin;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(classes= Trackmanage10003.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CreatJwtTest {
    @Test
    public void getokenAndUser() {
        UserLogin user = new UserLogin(1,"name","password",1);
        String token = CreatJwt.getoken(user);
        UserLogin userDecode =  CreatJwt.getUser(token);

        assertThat(userDecode.getUserName(), is(user.getUserName()));
        assertThat(userDecode.getUserID(), is(user.getUserID()));
    }
}