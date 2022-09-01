package teleDemo.service;

import teleDemo.entities.tbuser;
import teleDemo.mapper.userInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    //将DAO注入Service层
    @Autowired
    private userInfoMapper userInfoMapper;

    //  增
    public void Add(tbuser user){
        userInfoMapper.add(user);
    }

    //  删
    public void delete(int id){
        userInfoMapper.delete(id);
    }

    //  改
    public void update( tbuser user){
        userInfoMapper.update(user);
    }

    //  查
    public List<tbuser> find(int id){
        return userInfoMapper.find(id);
    }

    //  查（改）
    public tbuser findUserById(int id){return userInfoMapper.findUserById(id);}

    public List<tbuser> findConfirmed(){
        return userInfoMapper.findConfirmed();
    }

    public List<tbuser> findSecondary(){
        return userInfoMapper.findSecondary();
    }

    public String getNum(int id){
        return userInfoMapper.getNum(id);
    }

}
