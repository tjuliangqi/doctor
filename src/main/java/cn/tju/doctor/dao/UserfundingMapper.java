package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Userfunding;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserfundingMapper {
    @Select("SELECT * FROM `userfunding` WHERE `number` = #{number}")
    Userfunding getUserfundingByNumber(@Param("number") String number);
    @Select("SELECT * FROM `userfunding` WHERE `test` = #{test}")
    List<Userfunding> getUserfundingByTest(@Param("test") int test);
    @Select("SELECT * FROM `userfunding` WHERE `ifWork` = #{ifWork}")
    List<Userfunding> getUserfundingByIfWork(@Param("ifWork") int ifWork);
    @Select("SELECT * FROM `userfunding` WHERE `authorID` = #{authorID}")
    List<Userfunding> getUserfundingByAuthorID(String authorID);
    @Select("SELECT * FROM `userfunding` WHERE `source` = #{source}")
    List<Userfunding> getUserfundingBySource(String source);
    @Select("SELECT * FROM `userfunding` WHERE `testUser` = #{testUser} AND `type` = ${type} AND `test` = ${test}")
    List<Userfunding> getUserfundingListByTest(@Param("type") int type,@Param("test") int test,@Param("testUser") String testUser);
    @Select("SELECT * FROM `userfunding` WHERE `applyTime` >= #{type} AND `ifWork` = ${value2} AND `in` = ${value3} AND `test` = ${value1}")
    List<Userfunding> getUserfundingListByApplytime(@Param("type") String type,@Param("value1") int value1,@Param("value2") int value2,@Param("value3") int value3);
    @Select("SELECT * FROM `userfunding` WHERE `authorID` = #{type} AND `ifWork` = ${value2} AND `in` = ${value3} AND `test` = ${value1}")
    List<Userfunding> getUserfundingListByAuthorId(@Param("type") String type,@Param("value1") int value1,@Param("value2") int value2,@Param("value3") int value3);
    @Insert("INSERT INTO `userfunding` (`number`, `authorID` ,`mount`,`rest`,`in`, `out`, `applyID`, `applyTime`, " +
            "`record` ,`source` ,`sourceAccount` ,`go` ,`goaccount`,`type`" +
            ") VALUES (#{userfunding.number}, #{userfunding.authorID}," +
            "#{userfunding.mount},#{userfunding.rest},#{userfunding.in}," +
            "#{userfunding.out}, #{userfunding.applyID}, #{userfunding.applyTime}," +
            "#{userfunding.record},#{userfunding.source}," +
            "#{userfunding.sourceAccount},#{userfunding.go},#{userfunding.goaccount},#{userfunding.type})")
    int insertUserfunding(@Param("userfunding") Userfunding userfunding);
    @Update("UPDATE `userfunding` SET test = ${userfunding.test}, testRecord = #{userfunding.testRecord} ," +
            "testtime = #{userfunding.testtime}, testuser = #{userfunding.testuser} " +
            "WHERE number = #{userfunding.number}")
    int updateUserfundingTest(@Param("userfunding") Userfunding userfunding);
    @Update("UPDATE `userfunding` SET ifWork = #{userfunding.ifWork}, workRecord = #{userfunding.workRecord}," +
            "workTime = #{userfunding.workTime}, workUser = #{userfunding.workUser} " +
            "WHERE number = #{userfunding.number}")
    int updateUserfundingWork(@Param("userfunding") Userfunding userfunding);
}
