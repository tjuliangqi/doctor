package cn.tju.doctor.controller;


import cn.tju.doctor.dao.ProjectfundingMapper;
import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.daomain.*;
import cn.tju.doctor.utils.numberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projectFea")
public class ProjectFeaController {
    @Autowired
    ProjectfundingMapper projectfundingMapper;
    @Autowired
    UserMapper userMapper;
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
        int type2 = Integer.valueOf(type.substring(1,2));
        int type3 = Integer.valueOf(type.substring(2,3));
        int test = 0;
        int ifWork = 0;
        switch (type2){
            case 0:break;
            case 1:test = 1;break;
            case 2:test = 1;ifWork = 1;break;
            default:return RetResponse.makeErrRsp("type2 error");
        }
        switch (type1){
            case "0": result = projectfundingMapper.getProjectfundingListByApplytime(value,test,ifWork,type3);break;
            case "1": result = projectfundingMapper.getProjectfundingByprojectID(value,test,ifWork,type3);break;
            case "2": result.add(projectfundingMapper.getProjectfundingByNumber(value));break;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RetResult<String> add(@RequestBody Projectfunding projectfunding) {
        String number = numberUtils.getOrderNo();
        projectfunding.setNumber(number);
        projectfundingMapper.insertProjectfunding(projectfunding);
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public RetResult<String> verify(@RequestBody Projectfunding projectfunding)  {
        //RetResult retResult = new RetResult();
        projectfundingMapper.updateProjectfundingTest(projectfunding);
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/work", method = RequestMethod.POST)
    public RetResult<String> work(@RequestBody Projectfunding projectfunding)  {
        //RetResult retResult = new RetResult();
        projectfundingMapper.updateProjectfundingWork(projectfunding);
        return RetResponse.makeOKRsp("ok");
    }

}
