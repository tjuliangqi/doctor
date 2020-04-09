//package cn.tju.doctor.controller;
//
//import cn.tju.doctor.dao.UserMapper;
//import cn.tju.doctor.dao.UserfundingMapper;
//import cn.tju.doctor.daomain.RetResponse;
//import cn.tju.doctor.daomain.RetResult;
//import cn.tju.doctor.daomain.User;
//import cn.tju.doctor.daomain.Userfunding;
//import cn.tju.doctor.utils.numberUtils;
//import org.json.JSONException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static cn.tju.doctor.service.AskService.searchList;
//
//@RestController
//@RequestMapping("/userFea")
//public class UserFeaController {
//    @Autowired
//    UserfundingMapper userfundingMapper;
//    @Autowired
//    UserMapper userMapper;
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public RetResult<String> add(@RequestBody Userfunding userfunding) {
//        //RetResult retResult = new RetResult();
//        //这里扣钱变成云账户提现
//        User user = new User();
//        User go = new User();
//        if (userfunding.getOut() == 1){
//            List<User> list = userMapper.getUserByAuthorID(userfunding.getApplyID());
//            List<User> golist = userMapper.getUserByAuthorID(userfunding.getGo());
//            if (list.size() == 0){
//                return RetResponse.makeErrRsp("扣款账户不存在");
//            }
//            if (golist.size() == 0){
//                return RetResponse.makeErrRsp("目标账户不存在");
//            }
//            user = list.get(0);
//            go = golist.get(0);
//            if (user.getMoney() < userfunding.getMount()){
//                return RetResponse.makeErrRsp("账户余额不足");
//            }
//        }
//        String number = numberUtils.getOrderNo();
//        userfunding.setNumber(number);
//        try{
//            userfundingMapper.insertUserfunding(userfunding);
//        }catch (Exception e){
//            System.out.println("交易流水出错:" + e);
//            return RetResponse.makeErrRsp("交易流水出错");
//        }
//        user.setMoney(user.getMoney() - userfunding.getMount());
//        go.setMoney(go.getMoney() + userfunding.getMount());
//        try{
//            userMapper.updateUser(user);
//        }catch (Exception e){
//
//            System.out.println("扣款出错:" + e);
//            userfunding.setTest(2);
//            userfundingMapper.updateUserfundingTest(userfunding);
//            userfunding.setIfWork(2);
//            userfundingMapper.updateUserfundingWork(userfunding);
//            return RetResponse.makeErrRsp("扣款出错");
//        }
//        try{
//            userMapper.updateUser(go);
//        }catch (Exception e){
//            System.out.println("放款出错：" + e);
//            user.setMoney(user.getMoney() + userfunding.getMount());
//            userMapper.updateUser(user);
//            return RetResponse.makeErrRsp("放款出错");
//        }
//
//        return RetResponse.makeOKRsp("ok");
//    }
//
//    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
//    public RetResult<List<Userfunding>> searchList(@RequestBody Map<String,String> map){
//        List<Userfunding> result = new ArrayList<>();
//        if (map.keySet().size() == 0){
//            return RetResponse.makeErrRsp("未传参数");
//        }
//        for (String key : map.keySet()){
//            if (key.equals("source")){
//                result = userfundingMapper.getUserfundingBySource(map.get("source"));
//                break;
//            }
//            if (key.equals("authorID")){
//                result = userfundingMapper.getUserfundingByAuthorID(map.get("authorID"));
//                break;
//            }
//        }
//        return RetResponse.makeOKRsp(result);
//    }
//
//    @RequestMapping(value = "/verify", method = RequestMethod.POST)
//    public RetResult<String> verify(@RequestBody Userfunding userfunding)  {
//        //RetResult retResult = new RetResult();
//        userfundingMapper.updateUserfundingTest(userfunding);
//        return RetResponse.makeOKRsp("ok");
//    }
//
//    @RequestMapping(value = "/work", method = RequestMethod.POST)
//    public RetResult<String> work(@RequestBody Userfunding userfunding)  {
//        //RetResult retResult = new RetResult();
//        userfundingMapper.updateUserfundingWork(userfunding);
//        return RetResponse.makeOKRsp("ok");
//    }
//
//    @RequestMapping(value = "/search", method = RequestMethod.POST)
//    public RetResult<List<Userfunding>> search(@RequestBody Map<String,String> map)  {
//        List<Userfunding> result = new ArrayList<>();
//        String type = map.get("type");
//        String value = map.get("value");
//        String token = map.get("token");
//        int usertype = 0;
//        List userList = userMapper.getUserByToken(token);
//        if (userList.size() == 0){
//            return RetResponse.makeErrRsp("user error");
//        } else {
//            User user =(User) userList.get(0);
//            usertype = Integer.valueOf(user.getType());
//        }
//        String type1 = type.substring(0,1);
//        int type2 = Integer.valueOf(type.substring(1,2));
//        int type3 = Integer.valueOf(type.substring(2,3));
//        int test = 0;
//        int ifWork = 0;
//        switch (type2){
//            case 0:break;
//            case 1:test = 1;break;
//            case 2:test = 1;ifWork = 1;break;
//            default:return RetResponse.makeErrRsp("error");
//        }
//        switch (type1){
//            case "0": result = userfundingMapper.getUserfundingListByApplytime(value,test,ifWork,type3);break;
//            case "1": result = userfundingMapper.getUserfundingListByAuthorId(value,test,ifWork,type3);break;
//            case "2": result.add(userfundingMapper.getUserfundingByNumber(value));break;
//            default:return RetResponse.makeErrRsp("error");
//        }
//        if (usertype < 4){
//            for (Userfunding userfunding:result){
//                userfunding.setSourceAccount(null);
//                userfunding.setGoaccount(null);
//                userfunding.setTestuser(null);
//                userfunding.setWorkUser(null);
//            }
//        }
//        return RetResponse.makeOKRsp(result);
//    }
//}
