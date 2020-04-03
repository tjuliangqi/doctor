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

    @Transactional(rollbackFor = Exception.class)
    public void add(ProjectBeanDock projectBeanDock, User user, double money, boolean flag) throws Exception {
        if (flag){
            projectBeanDock.setMount(projectBeanDock.getMount() - money);
            user.setMoney(user.getMoney() + money);
        }else {
            projectBeanDock.setMount(projectBeanDock.getMount() + money);
            user.setMoney(user.getMoney() - money);
        }

        try {
            projectDockMapper.updateByProjectID2(projectBeanDock.getProjectID(),null, projectBeanDock.getMount());
            userMapper.updateUser(user);
        }catch (Exception e){

            System.out.println(e.getMessage());
            throw new Exception("回滚");
        }
    }
}
