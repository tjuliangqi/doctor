package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface DataMapper {
    @Select("SELECT * FROM `data` WHERE `date` = #{date}")
    Data getDataById(String date);
    @Select("SELECT * FROM `data`")
    List<Data> getDataList();
    @Insert({"INSERT INTO `data`(`article`, `view`, " +
            "`download`, `like`, `hide`, `date`) VALUES (${data.article},${data.view},${data.download},${data.like},${data.hide},#{data.date})"})
    int insertData(Data data);
    @Update("UPDATE `data` SET `article` = ${data.article}, `view` = ${data.view}," +
            " `download` = ${data.download}, `like` = ${data.like}, `hide` = ${data.hide} WHERE `date` = ${data.date}")
    int updateData(Data data);
}
