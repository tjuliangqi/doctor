package cn.tju.doctor.controller;

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
        }
        System.out.println(projectState);
        List<ProjectBean> projectBeans;
        if(firstState == "0")
            projectBeans = projectMapper.getProjectByTimeAndState(projectState);
        else if(firstState == "5") {
            String userID = value.split("\\+")[0];
            String projectID = value.split("\\+")[1];
            projectBeans = projectMapper.getProjectByUserProjectID(userID, projectID);
        }
        else
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
        String creatuser = json.get("creatuser").toString();
        String projectaccount = json.get("projectaccount").toString();
        String projectmoney = json.get("projectmoney").toString();
        String company = json.get("company").toString();
        String actor = json.get("actor").toString();

        ProjectBeanAdd projectBeanAdd = new ProjectBeanAdd();
        projectBeanAdd.setName(name);
        projectBeanAdd.setData(data);
        projectBeanAdd.setDataURL(dataURL);
        projectBeanAdd.setIntroduce(introduce);
        projectBeanAdd.setCreatuser(creatuser);
        projectBeanAdd.setProjectaccount(projectaccount);
        projectBeanAdd.setProjectmoney(projectmoney);
        projectBeanAdd.setCompany(company);
        projectBeanAdd.setActor(actor);
        String uuid = UUID.randomUUID().toString();
        String projectID = UUID.randomUUID().toString();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
        String beginTime = dateFormat.format(now);
        projectBeanAdd.setUuid(uuid);
        projectBeanAdd.setProjectID(projectID);
        projectBeanAdd.setBeginTime(beginTime);
        projectBeanAdd.setProcess("0");
        //projectBeanAdd.setActor("0");
        projectMapper.insertProject(projectBeanAdd);
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

        ProjectBeanAdd projectBeanAdd = new ProjectBeanAdd();
        projectBeanAdd.setProjectID(projectID);
        projectBeanAdd.setAcceptuser(acceptuser);
        projectBeanAdd.setUserdataURL(userdataURL);
        projectBeanAdd.setUserType(userType);
        projectBeanAdd.setCompany(company);
        projectBeanAdd.setActor(actor);
        String uuid = UUID.randomUUID().toString();
        projectBeanAdd.setUuid(uuid);
        try {
            List<ProjectBean> projectBeans = projectMapper.getProjectByProjectID(projectID);
            projectBeanAdd.setName(projectBeans.get(0).getName());
            projectBeanAdd.setData(projectBeans.get(0).getData());
            projectBeanAdd.setDataURL(projectBeans.get(0).getDataURL());
            projectBeanAdd.setIntroduce(projectBeans.get(0).getIntroduce());
            projectBeanAdd.setCreatuser(projectBeans.get(0).getCreatuser());
            projectBeanAdd.setIfWork(projectBeans.get(0).getIfWork());
            projectBeanAdd.setProcess(projectBeans.get(0).getProcess());

        } catch (Exception e){
            return RetResponse.makeErrRsp("项目还未创建，无法指派");
        }
        projectMapper.modifyProject(projectBeanAdd);
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
}
