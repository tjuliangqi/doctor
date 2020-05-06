package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Record;
import cn.tju.doctor.daomain.Record2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper {
    List<Record> getRecord(@Param("number") String number);
    List<Record2> getRecord2(@Param("number") String number);
    int insertRecord(Record record);
    int insertRecord2(Record2 record);
    List<String> getPaper(@Param("number") int number);
}