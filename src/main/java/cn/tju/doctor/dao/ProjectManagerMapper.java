package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.ProjectManagerBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectManagerMapper {
    @Select("SELECT * FROM `projectmanager` WHERE `projectID` = #{projectID}")
    List<ProjectManagerBean> getProjectManagerByProjectID(String projectID);

    @Insert("INSERT INTO `doctor`.`projectmanager`(`uuid`, `projectID`, `mount`, " +
            "`projectManager`, `company`, `process`, `dataURL`, `companyAccount`, `moneyManager`, " +
            "`beginTime`, `endtime`, `introduce`) VALUES (#{ProjectManager.uuid}, #{ProjectManager.projectID}," +
            " #{ProjectManager.mount}, #{ProjectManager.projectManager}, #{ProjectManager.company}, #{ProjectManager.process}, " +
            "#{ProjectManager.dataURL}, #{ProjectManager.companyAccount}, #{ProjectManager.moneyManager}, #{ProjectManager.beginTime}, " +
            "#{ProjectManager.endtime}, #{ProjectManager.introduce});")
    int insertProjectManager(@Param("ProjectManager") ProjectManagerBean ProjectManager);

    @Update("UPDATE `doctor`.`projectmanager` SET `projectID` = #{ProjectManager.projectID}, `mount` = #{ProjectManager.mount}, " +
            "`projectManager` = #{ProjectManager.projectManager}, `company` = #{ProjectManager.company}, `process` = #{ProjectManager.process}, " +
            "`dataURL` = #{ProjectManager.dataURL}, `companyAccount` = #{ProjectManager.companyAccount}, `moneyManager` = #{ProjectManager.moneyManager}, " +
            "`beginTime` = #{ProjectManager.beginTime}, `endtime` = #{ProjectManager.endtime}, `introduce` = #{ProjectManager.introduce} WHERE `uuid` = #{ProjectManager.uuid};")
    int updateProjectManager(@Param("ProjectManager") ProjectManagerBean ProjectManager);
}
