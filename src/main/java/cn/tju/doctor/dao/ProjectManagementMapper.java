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
    List<ProjectManagement> getByWork(@Param("testResult") Integer testResult);
    ProjectManagement getByUuid(@Param("uuid") String uuid);
    int updateWork(ProjectManagement projectManagement);
    int updateTest(ProjectManagement projectManagement);
    int insertProjectManagement(@Param("projectManagement") ProjectManagement projectManagement);

    List<ProjectManagement> getByUserTest0(@Param("creatuser") String creatuser);
    List<ProjectManagement> getByUserTest1(@Param("creatuser") String creatuser);

    int deleteByUUID(@Param("uuid") String uuid);
}
