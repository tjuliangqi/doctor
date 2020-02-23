package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Base;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BaseMapper {
    @Select("SELECT * FROM `base` WHERE `id` = ${id}")
    Base getBaseById(int id);
    @Select("SELECT * FROM `base`")
    List<Base> getBaseList();
    @Insert({"INSERT INTO `base`(`article`, `view`, " +
            "`download`, `like`, `hide`) VALUES (${base.article},${base.view},${base.download},${base.like},${base.hide})"})
    int insertBase(Base base);
    @Update("UPDATE `base` SET `article` = ${base.article}, `view` = ${base.view}," +
            " `download` = ${base.download}, `like` = ${base.like}, `hide` = ${base.hide} WHERE `uuid` = ${base.id}")
    int updateBase(Base base);
}
