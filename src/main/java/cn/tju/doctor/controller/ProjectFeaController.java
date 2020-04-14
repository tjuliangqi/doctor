package cn.tju.doctor.controller;


import cn.tju.doctor.dao.*;
import cn.tju.doctor.daomain.*;
import cn.tju.doctor.service.ProjectFeaServer;
import cn.tju.doctor.utils.numberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/projectFea")
public class ProjectFeaController {
    @Autowired
    ProjectfundingMapper projectfundingMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProjectDockMapper projectDockMapper;
    @Autowired
    ProjectFeaServer projectFeaServer;
    @Autowired
    UserfundingMapper userfundingMapper;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<List<Projectfunding>> search(@RequestBody Map<String,String> map)  {
        List<Projectfunding> result = new ArrayList<>();
        String type = map.get("type");
        String value = map.get("value");
        String token = map.get("token");
        int usertype;
        List userList = userMapper.getUserByToken(token);
        if (userList.size() == 0){
            return RetResponse.makeErrRsp("不合法用户");
        } else {
            User user =(User) userList.get(0);
            usertype = Integer.valueOf(user.getType());
        }
        String type1 = type.substring(0,1);
        String type2 = type.substring(1,2);
        int type3 = Integer.valueOf(type.substring(2,3));
        int test = 0;
        int ifWork = 0;
        switch (type2){
            case "0":break;
            case "1":test = 1;break;
            case "2":test = 1;ifWork = 1;break;
            default:return RetResponse.makeErrRsp("type2 error");
        }
        switch (type1){
            case "0": result = projectfundingMapper.getProjectfundingListByApplytime(value,test,ifWork,type3);break;
            case "1": result = projectfundingMapper.getProjectfundingByprojectID(value,test,ifWork,type3);break;
            case "2": result.add(projectfundingMapper.getProjectfundingByNumber(value).get(0));break;
            default:return RetResponse.makeErrRsp("type1 error");
        }
        if (usertype < 4){
            for (Projectfunding projectfunding:result){
                projectfunding.setSourceAccount(null);
                projectfunding.setGoaccount(null);
                projectfunding.setTestuser(null);
                projectfunding.setWorkUser(null);
            }
        }
        return RetResponse.makeOKRsp(result);
    }

    /*@RequestMapping(value = "/add", method = RequestMethod.POST)
    public RetResult<String> add(@RequestBody Projectfunding projectfunding) {
        ProjectBeanDock user = new ProjectBeanDock();
        User go = new User();
        if (projectfunding.getOut() == 1){
            List<ProjectBeanDock> list = projectDockMapper.getProjectDockByProjectID(projectfunding.getProjectID());
            List<User> golist = userMapper.getUserByAuthorID(projectfunding.getGo());
            if (list.size() == 0){
                return RetResponse.makeErrRsp("扣款账户不存在");
            }
            if (golist.size() == 0){
                return RetResponse.makeErrRsp("目标账户不存在");
            }
            user = list.get(0);
            go = golist.get(0);
            if (user.getMount() < projectfunding.getMount()){
                return RetResponse.makeErrRsp("账户余额不足");
            }
        }
        String number = numberUtils.getOrderNo();
        projectfunding.setNumber(number);
        try{
            projectfundingMapper.insertProjectfunding(projectfunding);
        }catch (Exception e){
            System.out.println("交易流水出错:" + e);
            return RetResponse.makeErrRsp("交易流水出错");
        }
        user.setMount(user.getMount() - projectfunding.getMount());
        go.setMoney(go.getMoney() + projectfunding.getMount());
        try{
            projectDockMapper.updateByProjectID2(user.getProjectID(),null,user.getMount());
        }catch (Exception e){
            System.out.println("扣款出错:" + e);
            projectfunding.setTest(2);
            projectfundingMapper.updateProjectfundingTest(projectfunding);
            projectfunding.setIfWork(2);
            projectfundingMapper.updateProjectfundingWork(projectfunding);
            return RetResponse.makeErrRsp("扣款出错");
        }
        try{
            userMapper.updateUser(go);
        }catch (Exception e){
            System.out.println("放款出错：" + e);
            return RetResponse.makeErrRsp("放款出错");
        }

        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/recall", method = RequestMethod.POST)
    @Transactional(rollbackFor=Exception.class)
    public RetResult<String> recall(@RequestParam String number) {
        Projectfunding projectfunding = projectfundingMapper.getProjectfundingByNumber(number);
        String userID = projectfunding.getGo();
        String projectID = projectfunding.getProjectID();
        Double mount = projectfunding.getMount();
        List<User> users = userMapper.getUserByAuthorID(userID);
        List<ProjectBeanDock> projectBeanDocks = projectDockMapper.getProjectDockByProjectID(projectID);
        User user = new User();
        ProjectBeanDock projectBeanDock = new ProjectBeanDock();
        if (users.size()>0){
            user = users.get(0);
        }else {
            RetResponse.makeErrRsp("用户不存在");
        }
        if (projectBeanDocks.size()>0){
            projectBeanDock = projectBeanDocks.get(0);
        }else {
            RetResponse.makeErrRsp("管理员账户不存在");
        }
        user.setMoney(user.getMoney()-mount);
        projectBeanDock.setMount(projectBeanDock.getMount()+mount);
        Projectfunding projectfunding1 = new Projectfunding();
        String uuid = numberUtils.getOrderNo();
        projectfunding1.setNumber(uuid);
        projectfunding1.setProjectID(projectID);
        projectfunding1.setIn(1);
        projectfunding1.setGo(userID);
        projectfunding1.setRecord("撤销操作");
        try{
            projectfundingMapper.insertProjectfunding(projectfunding1);
        }catch (Exception e){
            System.out.println("插入projectfunding出错"+e);
            return RetResponse.makeErrRsp("插入projectfunding出错");
        }

        try{
            userMapper.updateUser(user);
            projectDockMapper.updateByProjectID2(projectBeanDock.getProjectID(),null,projectBeanDock.getMount());
        }catch (Exception e){
            System.out.println("撤销出错：" + e);
            projectfunding.setTest(2);
            projectfundingMapper.updateProjectfundingTest(projectfunding1);
            projectfunding.setIfWork(2);
            projectfundingMapper.updateProjectfundingWork(projectfunding1);
            return RetResponse.makeErrRsp("撤销出错");
        }





    }*/


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RetResult<String> add(@RequestBody Projectfunding projectfunding) {
        ProjectBeanDock projectBeanDock;
        User user;
        switch (projectfunding.getType()){
            case 1:projectfunding.setProjectID("c42b26cb8dc544ad8b1cc034df6fc09e");break;
            case 2:break;
            case 3:projectfunding.setProjectID("c42b26cb8dc544ad8b1cc034df6fc09e");break;
            case 4:projectfunding.setProjectID("c42b26cb8dc544ad8b1cc034df6fc09e");break;
            default:return RetResponse.makeErrRsp("type错误");
        }
        if (projectfunding.getOut() != 1){
            return RetResponse.makeErrRsp("未设置标志位");
        }
        List<ProjectBeanDock> list = projectDockMapper.getProjectDockByProjectID(projectfunding.getProjectID());
        List<User> golist = userMapper.getUserByUsername(projectfunding.getGo());
        if (list.size() == 0){
            return RetResponse.makeErrRsp("管理员账户不存在");
        }
        if (golist.size() == 0){
            return RetResponse.makeErrRsp("用户账户不存在");
        }
        projectBeanDock = list.get(0);
        user = golist.get(0);
        if (projectBeanDock.getMount() < projectfunding.getMount()){
            return RetResponse.makeErrRsp("账户余额不足");
        }
        String number = numberUtils.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        projectfunding.setNumber(number);
        projectfunding.setTest(1);
        projectfunding.setIfWork(0);
        projectfunding.setApplyTime(time);
        try{
            projectfundingMapper.insertProjectfunding(projectfunding);
        }catch (Exception e){
            System.out.println("交易流水出错:" + e);
            return RetResponse.makeErrRsp("交易流水出错");
        }
        try{
            projectFeaServer.add(projectBeanDock,user,projectfunding,true);
        }catch (Exception e){
            System.out.println("扣款出错:" + e);
            projectfunding.setTest(0);
            projectfundingMapper.updateProjectfundingTest(projectfunding);
            projectfunding.setIfWork(0);
            projectfundingMapper.updateProjectfundingWork(projectfunding);
            return RetResponse.makeErrRsp("扣款出错");
        }
        return RetResponse.makeOKRsp("ok");

    }

    @RequestMapping(value = "/addManager", method = RequestMethod.POST)
    public RetResult<String> addManager(@RequestBody Map<String,String> map) {
        String source = map.get("source");
        String go = map.get("go");
        double mount = Double.valueOf(map.get("mount"));
        User from;
        User to;
        List<User> list1 = userMapper.getUserByUsername(source);
        List<User> list2 = userMapper.getUserByUsername(go);
        if (list1.size()==0) return RetResponse.makeErrRsp("公司账户不存在");
        if (list2.size()==0) return RetResponse.makeErrRsp("管理员账户不存在");
        from = list1.get(0);
        to =list2.get(0);
        if (from.getMoney() < mount) return RetResponse.makeErrRsp("公司账户余额不足");
        from.setMoney(from.getMoney()-mount);
        Userfunding userfunding = new Userfunding();
        String testUser = userMapper.getUserByCompany(from.getCompany(),"3").get(0).getUsername();
        String number = numberUtils.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        userfunding.setNumber(number);
        userfunding.setAuthorID(source);
        userfunding.setSource(source);
        userfunding.setGo(go);
        userfunding.setMount(mount);
        userfunding.setTest(0);
        userfunding.setIfWork(0);
        userfunding.setApplyTime(time);
        userfunding.setTestuser(testUser);
        //公司给管理员打钱
        userfunding.setType(1);
        try{
            projectFeaServer.cTOm(from,userfunding);
        } catch (Exception e) {
            return RetResponse.makeErrRsp("扣款失败");
        }
        return RetResponse.makeOKRsp("ok");
    }


    @RequestMapping(value = "/recall", method = RequestMethod.POST)
    public RetResult<String> recall(@RequestBody Map<String,String> map) {
        String number = map.get("number");
        Projectfunding projectfunding = projectfundingMapper.getProjectfundingByNumber(number).get(0);
        //添加撤销判断
        if (projectfunding.getRecord() != null && projectfunding.getRecord().equals("已撤销")){
            return RetResponse.makeErrRsp("重复撤销");
        }
        //否则添加已撤销
        projectfunding.setRecord("已撤销");
        String userID = projectfunding.getGo();
        String projectID = projectfunding.getProjectID();
        Double mount = projectfunding.getMount();
        List<User> users = userMapper.getUserByUsername(userID);
        List<ProjectBeanDock> projectBeanDocks = projectDockMapper.getProjectDockByProjectID(projectID);
        ProjectBeanDock projectBeanDock = new ProjectBeanDock();
        if (users.size()==0){
            return RetResponse.makeErrRsp("用户不存在");
        }
        User user = users.get(0);
        if (user.getMoney() < mount){
            return RetResponse.makeErrRsp("用户已提现无法撤销");
        }
        if (projectBeanDocks.size()>0){
            projectBeanDock = projectBeanDocks.get(0);
        }else {
            return RetResponse.makeErrRsp("管理员账户不存在");
        }
        Projectfunding projectfunding1 = new Projectfunding();
        String uuid = numberUtils.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        projectfunding1.setNumber(uuid);
        projectfunding1.setProjectID(projectID);
        projectfunding1.setIn(1);
        projectfunding1.setGo(userID);
        projectfunding1.setIfWork(1);
        projectfunding1.setTest(1);
        projectfunding1.setType(projectfunding.getType());
        projectfunding1.setMount(projectfunding.getMount());
        projectfunding1.setRecord("撤销操作");
        projectfunding1.setApplyTime(time);
        try{
            projectfundingMapper.insertProjectfunding(projectfunding1);
        }catch (Exception e){
            System.out.println("插入projectfunding出错"+e);
            return RetResponse.makeErrRsp("插入projectfunding出错");
        }
        try{
            projectFeaServer.add(projectBeanDock,user,projectfunding,false);
        }catch (Exception e){
            System.out.println("撤销出错:" + e);
            projectfunding.setTest(1);
            projectfundingMapper.updateProjectfundingTest(projectfunding);
            projectfunding.setIfWork(2);
            projectfundingMapper.updateProjectfundingWork(projectfunding);
            return RetResponse.makeErrRsp("撤销出错");
        }
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
    public RetResult<List<Projectfunding>> searchList(@RequestBody Map<String,String> map){
        List<Projectfunding> result = new ArrayList<>();
        if (map.keySet().size() == 0){
            return RetResponse.makeErrRsp("未传参数");
        }
        for (String key : map.keySet()){
            if (key.equals("source")){
                result = projectfundingMapper.getProjectfundingBySource(map.get("source"));
                break;
            }
        }

        return RetResponse.makeOKRsp(result);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public RetResult<String> verify(@RequestBody Projectfunding projectfunding)  {
        //RetResult retResult = new RetResult();
        projectfundingMapper.updateProjectfundingTest(projectfunding);
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/verifyUser", method = RequestMethod.POST)
    public RetResult<String> verifyUser(@RequestBody Map<String,String> map)  {
        String number = map.get("number");
        int test = Integer.valueOf(map.get("test"));
        String testRecord =map.get("testRecord");
        String testtime = map.get("testtime");
        Userfunding userfunding = userfundingMapper.getUserfundingByNumber(number);
        userfunding.setTestRecord(testRecord);
        userfunding.setTest(test);
        userfunding.setTesttime(testtime);
        User from = userMapper.getUserByUsername(userfunding.getApplyID()).get(0);
        User to = userMapper.getUserByUsername(userfunding.getGo()).get(0);
        try {
            //转账
            projectFeaServer.money(from,to,userfunding);
        }catch (Exception e){
            System.out.println(e);
            return RetResponse.makeErrRsp(e.getMessage());
        }
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/work", method = RequestMethod.POST)
    public RetResult<String> work(@RequestBody Projectfunding projectfunding)  {
        //RetResult retResult = new RetResult();
        projectfundingMapper.updateProjectfundingWork(projectfunding);
        return RetResponse.makeOKRsp("ok");
    }

}
