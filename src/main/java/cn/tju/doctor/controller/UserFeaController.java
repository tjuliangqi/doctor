package cn.tju.doctor.controller;

import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.dao.UserfundingMapper;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import cn.tju.doctor.daomain.User;
import cn.tju.doctor.daomain.Userfunding;
import cn.tju.doctor.utils.numberUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.tju.doctor.service.AskService.searchList;

@RestController
@RequestMapping("/userFea")
public class UserFeaController {
    @Autowired
    UserfundingMapper userfundingMapper;
    @Autowired
    UserMapper userMapper;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RetResult<String> add(@RequestBody Userfunding userfunding) {
        //RetResult retResult = new RetResult();
        String number = numberUtils.getOrderNo();
        userfunding.setNumber(number);
        userfundingMapper.insertUserfunding(userfunding);
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public RetResult<String> verify(@RequestBody Userfunding userfunding)  {
        //RetResult retResult = new RetResult();
        userfundingMapper.updateUserfundingTest(userfunding);
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/work", method = RequestMethod.POST)
    public RetResult<String> work(@RequestBody Userfunding userfunding)  {
        //RetResult retResult = new RetResult();
        userfundingMapper.updateUserfundingWork(userfunding);
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<List<Userfunding>> search(@RequestBody Map<String,String> map)  {
        List<Userfunding> result = new ArrayList<>();
        String type = map.get("type");
        String value = map.get("value");
        String token = map.get("token");
        int usertype = 0;
        List userList = userMapper.getUserByToken(token);
        if (userList.size() == 0){
            return RetResponse.makeErrRsp("user error");
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
            default:return RetResponse.makeErrRsp("error");
        }
        switch (type1){
            case "0": result = userfundingMapper.getUserfundingListByApplytime(value,test,ifWork,type3);break;
            case "1": result = userfundingMapper.getUserfundingListByAuthorId(value,test,ifWork,type3);break;
            case "2": result.add(userfundingMapper.getUserfundingByNumber(value));break;
            default:return RetResponse.makeErrRsp("error");
        }
        if (usertype < 4){
            for (Userfunding userfunding:result){
                userfunding.setSourceAccount(null);
                userfunding.setGoaccount(null);
                userfunding.setTestuser(null);
                userfunding.setWorkUser(null);
            }
        }
        return RetResponse.makeOKRsp(result);
    }
}
