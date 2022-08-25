package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.tbInfo;
import teleDemo.entities.tbuser;

import java.util.List;

@Mapper
public interface userInfoMapper {
    @Select("SELECT * FROM teledata.tb_user where status = #{status};")
    @Results(id="tbUserMap",value={
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "phone_number",property = "phoneNumber",jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.VARCHAR),
    })
    List<tbuser> getAlltbUser(@Param("status") String status);

    @Select("select * from teledata.tb_user where status = #{status} limit #{pageNum}, #{limit};")
    @ResultMap(value = "tbUserMap")
    List<tbuser> gettbUserByPage( @Param("status") String status,@Param("pageNum") int pageNum, @Param("limit")int limit);

    @Delete("delete from teledata.tb_user where id = #{id};")
    void delUser( @Param("id") Integer id);

    @Insert("insert into teledata.tb_user (phone_number,status) values (#{phoneNumber},#{status});")
    void userAdd( @Param("phoneNumber") String phoneNumber,@Param("status") String status);

    @Update("update teledata.tb_user set phone_number = #{phoneNumber}, status = #{status} where id = #{id}")
    void updateUser( @Param("id") Integer id, @Param("phoneNumber") String phoneNumber,@Param("status") String status);
}
