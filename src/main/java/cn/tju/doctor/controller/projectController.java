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
import java.util.stream.Collectors;

import static cn.tju.doctor.utils.ToBuildersUtils.intoState;

@RestController

@RequestMapping("/project")
public class projectController {
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectDockMapper projectDockMapper;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<List> search(@RequestBody Map map) {
        System.out.println("查询组合："+map.get("type").toString());
        System.out.println("查询值："+map.get("value").toString());

        String type = map.get("type").toString();
        String value = map.get("value").toString();
        String firstState = type.substring(0, 1);
        String secondState = type.substring(1, 2);

        ProjectState projectState = new ProjectState();
        projectState = intoState(projectState, firstState, secondState, value);
        System.out.println(projectState);

        List<ProjectBean> projectBeans;

        if(firstState.equals("a")){
            String userID = value.split("\\+")[0];
            String projectID = value.split("\\+")[1];
            projectBeans = projectMapper.getProjectByUserProjectID(userID, projectID);
        }else{
            projectBeans = projectMapper.getProjectByAll(projectState);
            // 根据 ProjectID 去重
            if(type.equals("0b") || type.equals("2b") || type.equals("3b") || type.equals("5b")){
                List<ProjectBean> unique = projectBeans.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ProjectBean::getProjectID))), ArrayList::new)
                );
                //unique.forEach(p -> System.out.println(p));
                projectBeans = unique;
            }
            // 根据 Process 去重
            if(type.equals("1b")){
                List<ProjectBean> unique = projectBeans.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ProjectBean::getProcess))), ArrayList::new)
                );
                //unique.forEach(p -> System.out.println(p));
                projectBeans = unique;
            }
        }
        return RetResponse.makeOKRsp(projectBeans);
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
        projectBeanAdd.setProjectManager(projectManager);
        String uuid = UUID.randomUUID().toString().replace("-","");
        String projectID = UUID.randomUUID().toString().replace("-","");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
        String beginTime = dateFormat.format(now);
        projectBeanAdd.setUuid(uuid);
        projectBeanAdd.setProjectID(projectID);
        projectBeanAdd.setBeginTime(beginTime);
        projectBeanAdd.setProcess("0");
        projectBeanAdd.setIfWork(0);

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
        projectBeanDock.setIfWork(0);
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
        projectBeanAdd.setProjectID(projectID);
        projectBeanAdd.setAcceptuser(acceptuser);
        projectBeanAdd.setUserdataURL(userdataURL);
        projectBeanAdd.setUserType(userType);
        projectBeanAdd.setCompany(company);
        projectBeanAdd.setActor(actor);
        projectBeanAdd.setProcess(process);
        projectBeanAdd.setIfWork(0);
        String uuid = UUID.randomUUID().toString().replace("-","");
        projectBeanAdd.setUuid(uuid);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
        String beginTime = dateFormat.format(now);
        projectBeanAdd.setBeginTime(beginTime);

        try {
            ProjectState projectState = new ProjectState();
            projectState.setState1("projectID"); // 根据projectID和process=0找到最开始创建的
            projectState.setState2("process");
            projectState.setStateValue1(projectID);
            projectState.setStateValue2("0");
            //List<ProjectBean> projectBeans = projectMapper.getProjectByProjectID(projectID);
            System.out.println(projectState);
            List<ProjectBeanAdd> projectBeans = projectMapper.getProjectByAll_allField(projectState);
            projectBeanAdd.setName(projectBeans.get(0).getName());
            projectBeanAdd.setData(projectBeans.get(0).getData());
            projectBeanAdd.setDataURL(projectBeans.get(0).getDataURL());
            projectBeanAdd.setIntroduce(projectBeans.get(0).getIntroduce());
            projectBeanAdd.setCreateuser(projectBeans.get(0).getCreateuser());
            projectBeanAdd.setProjectManager(projectBeans.get(0).getProjectManager());
            //projectBeanAdd.setIfWork(projectBeans.get(0).getIfWork());

        } catch (Exception e){
            return RetResponse.makeErrRsp("项目还未创建，无法指派");
        }

        ProjectBeanDock projectBeanDock = new ProjectBeanDock();
        projectBeanDock.setCompany(company);
        projectBeanDock.setProcess(process);
        projectBeanDock.setIfWork(0);
        System.out.println(projectBeanDock);
        projectMapper.insertProject(projectBeanAdd);
        //int i =projectDockMapper.modifyProjectDock(projectBeanDock);
        //System.out.println(i);
        int i = projectDockMapper.updateByProjectID(projectBeanDock);
        System.out.println(i);

        //Object mount = null;
        //projectDockMapper.updateByProjectID2(projectID, process, mount);
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
