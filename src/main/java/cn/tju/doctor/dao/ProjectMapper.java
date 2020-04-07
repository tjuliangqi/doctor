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

    List<ProjectBean> getProjectByAll(ProjectState projectState);
    List<ProjectBeanAdd> getProjectByAll_allField(ProjectState projectState);
    List<ProjectBean> getProjectByTimeAndState(ProjectState projectState);
    List<ProjectBean> getProjectByIDAndState(ProjectState projectState);
    List<ProjectBean> getProjectByProjectID(String projectID);
    List<ProjectBean> getProjectByProjectID2(String projectID);
    List<ProjectBean> getProjectByUserProjectID(String userID, String projectID);
    int insertProject(ProjectBeanAdd projectBeanAdd);
    int modifyProject(ProjectBeanAdd projectBeanAdd);
    int updateProject(ProjectState projectState);
    int updateProject2(ProjectState projectState);
    int updateProjectRead(String uuid);
    int updateFlag(String projectID,String userID,int flag);
}
