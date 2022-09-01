package teleDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teleDemo.entities.tbRelation;
import teleDemo.mapper.relationInfoMapper;

import java.util.List;

@Service
public class relationService {

    //将DAO注入Service层
    @Autowired
    private relationInfoMapper relationInfoMapper;

    public List<tbRelation> find(int user_id){
        return relationInfoMapper.find(user_id);
    }
    public List<tbRelation> getAlltbRelation() { return relationInfoMapper.getAlltbRelation();}

    public void update(tbRelation tbrelation){
        relationInfoMapper.update(tbrelation);
    }

}
