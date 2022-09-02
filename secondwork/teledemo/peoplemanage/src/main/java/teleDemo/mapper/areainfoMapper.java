package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.tbarea;

import java.util.List;

@Mapper
public interface areainfoMapper {
    @Insert("insert into tb_area(lat,lon,num) values(#{lat},#{lon},#{num})")
    void insertArea(tbarea tbarea);

    @Update("update tb_area set num=#{num} where lat=#{lat} and lon=#{lon}")
    void updateArea(tbarea tbarea);

    @Select("select * from tb_area where lat=#{lat} and lon=#{lon}")
    @Results(value={
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "num",property = "num",jdbcType = JdbcType.INTEGER),
            @Result(column = "lat",property = "lat",jdbcType = JdbcType.DOUBLE),
            @Result(column = "lon",property = "lon",jdbcType = JdbcType.DOUBLE)
    })
    tbarea getAreaByArea(tbarea tbarea);

    @Select("select * from tb_area ")
    @Results(id="tbInfoMap",value={
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "lat",property = "lat",jdbcType = JdbcType.DOUBLE),
            @Result(column = "lon",property = "lon",jdbcType = JdbcType.DOUBLE),
    })
    List<tbarea> getArea();
}
