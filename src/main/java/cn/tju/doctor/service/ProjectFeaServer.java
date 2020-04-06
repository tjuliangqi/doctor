package cn.tju.doctor.service;

import cn.tju.doctor.dao.ProjectDockMapper;
import cn.tju.doctor.dao.ProjectfundingMapper;
import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.daomain.ProjectBeanDock;
import cn.tju.doctor.daomain.Projectfunding;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.User;
import cn.tju.doctor.utils.numberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("ProjectFeaServer")
public class ProjectFeaServer {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ProjectDockMapper projectDockMapper;
    @Autowired
    ProjectfundingMapper projectfundingMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(ProjectBeanDock projectBeanDock, User user, Projectfunding projectfunding, boolean flag) throws Exception {
        double money = projectfunding.getMount();
        if (flag){
            projectBeanDock.setMount(projectBeanDock.getMount() - money);
            user.setMoney(user.getMoney() + money);
            switch (projectfunding.getType()){
                case 1:user.setArticleIncome(user.getArticleIncome()+money);break;
                case 2:user.setProjectIncome(user.getProjectIncome()+money);break;
                case 3:user.setTrainingIncome(user.getTrainingIncome()+money);break;
                case 4:user.setHealthIncome(user.getHealthIncome()+money);break;
            }
        }else {
            projectBeanDock.setMount(projectBeanDock.getMount() + money);
            user.setMoney(user.getMoney() - money);

            switch (projectfunding.getType()){
                case 1:user.setArticleIncome(user.getArticleIncome()-money);break;
                case 2:user.setProjectIncome(user.getProjectIncome()-money);break;
                case 3:user.setTrainingIncome(user.getTrainingIncome()-money);break;
                case 4:user.setHealthIncome(user.getHealthIncome()-money);break;
            }
        }
        try {
            if (!flag){
                projectfundingMapper.updateProjectfundingRecord(projectfunding);
            }
            projectDockMapper.updateByProjectID2(projectBeanDock.getProjectID(),null, projectBeanDock.getMount());
            userMapper.updateUser(user);
        }catch (Exception e){

            System.out.println(e.getMessage());
            throw new Exception("回滚");
        }
    }
}
