package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Projectfunding;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ProjectfundingMapper {
    @Select("SELECT * FROM `projectfunding` WHERE `number` = #{number}")
    Projectfunding getProjectfundingByNumber(String number);
    @Select("SELECT * FROM `projectfunding` WHERE `authorID` = #{authorID}")
    List<Projectfunding> getProjectfundingByAuthorID(String authorID);
    @Select("SELECT * FROM `projectfunding` WHERE `source` = #{source}")
    List<Projectfunding> getProjectfundingBySource(String source);
    @Select("SELECT * FROM `projectfunding` WHERE `${type}` = #{value}")
    List<Projectfunding> getProjectfundingList(String type, String value);
    @Select("SELECT * FROM `projectfunding` WHERE `applyTime` >= #{type} AND `ifWork` = ${value2} AND `in` = ${value3} AND `test` = ${value1}")
    List<Projectfunding> getProjectfundingListByApplytime(@Param("type") String type,@Param("value1") int value1,@Param("value2") int value2,@Param("value3") int value3);
    @Select("SELECT * FROM `projectfunding` WHERE `projectID` = #{type} AND `ifWork` = ${value2} AND `in` = ${value3} AND `test` = ${value1}")
    List<Projectfunding> getProjectfundingByprojectID(@Param("type") String type,@Param("value1") int value1,@Param("value2") int value2,@Param("value3") int value3);
    @Insert({"INSERT INTO `projectfunding` (`number`, `projectID` ,`mount` ,`rest` ," +
            "`in`, `out`, `applyID`, `applyTime`," +
            "`record`, `source`, `sourceAccount`, `go`, `goaccount`" +
            ") VALUES (#{projectfunding.number}, #{projectfunding.projectID}," +
            "#{projectfunding.mount},#{projectfunding.rest},#{projectfunding.in}," +
            "#{projectfunding.out}, #{projectfunding.applyID}, #{projectfunding.applyTime}," +
            "#{projectfunding.record},#{projectfunding.source}," +
            "#{projectfunding.sourceAccount},#{projectfunding.go},#{projectfunding.goaccount}" +
            ")"})
    int insertProjectfunding(@Param("projectfunding") Projectfunding projectfunding);
    @Update("UPDATE `projectfunding` SET test = ${projectfunding.test}, testRecord = #{projectfunding.testRecord} ," +
            "testtime = #{projectfunding.testtime}, testuser = #{projectfunding.testuser} " +
            "WHERE number = #{projectfunding.number}")
    int updateProjectfundingTest(@Param("projectfunding") Projectfunding projectfunding);
    @Update("UPDATE `projectfunding` SET ifWork = ${projectfunding.ifWork}, workRecord = #{projectfunding.workRecord}," +
            "workTime = #{projectfunding.workTime}, workUser = #{projectfunding.workUser} " +
            "WHERE number = #{projectfunding.number}")
    int updateProjectfundingWork(@Param("projectfunding") Projectfunding projectfunding);
}
