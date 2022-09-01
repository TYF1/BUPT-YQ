package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.StatusCount;
import teleDemo.entities.tbuser;

import java.util.List;

@Mapper
public interface userInfoMapper {
    @Select("SELECT * FROM teledata.tb_user;")
    @Results(id="tbUserMap",value={
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "phone_number",property = "phoneNumber",jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.VARCHAR),
            @Result(column = "date",property = "date",jdbcType = JdbcType.DATE),
            @Result(column = "source",property = "source",jdbcType = JdbcType.INTEGER),
    })
    List<tbuser> getAlltbUser();

    @Select("select * from teledata.tb_user limit #{pageNum}, #{limit};")
    @ResultMap(value = "tbUserMap")
    List<tbuser> gettbUserByPage(@Param("pageNum") int pageNum, @Param("limit")int limit);

    @Select("SELECT * FROM teledata.tb_user where status = #{status};")
    @ResultMap(value = "tbUserMap")
    List<tbuser> getAllByStatus(@Param("status") int status);

    @Update("update teledata.tb_user set status=#{status}, date=#{date} where id=#{id};")
    void updateStatusById(@Param("status") int status, @Param("date") String date, @Param("id") int id);

    @Update("update teledata.tb_user set status=2, date=#{date2} where (status=3 or status=0 or (status=2 and date>#{date2})) and id in (SELECT distinct user_id FROM teledata.tb_info where date_time like #{date} and wifi_info like #{wifi});")
    int updateStatus(@Param("date2") String date2, @Param("date") String date, @Param("wifi") String wifi);

    @Update("update teledata.tb_user set status=3, date=#{date2} where (status=0 or (status=3 and date>#{date2})) and id in (SELECT distinct user_id FROM teledata.tb_info where date_time like #{date} and wifi_info like #{wifi});")
    int updateStatus2(@Param("date2") String date2, @Param("date") String date, @Param("wifi") String wifi);

    @Select("SELECT * FROM teledata.tb_user where status=#{status} and date<=#{date} and id in (select distinct user_id from teledata.tb_info where date_time like #{date2} and wifi_info like #{wifi});")
    @ResultMap(value = "tbUserMap")
    List<tbuser> getUserByDateAndWifi(@Param("date") String date, @Param("date2") String date2, @Param("wifi") String wifi, @Param("status") int status);

    @Select("SELECT * FROM teledata.tb_user where status=#{status} and id in (select distinct user_id from teledata.tb_info where date_time like #{date2} and wifi_info like #{wifi});")
    @ResultMap(value = "tbUserMap")
    List<tbuser> getUserByDateAndWifi2(@Param("date2") String date2, @Param("wifi") String wifi, @Param("status") int status);

    @Select("SELECT status, count(*) as count FROM teledata.tb_user group by status order by status;")
    @Results(id = "StatusCountMap", value = {
            @Result(column = "status", property = "status",jdbcType = JdbcType.INTEGER),
            @Result(column = "count", property = "count", jdbcType = JdbcType.INTEGER),
    })
    List<StatusCount> getStatusCount();
}
