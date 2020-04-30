package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {
    @Select("SELECT * FROM `message` WHERE createuser = #{user} ORDER BY createtime DESC LIMIT 0,5")
    List<Message> get5Message(@Param("user") String user);
    @Select("SELECT * FROM `message` WHERE createuser = #{user} ORDER BY createtime DESC")
    List<Message> getAllMessage(@Param("user") String user);
    @Insert({"INSERT INTO `message`(`title`, `detail`, " +
            "`createuser`, `uuid`) VALUES (#{message.title},#{message.detail},#{message.createuser},#{message.uuid})"})
    int insertMessage(@Param("message") Message message);
}
