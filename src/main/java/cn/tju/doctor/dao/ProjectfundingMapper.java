package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Projectfunding;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProjectfundingMapper {
    @Select("SELECT * FROM `projectfunding` WHERE `number` = #{number}")
    Projectfunding getProjectfundingByNumber(String number);
    @Select("SELECT * FROM `projectfunding` WHERE `${type}` = #{value}")
    List<Projectfunding> getProjectfundingList(String type, String value);
    @Insert({"INSERT INTO `projectfunding`(`projectID` ,`mount` ,`rest` ," +
            "`in` ,`out` ,`applyID`, `applyTime`" +
            "`record` ,`source` ,`sourceAccount` ,`go` ,`goaccount` ," +
            ") VALUES (#{projectfunding.projectID}," +
            "#{projectfunding.mount},#{projectfunding.rest},#{projectfunding.in}," +
            "#{projectfunding.out}, #{projectfunding.applyID}, #{projectfunding.applyTime}" +
            "#{projectfunding.record},#{projectfunding.source}," +
            "#{projectfunding.sourceAccount},#{projectfunding.go},#{projectfunding.goaccount}," +
            ")"})
    int insertProjectfunding(Projectfunding projectfunding);
    @Update("UPDATE `projectfunding` SET test = ${projectfunding.test}, testRecord = #{projectfunding.testRecord} ," +
            "testtime = #{projectfunding.testtime}, testproject = #{projectfunding.testproject} " +
            "WHERE number = #{projectfunding.number}")
    int updateProjectfundingTest(Projectfunding projectfunding);
    @Update("UPDATE `projectfunding` SET ifWork = ${projectfunding.ifWork}, testRecord = #{projectfunding.workRecord}," +
            "worktime = #{projectfunding.worktime}, workproject = #{projectfunding.workproject} " +
            "WHERE number = #{projectfunding.number}")
    int updateProjectfundingWork(Projectfunding projectfunding);
}
