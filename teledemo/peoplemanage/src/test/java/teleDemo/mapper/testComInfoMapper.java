package teleDemo.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import teleDemo.entities.tbInfo;
import teleDemo.peopleManage10002;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = peopleManage10002.class)
public class testComInfoMapper {
    @Resource
    comInfoMapper comInfoMapper;
    @Test
    public void  testGetUserInfoById(){
        List<tbInfo> infos = comInfoMapper.getUserInfoById(1);
        for (tbInfo info : infos){
           System.out.println(info.toString());
        }

    }

    @Test
    public void testUpdateUserStatus(){
        Boolean state = comInfoMapper.updateUserStatus(0,1);
        System.out.println(state);
    }
}

