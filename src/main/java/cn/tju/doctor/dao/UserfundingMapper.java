package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Userfunding;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserfundingMapper {
    @Select("SELECT * FROM `userfunding` WHERE `number` = #{number}")
    Userfunding getUserfundingByNumber(String number);
    @Select("SELECT * FROM `userfunding` WHERE `${type}` = #{value}")
    List<Userfunding> getUserfundingList(String type, String value);
    @Insert({"INSERT INTO `userfunding`(`authorID` ,`mount` ,`rest` ," +
            "`in` ,`out` ,`applyID`, `applyTime`" +
            "`record` ,`source` ,`sourceAccount` ,`go` ,`goaccount` ," +
            ") VALUES (#{userfunding.authorID}," +
            "#{userfunding.mount},#{userfunding.rest},#{userfunding.in}," +
            "#{userfunding.out}, #{userfunding.applyID}, #{userfunding.applyTime}" +
            "#{userfunding.record},#{userfunding.source}," +
            "#{userfunding.sourceAccount},#{userfunding.go},#{userfunding.goaccount}," +
            ")"})
    int insertUserfunding(Userfunding userfunding);
    @Update("UPDATE `userfunding` SET test = ${userfunding.test}, testRecord = #{userfunding.testRecord} ," +
            "testtime = #{userfunding.testtime}, testuser = #{userfunding.testuser} " +
            "WHERE number = #{userfunding.number}")
    int updateUserfundingTest(Userfunding userfunding);
    @Update("UPDATE `userfunding` SET ifWork = ${userfunding.ifWork}, testRecord = #{userfunding.workRecord}," +
            "worktime = #{userfunding.worktime}, workuser = #{userfunding.workuser} " +
            "WHERE number = #{userfunding.number}")
    int updateUserfundingWork(Userfunding userfunding);
}
