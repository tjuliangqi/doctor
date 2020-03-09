package cn.tju.doctor.controller;

import cn.tju.doctor.dao.ProjectDockMapper;
import cn.tju.doctor.dao.ProjectMapper;
import cn.tju.doctor.daomain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController

@RequestMapping("/project")
public class projectController {
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectDockMapper projectDockMapper;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<List> search(@RequestBody Map map) {
        System.out.println(map.get("value"));
        System.out.println(map.get("type"));

        String type = map.get("type").toString();
        String value = map.get("value").toString();
        String firstState = type.substring(0, 1);
        String secondState = type.substring(1, 2);

        ProjectState projectState = new ProjectState();
        projectState.setStateValue1(value);
        switch (firstState){
            case "0":
                projectState.setState1("beginTime");
                break;
            case "1":
                projectState.setState1("projectID");
                break;
            case "2":
                projectState.setState1("createuser");
                break;
            case "3":
                projectState.setState1("acceptuser");
                break;
            case "4":
                projectState.setState1("uuid");
                break;
        }
        switch (secondState){
            case "0":
                projectState.setState2("process");
                projectState.setStateValue2("0");
                break;
            case "1":
                projectState.setState2("process");
                projectState.setStateValue2("1");
                break;
            case "2":
                projectState.setState2("process");
                projectState.setStateValue2("2");
                break;
            case "3":
                projectState.setState2("process");
                projectState.setStateValue2("3");
                break;
            case "4":
                projectState.setState2("process");
                projectState.setStateValue2("4");
                break;
            case "5":
                projectState.setState2("process");
                projectState.setStateValue2("5");
                break;
        }
        System.out.println(projectState);
        List<ProjectBean> projectBeans;
        if(firstState.equals("0"))
            projectBeans = projectMapper.getProjectByTimeAndState(projectState);
        else if(firstState.equals("a")) {
            String userID = value.split("\\+")[0];
            String projectID = value.split("\\+")[1];
            System.out.println(userID);
            System.out.println(projectID);
            projectBeans = projectMapper.getProjectByUserProjectID(userID, projectID);
        } else
            projectBeans = projectMapper.getProjectByIDAndState(projectState);
        return RetResponse.makeOKRsp(projectBeans);
//        return RetResponse.makeErrRsp("查无数据");
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RetResult<String> add(@RequestBody Map json) {

        String name = json.get("name").toString();
        String data = json.get("data").toString();
        String dataURL = json.get("dataURL").toString();
        String introduce = json.get("introduce").toString();
        String createuser = json.get("createuser").toString();
        String projectaccount = json.get("projectaccount").toString();
        String projectmoney = json.get("projectmoney").toString();
        String company = json.get("company").toString();
        String actor = json.get("actor").toString();
        String userType = json.get("userType").toString();
        double mount = (double)json.get("mount");
        String projectManager = json.get("projectManager").toString();
        String companyAccount = json.get("companyAccount").toString();
        String moneyManager = json.get("moneyManager").toString();
        String accounting = json.get("accounting").toString();
        String userdataURL = json.get("userdataURL").toString();

        ProjectBeanAdd projectBeanAdd = new ProjectBeanAdd();
        projectBeanAdd.setName(name);
        projectBeanAdd.setData(data);
        projectBeanAdd.setDataURL(dataURL);
        projectBeanAdd.setIntroduce(introduce);
        projectBeanAdd.setCreateuser(createuser);
        projectBeanAdd.setProjectaccount(projectaccount);
        projectBeanAdd.setProjectmoney(projectmoney);
        projectBeanAdd.setCompany(company);
        projectBeanAdd.setActor(actor);
        projectBeanAdd.setUserType(userType);
        projectBeanAdd.setUserdataURL(userdataURL);
        String uuid = UUID.randomUUID().toString().replace("-","");
        String projectID = UUID.randomUUID().toString().replace("-","");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
        String beginTime = dateFormat.format(now);
        projectBeanAdd.setUuid(uuid);
        projectBeanAdd.setProjectID(projectID);
        projectBeanAdd.setBeginTime(beginTime);
        projectBeanAdd.setProcess("0");

        ProjectBeanDock projectBeanDock = new ProjectBeanDock();
        projectBeanDock.setUuid(uuid);
        projectBeanDock.setProjectID(projectID);
        projectBeanDock.setBeginTime(beginTime);
        projectBeanDock.setCompany(company);
        projectBeanDock.setCompanyAccount(companyAccount);
        projectBeanDock.setDataURL(dataURL);
        projectBeanDock.setIntroduce(introduce);
        projectBeanDock.setMoneyManager(moneyManager);
        projectBeanDock.setProjectManager(projectManager);
        projectBeanDock.setMount(mount);
        projectBeanDock.setProcess("0");
        projectBeanDock.setAccounting(accounting);
        System.out.println(projectBeanAdd);
        projectMapper.insertProject(projectBeanAdd);
        projectDockMapper.insertProjectDock(projectBeanDock);

        return RetResponse.makeOKRsp("ok");
//        return RetResponse.makeErrRsp("查无数据");
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public RetResult<String> modify(@RequestBody Map json) {

        String projectID = json.get("projectID").toString();
        String acceptuser = json.get("acceptuser").toString();
        String userdataURL = json.get("userdataURL").toString();
        String userType = json.get("userType").toString();
        String company = json.get("company").toString();
        String actor = json.get("actor").toString();
        String process = json.get("process").toString();

        ProjectBeanAdd projectBeanAdd = new ProjectBeanAdd();
        ProjectBeanDock projectBeanDock = new ProjectBeanDock();
        projectBeanAdd.setProjectID(projectID);
        projectBeanAdd.setAcceptuser(acceptuser);
        projectBeanAdd.setUserdataURL(userdataURL);
        projectBeanAdd.setUserType(userType);
        projectBeanAdd.setCompany(company);
        projectBeanAdd.setActor(actor);
        projectBeanAdd.setProcess(process);
        String uuid = UUID.randomUUID().toString().replace("-","");
        projectBeanAdd.setUuid(uuid);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
        String beginTime = dateFormat.format(now);
        projectBeanAdd.setBeginTime(beginTime);


        projectBeanDock.setUuid(uuid);
        projectBeanDock.setProjectID(projectID);
        projectBeanDock.setCompany(company);
        projectBeanDock.setProcess(process);
        try {
            ProjectState projectState = new ProjectState();
            projectState.setState1("projectID"); // 根据projectID和process=0找到最开始创建的
            projectState.setState2("process");
            projectState.setStateValue1(projectID);
            projectState.setStateValue2("0");
            //List<ProjectBean> projectBeans = projectMapper.getProjectByProjectID(projectID);
            System.out.println(projectState);
            List<ProjectBean> projectBeans = projectMapper.getProjectByIDAndState(projectState);
            projectBeanAdd.setName(projectBeans.get(0).getName());
            projectBeanAdd.setData(projectBeans.get(0).getData());
            projectBeanAdd.setDataURL(projectBeans.get(0).getDataURL());
            projectBeanAdd.setIntroduce(projectBeans.get(0).getIntroduce());
            projectBeanAdd.setCreateuser(projectBeans.get(0).getCreateuser());
            //projectBeanAdd.setIfWork(projectBeans.get(0).getIfWork());

        } catch (Exception e){
            return RetResponse.makeErrRsp("项目还未创建，无法指派");
        }

        try {
            List<ProjectBeanDock> projectBeanDocks = projectDockMapper.getProjectDockByProjectID(projectID);
            projectBeanDock.setDataURL(projectBeanDocks.get(0).getDataURL());
            projectBeanDock.setMount(projectBeanDocks.get(0).getMount());
            projectBeanDock.setMoneyManager(projectBeanDocks.get(0).getMoneyManager());
            projectBeanDock.setIntroduce(projectBeanDocks.get(0).getIntroduce());
            projectBeanDock.setCompanyAccount(projectBeanDocks.get(0).getCompanyAccount());
            projectBeanDock.setBeginTime(projectBeanDocks.get(0).getBeginTime());
            projectBeanDock.setProjectManager(projectBeanDocks.get(0).getProjectManager());
            projectBeanDock.setAccounting(projectBeanDocks.get(0).getAccounting());

        } catch (Exception e){
            return RetResponse.makeErrRsp("未项目经理对接，无法指派");
        }
        System.out.println(projectBeanAdd);
        projectMapper.insertProject(projectBeanAdd);
        //projectDockMapper.modifyProjectDock(projectBeanDock);
        //projectDockMapper.updateByProjectID(projectBeanDock);
        projectDockMapper.updateByProjectID2(projectID, process, null);
        return RetResponse.makeOKRsp("ok");
//        return RetResponse.makeErrRsp("查无数据");
    }

    @RequestMapping(value = "/out", method = RequestMethod.POST)
    public RetResult<Map> out(@RequestBody Map json) {

        String type = json.get("type").toString();
        String value = json.get("value").toString();
        String firstState = type.substring(0, 1);
        String secondState = type.substring(1, 2);

        ProjectState projectState = new ProjectState();
        projectState.setStateValue1(value);
        switch (firstState){
            case "0":
                projectState.setState1("beginTime");
                break;
            case "1":
                projectState.setState1("projectID");
                break;
            case "2":
                projectState.setState1("createuser");
                break;
            case "3":
                projectState.setState1("acceptuser");
                break;
            case "4":
                projectState.setState1("uuid");
                break;
        }
        switch (secondState){
            case "0":
                projectState.setState2("process");
                projectState.setStateValue2("0");
                break;
            case "1":
                projectState.setState2("process");
                projectState.setStateValue2("1");
                break;
            case "2":
                projectState.setState2("process");
                projectState.setStateValue2("2");
                break;
        }
        System.out.println(projectState);
        Map result = new HashMap();
        result.put("url","");
        return RetResponse.makeOKRsp(result);
//        return RetResponse.makeErrRsp("查无数据");
    }

    @RequestMapping(value = "/lookUser", method = RequestMethod.POST)
    public RetResult<Map> lookUser(@RequestBody Map json) {

        String projectID = json.get("projectID").toString();
        String process = json.get("process").toString();
        ProjectState projectState = new ProjectState();
        projectState.setState1("projectID");
        projectState.setStateValue1(projectID);
        projectState.setState2("process");
        projectState.setStateValue2(process);
        List<ProjectBean> projectBeans;
        System.out.println(projectState);
        projectBeans = projectMapper.getProjectByIDAndState(projectState);
        System.out.println(projectBeans.size());
        HashSet<String> hashSet = new HashSet<>();
        for(ProjectBean each:projectBeans){
            System.out.println(each);
            hashSet.add(each.getAcceptuser());
        }
        Map result = new HashMap();
        result.put("acceptuser",hashSet);
        return RetResponse.makeOKRsp(result);
    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public RetResult<String> finish(@RequestBody Map json) {

        String projectID = json.get("projectID").toString();
        String process = json.get("process").toString();
        ProjectState projectState = new ProjectState();
        projectState.setState1("projectID");
        projectState.setStateValue1(projectID);
        projectState.setState2("process");
        projectState.setStateValue2(process);
        List<ProjectBean> projectBeans;
        int res = projectMapper.updateProject(projectState);
        int res2 = projectDockMapper.updateProjectDock(projectState);

        Map result = new HashMap();

        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/searchCompanyAccount", method = RequestMethod.POST)
    public RetResult<List<ProjectBeanDock>> searchCompanyAccount(@RequestBody Map json) {

        String companyAccount = json.get("companyAccount").toString();

        List<ProjectBeanDock> projectBeanDocks;
        projectBeanDocks = projectDockMapper.getByCompanyAccount(companyAccount);
        return RetResponse.makeOKRsp(projectBeanDocks);
    }
}
