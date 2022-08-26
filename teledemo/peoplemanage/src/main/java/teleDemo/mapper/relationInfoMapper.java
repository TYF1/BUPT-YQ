package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.transaction.annotation.Transactional;
import teleDemo.entities.tbRelation;

import java.util.List;

@Mapper
public interface relationInfoMapper {
    @Select("SELECT * FROM teledata.tb_relation;")
    @Results(id="tbRelationMap",value={
            @Result(column = "user_id",property = "user_id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "user_relation",property = "relation",jdbcType = JdbcType.INTEGER),
            @Result(column = "status",property = "status",jdbcType = JdbcType.VARCHAR),
    })
    List<tbRelation> getAlltbRelation();

    @Select("select * from teledata.tb_relation limit #{pageNum}, #{limit};")
    @ResultMap(value = "tbRelationMap")
    List<tbRelation> gettbRelationByPage(@Param("pageNum") int pageNum, @Param("limit")int limit);

    @Select("select * from teledata.tb_relation where user_id=#{user_id}")
    @Transactional
    @ResultMap(value = "tbRelationMap")
    List<tbRelation> find(int user_id);
}
