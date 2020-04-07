package cn.tju.doctor.controller;

import cn.tju.doctor.dao.ProjectDockMapper;
import cn.tju.doctor.dao.ProjectMapper;
import cn.tju.doctor.daomain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static cn.tju.doctor.utils.ToBuildersUtils.intoState;
import static cn.tju.doctor.utils.fileUtil.upload;

@RestController

@RequestMapping("/project")
public class projectController {
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectDockMapper projectDockMapper;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<List> search(@RequestBody Map map) {
        // 2b+公司用户id，查公司有哪些项目，管理员5b， 普通用户3b
        // 1b+项目id，查项目有哪些期数
        // 11+项目id，查这个项目的第一期
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
        List<ProjectBeanDock> projectBeanDocks;

        if(firstState.equals("a")){
            String userID = value.split("\\+")[0];
            String projectID = value.split("\\+")[1];
            projectBeans = projectMapper.getProjectByUserProjectID(userID, projectID);
        }
        // 2b+公司用户id，查公司有哪些项目，管理员5b
        else if(type.equals("2b") || type.equals("5b")){
            projectBeanDocks = projectDockMapper.getALLPJList(type, value);
            return RetResponse.makeOKRsp(projectBeanDocks);
        }
        else{
            projectBeans = projectMapper.getProjectByAll(projectState);
            //删除0期
            if(projectBeans.size()>1){
                for (int i = 0; i < projectBeans.size(); i++) {
                    if ("0".equals(projectBeans.get(i).getProcess()))
                        projectBeans.remove(i);
                }
            }
            // 根据 ProjectID 去重
            if(type.equals("0b") || type.equals("3b")){
                Collections.reverse(projectBeans); // 先反向排序
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

    @RequestMapping(value = "/add2", method = RequestMethod.POST)
    public RetResult<String> add2(@RequestParam("file") MultipartFile file,
                                     @RequestParam("name") String name,
                                     @RequestParam("data") String data,
                                     @RequestParam("dataURL") String dataURL,
                                     @RequestParam("introduce") String introduce,
                                     @RequestParam("createuser") String createuser,
                                     @RequestParam("projectaccount") String projectaccount,
                                     @RequestParam("projectmoney") String projectmoney,
                                     @RequestParam("company") String company,
                                     @RequestParam("actor") String actor,
                                     @RequestParam("userType") String userType,
                                     @RequestParam("mount") double mount,
                                     @RequestParam("projectManager") String projectManager,
                                     @RequestParam("companyAccount") String companyAccount,
                                     @RequestParam("moneyManager") String moneyManager,
                                    @RequestParam("accounting") String accounting,
                                    @RequestParam("userdataURL") String userdataURL) {

        dataURL = upload(file,createuser);

        return RetResponse.makeOKRsp("ok");
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
        double mount = Double.valueOf(json.get("mount").toString());
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
        projectBeanDock.setName(name);
        projectBeanDock.setCreateuser(createuser);
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
        List<ProjectBeanDock> projectBeanDocks =  projectDockMapper.getProjectDockByProjectID(projectID);
        String process2 = projectBeanDocks.get(0).getProcess();
        System.out.println(projectBeanDocks.get(0).getProcess());
        System.out.println(projectBeanDocks.get(0).getIfWork());
        if(!"0".equals(projectBeanDocks.get(0).getProcess())  && projectBeanDocks.get(0).getIfWork() == 0){
            return RetResponse.makeErrRsp("上一期还未完成，无法指派");
        }
        ProjectBeanDock projectBeanDock = new ProjectBeanDock();
        projectBeanDock.setProjectID(projectID);
        //projectBeanDock.setCompany(company);
        //process = Integer.toString (Integer.valueOf(process2) + 1);
        projectBeanDock.setProcess(process);
        projectBeanDock.setIfWork(0);
        System.out.println(projectBeanDock);
        projectMapper.insertProject(projectBeanAdd);
        projectDockMapper.updateByProjectID(projectBeanDock);

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
    public RetResult<List> lookUser(@RequestBody Map json) {

        String projectID = json.get("projectID").toString();

        List<ProjectBean> projectBeans;
        projectBeans = projectMapper.getProjectByProjectID2(projectID);
        System.out.println(projectBeans.size());
        HashSet<String> hashSet = new HashSet<>();
        List<Map> list = new ArrayList<>();
        for(ProjectBean each:projectBeans){
            System.out.println(each);
            String acceptuser = each.getAcceptuser();
            if(acceptuser!=null && !hashSet.contains(acceptuser)) {
                hashSet.add(acceptuser);
                Map<String, String> map = new HashMap<>();
                map.put("projectID", projectID);
                map.put("acceptuser", acceptuser);
                map.put("company", each.getCompany());
                map.put("introduce", each.getIntroduce());
                list.add(map);
            }
        }
        return RetResponse.makeOKRsp(list);
    }
    @RequestMapping(value = "/lookUserGuan", method = RequestMethod.POST)
    public RetResult<List> lookUserGuan(@RequestBody Map json) {

        String projectID = json.get("projectID").toString();

        List<ProjectBeanDock> projectBeanDocks;
        projectBeanDocks = projectDockMapper.getProjectDockByProjectID(projectID);
        System.out.println(projectBeanDocks.size());
        HashSet<String> hashSet = new HashSet<>();
        List<Map> list = new ArrayList<>();
        for(ProjectBeanDock each:projectBeanDocks){
            System.out.println(each);
            String guan = each.getProjectManager();
            if(guan!=null && !hashSet.contains(guan)) {
                hashSet.add(guan);
                Map<String, String> map = new HashMap<>();
                map.put("projectID", projectID);
                map.put("projectManager", guan);
                map.put("company", each.getCompany());
                map.put("introduce", each.getIntroduce());
                list.add(map);
            }
        }
        return RetResponse.makeOKRsp(list);
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
        try {
            int res = projectMapper.updateProject(projectState);
            int res2 = projectDockMapper.updateProjectDock(projectState);
            return RetResponse.makeOKRsp("完成"+projectID+"的"+process+"期成功");
        } catch (Exception e){
            return RetResponse.makeErrRsp("完成"+projectID+"的"+process+"期失败");
        }
    }

    @RequestMapping(value = "/searchCompanyAccount", method = RequestMethod.POST)
    public RetResult<List<ProjectBeanDock>> searchCompanyAccount(@RequestBody Map json) {

        String companyAccount = json.get("companyAccount").toString();

        List<ProjectBeanDock> projectBeanDocks;
        projectBeanDocks = projectDockMapper.getByCompanyAccount(companyAccount);
        return RetResponse.makeOKRsp(projectBeanDocks);
    }

    @RequestMapping(value = "/workCheck", method = RequestMethod.POST)
    public RetResult<String> workCheck(@RequestBody Map json) {
        String uuid = json.get("uuid").toString();
        int ifRead = (int)json.get("ifRead");
        try {
            projectMapper.updateProjectRead(uuid);
            return RetResponse.makeOKRsp("ok");
        }catch (Exception e){
            return RetResponse.makeErrRsp("不成功");
        }
    }

    @RequestMapping(value = "/finishAll", method = RequestMethod.POST)
    public RetResult<String> finishAll(@RequestBody Map json) {

        String projectID = json.get("projectID").toString();
        ProjectState projectState = new ProjectState();
        projectState.setState1("projectID");
        projectState.setStateValue1(projectID);
        List<ProjectBeanDock> projectBeanDocks =  projectDockMapper.getProjectDockByProjectID(projectID);
        if(projectBeanDocks.get(0).getProcess() != "0" && projectBeanDocks.get(0).getIfWork() == 0){
            return RetResponse.makeErrRsp("最后一期还未完成，无法完成整个项目");
        }
        int res = projectMapper.updateProject2(projectState);
        int res2 = projectDockMapper.updateProjectDock2(projectState);

        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/waitProject", method = RequestMethod.POST)
    public RetResult<Map> waitProject(@RequestBody Map json) {
        String createuser = json.get("createuser").toString();
        ProjectState projectState = new ProjectState();
        projectState = intoState(projectState, "2", "b", createuser);
        List<ProjectBean> projectBeans;
        try {
            projectBeans = projectMapper.getProjectByAll(projectState);
            HashSet<String> hashSet = new HashSet<>();
            for(ProjectBean each:projectBeans){
                String projectID = each.getProjectID();
                if(projectID != null)
                    hashSet.add(projectID);
            }
            Map result = new HashMap();
            result.put("ProjectID",hashSet);
            return RetResponse.makeOKRsp(result);
        } catch (Exception e){
            return RetResponse.makeErrRsp("错误");
        }
    }

}
