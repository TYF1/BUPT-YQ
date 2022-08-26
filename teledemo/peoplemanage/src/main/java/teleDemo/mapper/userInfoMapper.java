package teleDemo.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import teleDemo.entities.tbInfo;
import teleDemo.entities.tbuser;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Mapper
public interface userInfoMapper {
    @Select("SELECT * FROM teledata.tb_user;")
    @Results(id="tbUserMap",value={
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "phone_number",property = "phoneNumber",jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.VARCHAR),
    })
    List<tbuser> getAlltbUser();

    @Select("select * from teledata.tb_user limit #{pageNum}, #{limit};")
    @ResultMap(value = "tbUserMap")
    List<tbuser> gettbUserByPage(@Param("pageNum") int pageNum, @Param("limit")int limit);

    // 增
    @Update("INSERT INTO `tb_user` (`id`, `phone_number`, `status`) VALUES (#{id},#{phoneNumber},#{status});")
    @Transactional
    void add(tbuser user);

    // 删
    @Update("delete from teledata.tb_user where id=#{id}")
    @Transactional
    void delete(int id);

    // 改
    @Update("update teledata.tb_user set id=#{id}, phone_number=#{phoneNumber}, status=#{status} where id=#{id};")
    @Transactional
    void update(tbuser user);

    // 查
    @Select("select * from teledata.tb_user where id=#{id}")
    @Transactional
    @ResultMap(value = "tbUserMap")
    List<tbuser> find(int id);

    // 查（改）
    @Select("select * from teledata.tb_user where id=#{id}")
    @Transactional
    @ResultMap(value = "tbUserMap")
    tbuser findUserById(int id);


    @Select("select * from teledata.tb_user where status = '1';")
    @ResultMap(value = "tbUserMap")
    @Transactional
    List<tbuser> findConfirmed();

    @Select("select phone_number from teledata.tb_user where id=#{id}")
    @ResultMap(value = "tbUserMap")
    @Transactional
    String getNum(int id);
}
