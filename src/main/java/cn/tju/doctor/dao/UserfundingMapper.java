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
    @Select("SELECT * FROM `userfunding` WHERE `${type}` = #{value}")
    List<Userfunding> getUserfundingList(@Param("type") String type,@Param("value") String value);
    @Insert("INSERT INTO `userfunding` (`number`, `authorID` ,`mount`,`rest`,`in`, `out`, `applyID`, `applyTime`, " +
            "`record` ,`source` ,`sourceAccount` ,`go` ,`goaccount`" +
            ") VALUES (#{userfunding.number}, #{userfunding.authorID}," +
            "#{userfunding.mount},#{userfunding.rest},#{userfunding.in}," +
            "#{userfunding.out}, #{userfunding.applyID}, #{userfunding.applyTime}," +
            "#{userfunding.record},#{userfunding.source}," +
            "#{userfunding.sourceAccount},#{userfunding.go},#{userfunding.goaccount})")
    int insertUserfunding(@Param("userfunding") Userfunding userfunding);
    @Update("UPDATE `userfunding` SET test = ${userfunding.test}, testRecord = #{userfunding.testRecord} ," +
            "testtime = #{userfunding.testtime}, testuser = #{userfunding.testuser} " +
            "WHERE number = #{userfunding.number}")
    int updateUserfundingTest(@Param("userfunding") Userfunding userfunding);
    @Update("UPDATE `userfunding` SET ifWork = #{userfunding.ifWork}, testRecord = #{userfunding.workRecord}," +
            "workTime = #{userfunding.workTime}, workUser = #{userfunding.workUser} " +
            "WHERE number = #{userfunding.number}")
    int updateUserfundingWork(@Param("userfunding") Userfunding userfunding);
}
