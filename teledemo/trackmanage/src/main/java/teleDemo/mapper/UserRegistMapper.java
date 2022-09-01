package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import teleDemo.entities.UserLogin;

/**
 * @Project Name:trackmanage
 * @File Name: UserRegistMapper
 * @Description: 用于查询并插入userlogin数据库
 * @ HISTORY：
 * Created   2022.8.23  ZNY
 * Modified  2022.8.23  ZNY
 */
@Mapper
@Repository
public interface UserRegistMapper {

    @Select(value = "select * from UserLogin u where u.userName=#{username}")
    @Results(id="tbInfoRegistMap",value={
            @Result(column = "userID",property = "userID",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "userName",property = "userName",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "role",property = "role",jdbcType = JdbcType.INTEGER),
    })
    UserLogin findUserByName(@Param("username") String username);

    @Insert("insert into UserLogin (userName,password) values(#{username},#{password})")
    void regist(@Param("username") String username, @Param("password") String password);

    @Delete("delete from userlogin where userName = #{username} and password = #{password}")
    void delete(@Param("username") String username, @Param("password") String password);
}

