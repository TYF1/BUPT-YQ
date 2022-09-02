package teleDemo.mapper;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.Record;
import teleDemo.entities.tbuser;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Mapper
public interface updateRecordMapper
{
    @Insert("insert into tb_record(user_id,pre_status,cur_status,update_time) values(#{user_id},#{pre_status},#{cur_status},#{update_time})")
    public int addRecord(@Param("user_id") int user_id,@Param("pre_status") String pre_status,@Param("cur_status") String cur_status,@Param("update_time") Date update_time);

    @Select("SELECT * FROM tb_record;")
    @Results(id = "RecordMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "user_id", jdbcType = JdbcType.INTEGER),
            @Result(column = "pre_status", property = "pre_status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cur_status", property = "cur_status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "update_time", jdbcType = JdbcType.DATE),
    })
    List<Record> getAllRecord();

    @Select("select * from tb_record limit #{pageNum}, #{limit};")
    @ResultMap(value = "RecordMap")
    List<Record> gettbRecordByPage(@Param("pageNum") int pageNum, @Param("limit") int limit);
}
