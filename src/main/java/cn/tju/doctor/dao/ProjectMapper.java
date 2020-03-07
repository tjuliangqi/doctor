package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.ProjectBean;
import cn.tju.doctor.daomain.ProjectBeanAdd;
import cn.tju.doctor.daomain.ProjectState;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper {

    List<ProjectBean> getProjectByTimeAndState(ProjectState projectState);
    List<ProjectBean> getProjectByIDAndState(ProjectState projectState);
    List<ProjectBean> getProjectByProjectID(String projectID);
    List<ProjectBean> getProjectByUserProjectID(String userID, String projectID);
    int insertProject(ProjectBeanAdd projectBeanAdd);
    int modifyProject(ProjectBeanAdd projectBeanAdd);
    int updateProject(ProjectState projectState);
}