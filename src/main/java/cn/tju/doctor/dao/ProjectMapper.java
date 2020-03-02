package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper {

    List<ProjectBean> getProjectByTimeAndState(ProjectState projectState);
    List<ProjectBean> getProjectByIDAndState(ProjectState projectState);
    List<ProjectBean> getProjectByProjectID(String projectID);
    int insertProject(ProjectBeanAdd projectBeanAdd);
    int modifyProject(ProjectBeanAdd projectBeanAdd);
    int updateProject(@Param("workState") WorkState workState);
}
