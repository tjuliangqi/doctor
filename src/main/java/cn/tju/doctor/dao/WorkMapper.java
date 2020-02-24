package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.Work;
import cn.tju.doctor.daomain.WorkState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WorkMapper {
    List<Work> getWorkByIDAndState(WorkState workState);
    int insertWork(Work work);
    int updateWork(@Param("workState") WorkState workState);
}
