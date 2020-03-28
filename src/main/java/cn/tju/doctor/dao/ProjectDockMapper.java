package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.ProjectBeanDock;
import cn.tju.doctor.daomain.ProjectState;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectDockMapper {
    int insertProjectDock(ProjectBeanDock projectBeanDock);
    List<ProjectBeanDock> getProjectDockByProjectID(String projectID);
    List<ProjectBeanDock> getByCompanyAccount(String companyAccount);

    int modifyProjectDock(ProjectBeanDock projectBeanDock);
    int updateProjectDock(ProjectState projectState);
    int updateProjectDock2(ProjectState projectState);
    int updateByProjectID(ProjectBeanDock projectBeanDock);
    int updateByProjectID2(String projectID, String process, Object mount);
}
