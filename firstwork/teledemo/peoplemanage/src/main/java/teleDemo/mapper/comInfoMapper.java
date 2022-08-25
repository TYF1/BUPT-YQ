package teleDemo.mapper;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.tbInfo;

import java.sql.Date;

import java.util.List;

@Mapper
public interface comInfoMapper {
    @Select("SELECT * FROM tb_info where net_speed!=null||net_speed!=0&&wifi_count!=0 order by id desc limit 100;")
    @Results(id="tbInfoMap",value={
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "asu",property = "asu",jdbcType = JdbcType.INTEGER),
            @Result(column = "cid",property = "cid",jdbcType = JdbcType.VARCHAR),
            @Result(column = "date_time",property = "dateTime",jdbcType = JdbcType.DATE),
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

    @Select("select * from tb_info where net_speed!=null||net_speed!=0&&wifi_count!=0 order by date_time desc limit #{pageNum}, #{limit};")
    @ResultMap(value = "tbInfoMap")
    List<tbInfo> gettbINfoByPage(@Param("pageNum") int pageNum, @Param("limit")int limit);
    @Select("select * from tb_info where Date(date_time) between #{starttime} and #{endtime} and user_id=#{id}" )
    @ResultMap(value = "tbInfoMap")
    List<tbInfo> gettbINfoByUserId(@Param("id")int id, @Param("starttime") Date starttime, @Param("endtime")Date endtime);

    @Select("select distinct user_id  from tb_info where Date(date_time) between #{starttime} and #{endtime} and cid=#{cid} ")
    List<Integer> getUserIdListByWifi(@Param("cid")String cid, @Param("starttime")Date starttime, @Param("endtime")Date endtime);
    @Select("select distinct user_id  from tb_info where lat between #{startLat} and #{endLat} and lon between #{startLon}and #{endLon}")
    List<Integer> getUserIdListByLatAndLon(@Param("startLat")double startLat, @Param("endLat")double endLat, @Param("startLon")double startLon, @Param("endLon")double endLon);
    @Update("update tb_info set ASTATUS=#{ASTATUS} where id=#{id}")
    List<Integer> updateUserArea(@Param("ASTATUS")String ASTATUS, @Param("id")int id);
    @Select("select ASTATUS from tb_info where id=#{id}")
    String selectUserStatus(@Param("id") int id);
    @Update("update tb_info set ASTATUS=\"1\" where id=#{id}")
    boolean setHighRiskArea(@Param("id")int id);
}
