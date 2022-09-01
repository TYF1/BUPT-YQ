/**
 * @Project Name:MSHD
 * @File Name: weiboInfoMapper.java
 * @Description: Mapper类
 * @HISTORY：
 *    Created   2022.8.30  魏瑾
 */

package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.tbWeiboComments;

import java.util.List;

@Mapper
public interface weiboInfoMapper {
    @Select("select * from eqe.weibo_comments where keyWord=#{keyWord} order by commentId desc limit 10")
    @Results(id="tbWeiboMap", value={
            @Result(column = "commentId", property = "commentId", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "userName", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "userFans", property = "userFans", jdbcType = JdbcType.INTEGER),
            @Result(column = "comments_1", property = "comments_1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "keyWord", property = "keyWord", jdbcType = JdbcType.VARCHAR),
            @Result(column = "link", property = "link", jdbcType = JdbcType.VARCHAR)
    })
    List<tbWeiboComments> getNewest10Weibo(@Param("keyWord") String keyWord);
}