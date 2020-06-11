package cn.tju.doctor.controller;

import cn.tju.doctor.Config;
import cn.tju.doctor.dao.*;
import cn.tju.doctor.daomain.*;
import cn.tju.doctor.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.constant.XmlData;
import yzhpay.sdk.dto.response.Response;
import yzhpay.sdk.pay.order.BankCardOrder;
import yzhpay.sdk.pay.verify.FourFactorVerify;
import yzhpay.sdk.util.HttpUtil;
import yzhpay.sdk.util.JsonUtil;
import yzhpay.sdk.util.Property;
import yzhpay.sdk.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static cn.tju.doctor.utils.ToBuildersUtils.intoState;
import static cn.tju.doctor.utils.fileUtil.upload;

@RestController

@RequestMapping("/project")
public class projectController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectDockMapper projectDockMapper;
    @Autowired
    ProjectManagementMapper projectManagementMapper;
    @Autowired
    EachFundingMapper eachFundingMapper;
    @Autowired
    UserfundingMapper userfundingMapper;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<List> search(@RequestBody Map map) {
        // 2b+公司用户id，查公司有哪些项目，管理员5b， 普通用户3b
        // 1b+项目id，查项目有哪些期数
        // 11+项目id，查这个项目的第一期
        System.out.println("查询组合：" + map.get("type").toString());
        System.out.println("查询值：" + map.get("value").toString());

        String type = map.get("type").toString();
        String value = map.get("value").toString();
        String firstState = type.substring(0, 1);
        String secondState = type.substring(1, 2);

        ProjectState projectState = new ProjectState();
        projectState = intoState(projectState, firstState, secondState, value);
        System.out.println(projectState);

        List<ProjectBean> projectBeans;
        List<ProjectBeanDock> projectBeanDocks;

        if (firstState.equals("a")) {
            String userID = value.split("\\+")[0];
            String projectID = value.split("\\+")[1];
            projectBeans = projectMapper.getProjectByUserProjectID(userID, projectID);
        }
        // 2b+公司用户id，查公司有哪些项目，管理员5b
        else if (type.equals("2b") || type.equals("5b")) {
            projectBeanDocks = projectDockMapper.getALLPJList(type, value);
            return RetResponse.makeOKRsp(projectBeanDocks);
        } else {
            projectBeans = projectMapper.getProjectByAll(projectState);
            //删除0期
            if (projectBeans.size() > 1) {
                for (int i = 0; i < projectBeans.size(); i++) {
                    if ("0".equals(projectBeans.get(i).getProcess()))
                        projectBeans.remove(i);
                }
            }
            // 根据 ProjectID 去重
            if (type.equals("0b") || type.equals("3b")) {
                Collections.reverse(projectBeans); // 先反向排序
                List<ProjectBean> unique = projectBeans.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ProjectBean::getProjectID))), ArrayList::new)
                );
                //unique.forEach(p -> System.out.println(p));
                projectBeans = unique;
            }
            // 根据 Process 去重
            if (type.equals("1b")) {
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
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String projectID = UUID.randomUUID().toString().replace("-", "");
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
        String uuid = UUID.randomUUID().toString().replace("-", "");
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

        } catch (Exception e) {
            return RetResponse.makeErrRsp("项目还未创建，无法指派");
        }
        List<ProjectBeanDock> projectBeanDocks = projectDockMapper.getProjectDockByProjectID(projectID);
        String process2 = projectBeanDocks.get(0).getProcess();
        System.out.println(projectBeanDocks.get(0).getProcess());
        System.out.println(projectBeanDocks.get(0).getIfWork());
        if (!"0".equals(projectBeanDocks.get(0).getProcess()) && projectBeanDocks.get(0).getIfWork() == 0) {
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
        result.put("url", "");
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
        for (ProjectBean each : projectBeans) {
            System.out.println(each);
            String acceptuser = each.getAcceptuser();
            if (acceptuser != null && !hashSet.contains(acceptuser)) {
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
        for (ProjectBeanDock each : projectBeanDocks) {
            System.out.println(each);
            String guan = each.getProjectManager();
            if (guan != null && !hashSet.contains(guan)) {
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
            return RetResponse.makeOKRsp("完成" + projectID + "的" + process + "期成功");
        } catch (Exception e) {
            return RetResponse.makeErrRsp("完成" + projectID + "的" + process + "期失败");
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
        int ifRead = (int) json.get("ifRead");
        try {
            projectMapper.updateProjectRead(uuid);
            return RetResponse.makeOKRsp("ok");
        } catch (Exception e) {
            return RetResponse.makeErrRsp("不成功");
        }
    }

    @RequestMapping(value = "/finishAll", method = RequestMethod.POST)
    public RetResult<String> finishAll(@RequestBody Map json) {

        String projectID = json.get("projectID").toString();
        ProjectState projectState = new ProjectState();
        projectState.setState1("projectID");
        projectState.setStateValue1(projectID);
        List<ProjectBeanDock> projectBeanDocks = projectDockMapper.getProjectDockByProjectID(projectID);
        if (projectBeanDocks.get(0).getProcess() != "0" && projectBeanDocks.get(0).getIfWork() == 0) {
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
            for (ProjectBean each : projectBeans) {
                String projectID = each.getProjectID();
                if (projectID != null)
                    hashSet.add(projectID);
            }
            Map result = new HashMap();
            result.put("ProjectID", hashSet);
            return RetResponse.makeOKRsp(result);
        } catch (Exception e) {
            return RetResponse.makeErrRsp("错误");
        }
    }

    // 业务申请接口
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public RetResult<String> apply(@RequestParam("data") MultipartFile file,
                                   ProjectManagement projectManagement) {

        String dataURL = upload(file, projectManagement.getCreatuser());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
        String creattime = dateFormat.format(now);
        List<User> lists = userMapper.getUserByUsername(projectManagement.getCreatuser());
        if (lists.size() < 1)
            return RetResponse.makeErrRsp("查询不到该creatuser");
        String company = lists.get(0).getCompany();
        projectManagement.setUuid(uuid);
        projectManagement.setDataURL(dataURL);
        projectManagement.setCreattime(creattime);
        projectManagement.setCompany(company);
        System.out.println(lists.get(0).getPercent());
        projectManagement.setPercent(lists.get(0).getPercent());
        projectManagement.setMoney(projectManagement.getMount() * (1 - projectManagement.getPercent()));
        int flag = projectManagementMapper.insertProjectManagement(projectManagement);
        if (flag == 1) {
            return RetResponse.makeOKRsp("ok");
        } else {
            return RetResponse.makeErrRsp("业务申请失败");
        }
    }

    //用户查询已申请业务接口
    @RequestMapping(value = "/searchHistoryApp", method = RequestMethod.POST)
    public RetResult<List> searchHistoryApp(@RequestBody Map json) {
        String createuser = json.get("createuser").toString();
        String test = json.get("test").toString();
        if (test.equals("0")) {
            List<ProjectManagement> list = projectManagementMapper.getByUserTest0(createuser);
            if (list.size() < 1)
                return RetResponse.makeErrRsp("查询不到该creatuser的未审核");
            for (ProjectManagement each : list) {
                each.setPrevious(0.0);
            }
            return RetResponse.makeOKRsp(list);
        } else {
            List<ProjectManagement> list = projectManagementMapper.getByUserTest1(createuser);
            if (list.size() < 1)
                return RetResponse.makeErrRsp("查询不到该creatuser的已审核");

            for (ProjectManagement each : list) {
                String uuid = each.getUuid();
                List<Eachfunding> list1 = eachFundingMapper.selectEachFundingByUUIDtest(uuid);
                double previous = 0.0;
                for (Eachfunding eacheach : list1)
                    previous = previous + eacheach.getMount();
                each.setPrevious(previous);
            }
            return RetResponse.makeOKRsp(list);
        }
    }

    //业务申请上款接口
    @RequestMapping(value = "/addApply", method = RequestMethod.POST)
    public RetResult<Eachfunding> addApply(@RequestBody Map<String, String> json) {
        String createuser = json.get("createuser").toString();
        String uuid = json.get("uuid").toString();
        double mount = Double.valueOf(json.get("mount"));
        ProjectManagement old = projectManagementMapper.getByUuid(uuid);
        List<Eachfunding> newlist = eachFundingMapper.selectEachFundingByUUIDtest(uuid);
        double previous = 0.0;
        for (Eachfunding eacheach : newlist)
            previous = previous + eacheach.getMount();
        if ((previous + mount) > old.getMount())
            return RetResponse.makeErrRsp("加上传来的mount大于老表mount");
        else {
            Eachfunding eachfunding = new Eachfunding();
            eachfunding.setApplymount(old.getMount());
            eachfunding.setMount(mount);
            eachfunding.setCreatuser(createuser);
            eachfunding.setUuid(uuid);
            String number = UUID.randomUUID().toString().replace("-", "");
            eachfunding.setNumber(number);
            double percent = old.getPercent();
            eachfunding.setPercent(percent);
            eachfunding.setTest(0);
            int res = eachFundingMapper.insertEachFunding(eachfunding);
            if (res == 1) {
                return RetResponse.makeOKRsp(eachfunding);
            } else {
                return RetResponse.makeErrRsp("插入失败");
            }
        }

    }


    @RequestMapping(value = "/searchUnverify", method = RequestMethod.POST)
    public RetResult<List> searchUnverify(@RequestBody Map json) {
        String testResult = json.get("testResult").toString();

        List<ProjectManagement> byTest = projectManagementMapper.getByTest(Integer.parseInt(testResult));

        return RetResponse.makeOKRsp(byTest);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public RetResult<String> verify(@RequestBody Map json) {
        String uuid = json.get("uuid").toString();
        String testResult = json.get("testResult").toString();
        ProjectManagement projectManagement = new ProjectManagement();

        projectManagement.setTest(Integer.parseInt(testResult));
        projectManagement.setUuid(uuid);

        if (projectManagement.getTest() == -1) {
            projectManagementMapper.deleteByUUID(uuid);
            return RetResponse.makeOKRsp("delete ok");
        }

        int i = projectManagementMapper.updateTest(projectManagement);
        if (i == 1) {
            return RetResponse.makeOKRsp("ok");
        } else {
            return RetResponse.makeErrRsp("更新失败");
        }
    }


    @RequestMapping(value = "/searchUnverify1", method = RequestMethod.POST)
    public RetResult<List<Map<String, String>>> searchUnverify1(@RequestBody Map<String, String> json) {
//        String workuser = json.get("workuser");

        List<Eachfunding> eachfundings = eachFundingMapper.selectEachFundingByTest();
        if (eachfundings.size() == 0) {
            return RetResponse.makeErrRsp("查询记录不存在");
        }
        Set<Eachfunding> set = new HashSet<>();

        for (Eachfunding eachfunding : eachfundings) {
            set.add(eachfunding);
        }
        List<Map<String, String>> result = new ArrayList<>();
        for (Eachfunding each : set) {
            Map<String, String> res = new HashMap<>();
            ProjectManagement projectManagement = projectManagementMapper.getByUuid(each.getUuid());
//            List<Eachfunding> eachfundings1 = eachFundingMapper.selectEachFundingByWorkUser(workuser);
            Eachfunding eachfunding = eachFundingMapper.selectEachFundingByNumber(each.getNumber());
            List<Eachfunding> verifys = eachFundingMapper.count(each.getUuid(), 0);
            double verifyMount = 0;
            for (Eachfunding verify : verifys) {
                verifyMount = verifyMount + verify.getMount();
            }
            List<Eachfunding> previouslist = eachFundingMapper.count(each.getUuid(), 1);
            double previous = 0;
            for (Eachfunding pre : previouslist) {
                previous = previous + pre.getMount();
            }
            res.put("createuser", projectManagement.getCreatuser());
            res.put("uuid", each.getUuid());
            res.put("mount", String.valueOf(eachfunding.getMount()));
            res.put("money", String.valueOf(projectManagement.getMoney()));
            res.put("name", projectManagement.getName());
            res.put("company", projectManagement.getCompany());
            res.put("dataURL", projectManagement.getDataURL());
            res.put("ifWork", String.valueOf(projectManagement.getIfWork()));
            res.put("test", String.valueOf(eachfunding.getTest()));
            res.put("createtime", projectManagement.getCreattime());
            res.put("testtime", projectManagement.getTesttime());
            res.put("worktime", projectManagement.getWorktime());
            res.put("percent", String.valueOf(projectManagement.getPercent()));
            res.put("previous", String.valueOf(previous));
            res.put("verifyMount", String.valueOf(verifyMount));
            res.put("actureMount", String.valueOf(verifyMount * (1 - projectManagement.getPercent())));
            result.add(res);


        }
        return RetResponse.makeOKRsp(result);
    }


    @RequestMapping(value = "/verify1", method = RequestMethod.POST)
    public RetResult<String> verify1(@RequestBody Map json) {
        String number = json.get("number").toString();
        String testResult = json.get("testResult").toString();

        List<Eachfunding> eachfundings = eachFundingMapper.count(number, 0);
        for (Eachfunding eachfunding : eachfundings) {
            eachfunding.setTest(Integer.parseInt(testResult));
            eachFundingMapper.updateEachFunding(eachfunding);//修改eachfunding的test
            User user = userMapper.getUserByUsername(eachfunding.getCreatuser()).get(0);
            user.setMoney(user.getMoney() + eachfunding.getMount() * (1 - eachfunding.getPercent()));
            userMapper.updateUser(user);
            String uuid = eachfunding.getUuid();
            Double applymount = eachfunding.getApplymount();

            List<Eachfunding> eachfundingsList = eachFundingMapper.selectEachFundingByUUID(uuid);
            Double allMount = 0.0;
            for (Eachfunding each : eachfundingsList) {
                allMount += each.getMount();
            }
            if (applymount.equals(allMount)) {   //判断mount加起来是否等于applymount
                ProjectManagement projectManagement = new ProjectManagement();
                projectManagement.setUuid(uuid);
                projectManagement.setIfWork(1); //修改旧表的ifwork为1
                projectManagementMapper.updateWork(projectManagement);
            }
        }


        //将钱给用户


        //修改新表的ifwork


        return RetResponse.makeOKRsp("ok");
//        ProjectManagement projectManagement = projectManagementMapper.getByUuid(uuid);
//        projectManagement.setIfWork(Integer.parseInt(testResult));
//        projectManagement.setUuid(uuid);
//        int i = projectManagementMapper.updateWork(projectManagement);
//        if(i==1){
//            if (testResult.equals("1")){
//                User user = userMapper.getUserByUsername(projectManagement.getCreatuser()).get(0);
//                user.setMoney(user.getMoney()+projectManagement.getMoney());
//                userMapper.updateUser(user);
//            }
//            return RetResponse.makeOKRsp("ok");
//        }else {
//            return RetResponse.makeErrRsp("更新失败");
//        }
    }


    // 银行四要素验证，传入要验证的user
    public RetResult<String> fourFactorVerifyConfirm(User user) throws Exception {
        FourFactorVerify fourFactorVerify = new FourFactorVerify();
        fourFactorVerify.setRealName(user.getName());
        fourFactorVerify.setIdNumber(user.getActureID());
        fourFactorVerify.setCardNumber(user.getBankID());
        fourFactorVerify.setPhoneNumber(user.getPhone());
        Map<String, Object> result = null;
        try {
            result = HttpUtil.post(fourFactorVerify.assembleRequest(), Property.getUrl(ConfigPath.YZH_FOUR_FACTOR_BANK_CARD_VERIFY));
        } catch (Exception e) {
            return RetResponse.makeErrRsp("验证失败");
        }

        String code = (String) result.get("code");
        if (code != "0000") {
            return RetResponse.makeErrRsp("目标账户不存在");
        }

        return RetResponse.makeOKRsp("ok");
    }

    //提现
    public RetResult<String> bankOrder(User user, String payMoney) throws Exception {
        BankCardOrder bankOrder = new BankCardOrder();
        bankOrder.setRealName(user.getName());
        bankOrder.setCardNumber(user.getBankID());
        bankOrder.setPhoneNumber(user.getPhone());
        bankOrder.setIdNumber(user.getActureID());
        bankOrder.setPayMoney(payMoney);
        bankOrder.setPayRemark("提现");
        try {
            Map<String, Object> payResult = HttpUtil.post(bankOrder.assembleRequest(), Property.getUrl(ConfigPath.YZH_BANK_CARD_REAL_TIME_ORDER));
            Response response = null;
            Map<String, Object> result = null;
            try {
                if ("200".equals(StringUtils.trim(result.get(XmlData.STATUSCODE)))) {
                    response = JsonUtil.fromJson(StringUtils.trim(payResult.get(XmlData.DATA)), Response.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (response.getCode().equals("0000")) {
                return RetResponse.makeOKRsp("ok");
            } else {
                return RetResponse.makeErrRsp("提现失败");
            }
        } catch (Exception e) {

            return RetResponse.makeErrRsp("提现失败，网络错误");
        }
    }

    // 每天个人转账记录
    // 在userFunding表查applyTime=当天，type=2是团队到个人，type=3是公司到个人
    @RequestMapping(value = "/todayRecord", method = RequestMethod.POST)
    public RetResult<Map<String, Object>> todayRecord() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        String today = dateFormat.format(now);
        System.out.println(today);
        List<Userfunding> eachfunding2 = userfundingMapper.selectTodayFunding(today, 2);
        List<Userfunding> eachfunding3 = userfundingMapper.selectTodayFunding(today, 3);
        Map<String, Object> map = new HashMap<>();
        map.put("teamToPerson",eachfunding2);
        map.put("companyToPerson",eachfunding3);

        String[] strings = {"number", "authorID", "mount", "rest", "in", "out", "test", "testtime",
                "testuser", "ifWork", "workTime", "workUser", "record", "source", "sourceAccount",
                "go", "goaccount", "applyID", "applyTime", "testRecord", "workRecord", "type",
                "moneyType"};
        List<String> fieldList= new ArrayList<>(Arrays.asList(strings));
        CSVUtils csvUtils=new CSVUtils();
        List<List<String>> listList=new ArrayList<>();
        listList.add(fieldList);
        List<String> list=null;
        for(Userfunding userfunding:eachfunding3){
            list=new ArrayList<>();//一个List为一行
            list.add(userfunding.getNumber());
            list.add(userfunding.getAuthorID());
            list.add(String.valueOf(userfunding.getMount()));
            list.add(String.valueOf(userfunding.getRest()));
            list.add(String.valueOf(userfunding.getIn()));
            list.add(String.valueOf(userfunding.getOut()));
            list.add(String.valueOf(userfunding.getTest()));
            list.add(userfunding.getTesttime());
            list.add(userfunding.getTestuser());
            list.add(String.valueOf(userfunding.getIfWork()));
            list.add(userfunding.getWorkTime());
            list.add(userfunding.getWorkUser());
            list.add(userfunding.getRecord());
            list.add(userfunding.getSource());
            list.add(userfunding.getSourceAccount());
            list.add(userfunding.getGo());
            list.add(userfunding.getGoaccount());
            list.add(userfunding.getApplyID());
            list.add(userfunding.getApplyTime());
            list.add(userfunding.getTestRecord());
            list.add(userfunding.getWorkRecord());
            list.add(String.valueOf(userfunding.getType()));
            list.add(String.valueOf(userfunding.getMoneyType()));
            listList.add(list);
        }

        File file = csvUtils.createCSVFile(listList,Config.UPLOAD_DIR + File.separator + File.separator ,"csv");
        System.out.println(file.getPath());
        map.put("companyToPersonCSV","http://39.96.65.14/doctorfile/upload/"  + file.getName());
        return RetResponse.makeOKRsp(map);


    }

}
