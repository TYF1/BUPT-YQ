/**
 * @Project Name:MSHD
 * @File Name: hsInfoMapper.java
 * @Description: Mapper类
 * @HISTORY：
 *    Created   2022.8.24  魏瑾
 */

package teleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.tbHotSearch;

import java.util.List;

@Mapper
public interface hsInfoMapper {
    @Select("SELECT * FROM eqe.t_hotsearch ORDER BY hsHotness DESC LIMIT 10;")
    @Results(id="tbHsMap", value={
            @Result(column = "hsId", property = "hsId", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "hsContent", property = "hsContent", jdbcType = JdbcType.VARCHAR),
            @Result(column = "hsHotness", property = "hsHotness", jdbcType = JdbcType.INTEGER),
            @Result(column = "hsTime", property = "hsTime", jdbcType = JdbcType.VARCHAR),
    })
    List<tbHotSearch> getTop10HotSearch();
}