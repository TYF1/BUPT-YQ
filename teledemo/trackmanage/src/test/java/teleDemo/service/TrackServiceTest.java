package teleDemo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import teleDemo.Trackmanage10003;

import javax.annotation.Resource;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest(classes= Trackmanage10003.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TrackServiceTest {

    @Resource
    TrackService trackService;

    @Test
    public void gettbInfo() {
        ArrayList<ArrayList<Double>> list = trackService.gettbInfo(1);
        assertThat(list, notNullValue());

        ArrayList<ArrayList<Double>> list0 = trackService.gettbInfo(0);
        assertThat(list0, notNullValue());
        assertThat(list0.size(), is(0));
    }
}
