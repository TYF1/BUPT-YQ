package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.DateWifi;

import java.util.List;

@Mapper
public interface datewifiMapper {
    @Insert("insert ignore into teledata.datewifi(date, wifi, status) values(#{date}, #{wifi}, #{status});")
    void insertDateWifi(@Param("date") String date, @Param("wifi") String wifi, @Param("status") int status);

    @Select("select * from teledata.datewifi where status = #{status};")
    @Results(id="DateWifiMap", value = {
            @Result(column = "date",property = "date",jdbcType = JdbcType.DATE),
            @Result(column = "wifi",property = "wifi",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.INTEGER),
    })
    List<DateWifi> getDateWifiByStatus(@Param("status") int status);

    @Select("select * from teledata.datewifi;")
    @ResultMap(value = "DateWifiMap")
    List<DateWifi> getAllDateWifi();
}
