package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper {
    List<Record> getRecord(@Param("number") String number);
    int insertRecord(Record record);
    List<String> getPaper(@Param("number") int number);
}