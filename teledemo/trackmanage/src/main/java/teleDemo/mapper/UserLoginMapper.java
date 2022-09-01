package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.UserLogin;

import java.util.List;
/**
 * @Project Name:trackmanage
 * @File Name: UserLoginMapper
 * @Description: 用于查询userlogin数据库
 * @ HISTORY：
 *    Created   2022.8.22  WYJ
 *    Modified  2022.8.23  WYJ
 */
@Mapper
public interface UserLoginMapper {
    @Select("select * from UserLogin where userName = #{username} and password = #{password}")
    @Results(id="tbInfoMap",value={
            @Result(column = "userID",property = "userID",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "userName",property = "userName",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "role",property = "role",jdbcType = JdbcType.INTEGER),
    })
    List<UserLogin> getUserLogin(@Param("username") String username, @Param("password") String password);

    @Select("select * from UserLogin where userName = #{username}")
    @ResultMap(value = "tbInfoMap")
    List<UserLogin> getUserByName(@Param("username") String username);
}