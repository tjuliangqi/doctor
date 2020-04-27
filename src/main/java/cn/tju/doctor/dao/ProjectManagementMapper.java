package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.ProjectManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectManagementMapper {
    List<ProjectManagement> getByTest(@Param("testResult") Integer testResult);
    int updateTest(ProjectManagement projectManagement);
    int insertProjectManagement(@Param("projectManagement") ProjectManagement projectManagement);
}
