package cn.tju.doctor.service;

import cn.tju.doctor.dao.*;
import cn.tju.doctor.daomain.*;
import cn.tju.doctor.utils.numberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("ProjectFeaServer")
public class ProjectFeaServer {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ProjectDockMapper projectDockMapper;
    @Autowired
    ProjectfundingMapper projectfundingMapper;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    UserfundingMapper userfundingMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(ProjectBeanDock projectBeanDock, User user, Projectfunding projectfunding, boolean flag) throws Exception {
        int projectflag = 0;
        double money = projectfunding.getMount();
        if (flag){
            projectBeanDock.setMount(projectBeanDock.getMount() - money);
            user.setMoney(user.getMoney() + money);
            projectflag = 1;
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
            projectMapper.updateFlag(projectfunding.getProjectID(),user.getUsername(),projectflag);
        }catch (Exception e){

            System.out.println(e.getMessage());
            throw new Exception("回滚");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cTOm(User user , Userfunding userfunding) throws Exception {
        try {
            userMapper.updateUser(user);
            userfundingMapper.insertUserfunding(userfunding);
        }catch (Exception e){
            System.out.println(e);
            throw new Exception("回滚");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void money(User from , User to, Userfunding userfunding) throws Exception {
        if (userfunding.getTest() == 2){
            from.setMoney(from.getMoney()+userfunding.getMount());
            try {
                userMapper.updateUser(from);
                userfundingMapper.updateUserfundingTest(userfunding);
            }catch (Exception e){
                System.out.println(e);
                throw new Exception("审核失败回滚");
            }
        }else if (userfunding.getTest() == 1){
            to.setMoney(to.getMoney()+userfunding.getMount());
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sf.format(date);
            userfunding.setWorkTime(time);
            userfunding.setIfWork(1);
            userfunding.setTestRecord("ok");
            try {
                userMapper.updateUser(from);
                userfundingMapper.updateUserfundingTest(userfunding);
                userfundingMapper.updateUserfundingWork(userfunding);
            }catch (Exception e){
                System.out.println(e);
                throw new Exception("转账失败回滚");
            }
        }else {
            throw new Exception("检查审核状态");
        }
    }
}
