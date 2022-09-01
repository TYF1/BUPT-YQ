package teleDemo.mapper;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.tbInfo;
import teleDemo.entities.Date1;

import java.util.List;

@Mapper
public interface comInfoMapper {
    @Select("SELECT * FROM teledata.tb_info where net_speed!=null||net_speed!=0&&wifi_count!=0 order by id desc limit 100;")
    @Results(id="tbInfoMap",value={
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "asu",property = "asu",jdbcType = JdbcType.INTEGER),
            @Result(column = "cid",property = "cid",jdbcType = JdbcType.VARCHAR),
            @Result(column = "date_time",property = "dateTime",jdbcType = JdbcType.DATE),
            @Result(column = "date_time",property = "time",jdbcType = JdbcType.TIME),
            @Result(column = "dbm",property = "dbm",jdbcType = JdbcType.INTEGER),
            @Result(column = "lac",property = "lac",jdbcType = JdbcType.VARCHAR),
            @Result(column = "lat",property = "lat",jdbcType = JdbcType.DOUBLE),
            @Result(column = "lon",property = "lon",jdbcType = JdbcType.DOUBLE),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.INTEGER),
            @Result(column = "net_speed",property = "netSpeed",jdbcType = JdbcType.VARCHAR),
            @Result(column = "unread_sms",property = "unreadSms",jdbcType = JdbcType.INTEGER),
            @Result(column = "wifi_count",property = "wifiCount",jdbcType = JdbcType.INTEGER),
            @Result(column = "wifi_info",property = "wifiInfo",jdbcType = JdbcType.LONGVARCHAR),
    })
    List<tbInfo> getAlltbINfo();

    @Select("select * from teledata.tb_info where net_speed!=null||net_speed!=0&&wifi_count!=0 order by date_time desc limit #{pageNum}, #{limit};")
    @ResultMap(value = "tbInfoMap")
    List<tbInfo> gettbINfoByPage(@Param("pageNum") int pageNum, @Param("limit")int limit);

    @Select("SELECT wifi_info FROM teledata.tb_info where user_id = #{id} and date_time like #{date} and wifi_info like \"[{%\";")
    @Results(id = "WifiMap", value = {
            @Result(column = "wifi_info",property = "wifiInfo",jdbcType = JdbcType.LONGVARCHAR),
    })
    List<tbInfo> getInfoByDateAndId(@Param("id") int id, @Param("date") String date);

    @Select("select convert(date_time, Date) as d, count(distinct user_id) as c, sum(user_id=#{id}) as e from teledata.tb_info where wifi_info like \"[{%\" group by d having c>1 and e>0;")
    @Results(id = "Date1Map", value = {
            @Result(column = "d", property = "date", jdbcType = JdbcType.DATE),
    })
    List<Date1> getDateById(@Param("id") int id);

    @Select("select convert(date_time, Date) as d, count(distinct user_id) as c, sum(user_id=#{id}) as e from teledata.tb_info where date_time>=(select date from teledata.tb_user where id=#{id}) and wifi_info like \"[{%\" group by d having c>1 and e>0;")
    @ResultMap(value = "Date1Map")
    List<Date1> getDateById2(@Param("id") int id);

    @Select("SELECT * FROM teledata.tb_info where user_id in (select id from teledata.tb_user where status=0 or status=3) and date_time like #{date} and wifi_info like #{wifi};")
    @ResultMap(value = "tbInfoMap")
    List<tbInfo> getInfoByDateAndWifi(@Param("date") String date, @Param("wifi") String wifi);
}
