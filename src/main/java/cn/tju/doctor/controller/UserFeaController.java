package cn.tju.doctor.controller;

import cn.tju.doctor.dao.RecordMapper;
import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.dao.UserfundingMapper;
import cn.tju.doctor.daomain.*;
import cn.tju.doctor.service.UserFeaServer;
import cn.tju.doctor.utils.numberUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.tomcat.jni.Thread;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.constant.XmlData;
import yzhpay.sdk.dto.response.Response;
import yzhpay.sdk.pay.order.BankCardOrder;
import yzhpay.sdk.pay.verify.FourFactorVerify;
import yzhpay.sdk.util.HttpUtil;
import yzhpay.sdk.util.JsonUtil;
import yzhpay.sdk.util.Property;
import yzhpay.sdk.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
//这个是谁的？？？
//import static cn.tju.doctor.service.AskService.searchList;

@RestController
@RequestMapping("/userFea")
public class UserFeaController {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    UserfundingMapper userfundingMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserFeaServer userFeaServer;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RetResult<String> add(@RequestBody Userfunding userfunding) {
        //RetResult retResult = new RetResult();
        //这里扣钱变成云账户提现

        User user = new User();
        List<User> list = userMapper.getUserByUsername(userfunding.getApplyID());
        if (list.size() == 0) {
            return RetResponse.makeErrRsp("扣款账户不存在");
        }
        user = list.get(0);
        String testUser = userMapper.getUserByCompany(user.getCompany(), "3").get(0).getUsername();
        if (userfunding.getOut() == 1) {

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

            userfunding.setGo(user.getBankID());
            if (user.getMoney() < userfunding.getMount() && user.getMoney() > 0) {
                return RetResponse.makeErrRsp("账户余额不足");
            }
        }
        String number = numberUtils.getOrderNo();
        userfunding.setNumber(number);
        userfunding.setTestuser(testUser);
        try {
            userfundingMapper.insertUserfunding(userfunding);
        } catch (Exception e) {
            System.out.println("交易流水出错:" + e);
            return RetResponse.makeErrRsp("交易流水出错");
        }
        user.setMoney(user.getMoney() - userfunding.getMount());
        try {
            userMapper.updateUser(user);
        } catch (Exception e) {

            System.out.println("扣款出错:" + e);
            userfunding.setTest(2);
            userfunding.setTestRecord("扣款失败");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userfunding.setTesttime(df.format(new Date()));
            userfundingMapper.updateUserfundingTest(userfunding);
            return RetResponse.makeErrRsp("扣款出错");
        }

        return RetResponse.makeOKRsp("ok");
    }

    /**
     * 新增查询用户的交易明细，包括转入转出
     * @param map
     * @return
     */
    @RequestMapping(value = "/searchDetail", method = RequestMethod.POST)
    public RetResult<List<Userfunding>> searchDetail(@RequestBody Map<String, String> map) {
        List<Userfunding> result = new ArrayList<>();
        if (map.keySet().size() == 0) {
            return RetResponse.makeErrRsp("未传参数");
        }
        List<Userfunding> result1 = new ArrayList<>();
        List<Userfunding> result2 = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.equals("source")) {
                result1 = userfundingMapper.getUserfundingBySource(map.get("source"));

            }
            if (key.equals("go")) {
                result2 = userfundingMapper.getUserfundingByAuthorID(map.get("authorID"));
            }
        }
        result.addAll(result1);
        result.addAll(result2);
        return RetResponse.makeOKRsp(result);
    }


    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
    public RetResult<List<Userfunding>> searchList(@RequestBody Map<String, String> map) {
        List<Userfunding> result = new ArrayList<>();
        if (map.keySet().size() == 0) {
            return RetResponse.makeErrRsp("未传参数");
        }
        for (String key : map.keySet()) {
            if (key.equals("source")) {
                result = userfundingMapper.getUserfundingBySource(map.get("source"));
                break;
            }
            if (key.equals("authorID")) {
                result = userfundingMapper.getUserfundingByAuthorID(map.get("authorID"));
                break;
            }
        }
        return RetResponse.makeOKRsp(result);
    }

    /*@RequestMapping(value = "/verifyUser", method = RequestMethod.POST)
    public RetResult<String> verifyUser(@RequestBody Map<String,String> map)  {
        String number = map.get("number");
        String testResult = map.get("testResult");
        Userfunding userfunding = userfundingMapper.getUserfundingByNumber(number);
        System.out.println(userfunding.getMount());
        User to;
        List<User> toList = userMapper.getUserByAuthorID(userfunding.getAuthorID());
        if (toList.size() != 0){
            to = toList.get(0);
        }else {
            return RetResponse.makeErrRsp("目标用户不存在");
        }
    }*/

    @RequestMapping(value = "/verifyUser", method = RequestMethod.POST)
    public RetResult<String> verifyUser(@RequestBody Map<String, String> map) {
        String number = map.get("number");
        int test = Integer.valueOf(map.get("test"));
        String testRecord = map.get("testRecord");
        String testtime = map.get("testtime");
        Userfunding userfunding = userfundingMapper.getUserfundingByNumber(number);
        userfunding.setTestRecord(testRecord);
        userfunding.setTest(test);
        userfunding.setTesttime(testtime);
        User user = userMapper.getUserByUsername(userfunding.getAuthorID()).get(0);
        try {
            userFeaServer.money(user, userfunding);
        } catch (Exception e) {
            System.out.println(e);
            return RetResponse.makeErrRsp(e.getMessage());
        }
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public RetResult<String> verify(@RequestBody Map<String, String> map) {
        //RetResult retResult = new RetResult();
        String number = map.get("number");
        String testResult = map.get("testResult");
        Userfunding userfunding = userfundingMapper.getUserfundingByNumber(number);
        System.out.println(userfunding.getMount());
        List<User> userList = userMapper.getUserByAuthorID(userfunding.getAuthorID());
        User user = userList.get(0);
        if (testResult.equals("2")) {
            userfunding.setTest(2);
            userfunding.setTestRecord("error");
            userfunding.setTestuser("admin");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userfunding.setTesttime(df.format(new Date()));
            userfundingMapper.updateUserfundingTest(userfunding);
            user.setMoney(user.getMoney() + userfunding.getMount());
            userMapper.updateUser(user);
            return RetResponse.makeOKRsp("ok");
        }
        if (testResult.equals("1")) {
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
            Response verresponse = null;
            try {
                if ("200".equals(StringUtils.trim(result.get(XmlData.STATUSCODE)))) {
                    verresponse = JsonUtil.fromJson(StringUtils.trim(result.get(XmlData.DATA)), Response.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(verresponse.getCode());
            if (!verresponse.getCode().equals("0000")) {
                userfunding.setTest(2);
                userfunding.setTestRecord("目标账户不存在");
                userfunding.setTestuser("admin");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userfunding.setTesttime(df.format(new Date()));
                userfundingMapper.updateUserfundingTest(userfunding);
                user.setMoney(user.getMoney() + userfunding.getMount());
                userMapper.updateUser(user);
                return RetResponse.makeErrRsp("目标账户不存在");
            } else {
                userfunding.setTest(1);
                userfunding.setTestRecord("ok");
                userfunding.setTestuser("admin");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userfunding.setTesttime(df.format(new Date()));
                userfundingMapper.updateUserfundingTest(userfunding);
                BankCardOrder bankOrder = new BankCardOrder();
                bankOrder.setRealName(user.getName());
                bankOrder.setCardNumber(user.getBankID());
                bankOrder.setPhoneNumber(user.getPhone());
                bankOrder.setIdNumber(user.getActureID());
                bankOrder.setPayMoney(String.valueOf(userfunding.getMount()));
                bankOrder.setPayRemark("提现");
                try {
                    Map<String, Object> payResult = HttpUtil.post(bankOrder.assembleRequest(), Property.getUrl(ConfigPath.YZH_BANK_CARD_REAL_TIME_ORDER));
                    Response response = null;
                    try {
                        if ("200".equals(StringUtils.trim(result.get(XmlData.STATUSCODE)))) {
                            response = JsonUtil.fromJson(StringUtils.trim(payResult.get(XmlData.DATA)), Response.class);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (response.getCode().equals("0000")) {
                        userfunding.setIfWork(1);
                        userfunding.setWorkRecord("ok");
                        userfunding.setWorkUser("no");
                        userfunding.setWorkTime(df.format(new Date()));
                        userfundingMapper.updateUserfundingWork(userfunding);
                        return RetResponse.makeOKRsp("ok");
                    } else {
                        userfunding.setIfWork(2);
                        userfunding.setWorkRecord("网络错误");
                        userfunding.setWorkUser("no");
                        userfunding.setWorkTime(df.format(new Date()));
                        userfundingMapper.updateUserfundingWork(userfunding);
                        user.setMoney(user.getMoney() + userfunding.getMount());
                        userMapper.updateUser(user);
                        return RetResponse.makeErrRsp("提现失败");
                    }
                } catch (Exception e) {
                    userfunding.setIfWork(2);
                    userfunding.setWorkRecord("网络错误");
                    userfunding.setWorkUser("no");
                    userfunding.setWorkTime(df.format(new Date()));
                    userfundingMapper.updateUserfundingWork(userfunding);
                    user.setMoney(user.getMoney() + userfunding.getMount());
                    userMapper.updateUser(user);
                    return RetResponse.makeErrRsp("提现失败，网络错误");
                }
            }
        } else {
            return RetResponse.makeErrRsp("参数错误");
        }
    }

//    @RequestMapping(value = "/work", method = RequestMethod.POST)
//    public RetResult<String> work(@RequestBody Userfunding userfunding)  {
//        //RetResult retResult = new RetResult();
//        userfundingMapper.updateUserfundingWork(userfunding);
//        return RetResponse.makeOKRsp("ok");
//    }

    @RequestMapping(value = "/searchUnverify", method = RequestMethod.POST)
    public RetResult<List<Userfunding>> searchUnverify(@RequestBody Map<String, String> map) {
        String testUser = map.get("testUser");
        int type = Integer.valueOf(map.get("type"));
        List<Userfunding> result = new ArrayList<>();
        try {
            result = userfundingMapper.getUserfundingListByType(0, testUser, type);
        } catch (Exception e) {
            System.out.println(e);
            return RetResponse.makeErrRsp("查询错误");
        }

        return RetResponse.makeOKRsp(result);
    }

    @RequestMapping(value = "/searchVerify", method = RequestMethod.POST)
    public RetResult<List<Userfunding>> searchVerify(@RequestBody Map<String, String> map) {
        String testUser = map.get("testUser");
        int type = Integer.valueOf(map.get("type"));
        List<Userfunding> result = new ArrayList<>();
        try {
            result = userfundingMapper.getUserfundingListBytype12(0, testUser,type);
        } catch (Exception e) {
            System.out.println(e);
            return RetResponse.makeErrRsp("查询错误");
        }

        return RetResponse.makeOKRsp(result);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<List<Userfunding>> search(@RequestBody Map<String, String> map) {
        List<Userfunding> result = new ArrayList<>();
        String type = map.get("type");
        if (type.length() == 2) {
            String typea = type.substring(0, 1);
            String typeb = type.substring(1, 2);
            if (typea.equals("a")) {
                switch (typeb) {
                    case "0":
                        result = userfundingMapper.getUserfundingByTest(0);
                        break;
                    case "1":
                        result = userfundingMapper.getUserfundingByTest(1);
                        break;
                    case "2":
                        result = userfundingMapper.getUserfundingByTest(2);
                        break;
                    default:
                        return RetResponse.makeErrRsp("参数错误");
                }
                return RetResponse.makeOKRsp(result);
            } else if (typea.equals("b")) {
                switch (typeb) {
                    case "0":
                        result = userfundingMapper.getUserfundingByIfWork(0);
                        break;
                    case "1":
                        result = userfundingMapper.getUserfundingByIfWork(1);
                        break;
                    case "2":
                        result = userfundingMapper.getUserfundingByIfWork(2);
                        break;
                    default:
                        return RetResponse.makeErrRsp("参数错误");
                }
                return RetResponse.makeOKRsp(result);
            } else {
                return RetResponse.makeErrRsp("参数错误");
            }

        }
        String value = map.get("value");
        String token = map.get("token");
        int usertype = 0;
        List userList = userMapper.getUserByToken(token);
        if (userList.size() == 0) {
            return RetResponse.makeErrRsp("user error");
        } else {
            User user = (User) userList.get(0);
            usertype = Integer.valueOf(user.getType());
        }
        String type1 = type.substring(0, 1);
        int type2 = Integer.valueOf(type.substring(1, 2));
        int type3 = Integer.valueOf(type.substring(2, 3));
        int test = 0;
        int ifWork = 0;
        switch (type2) {
            case 0:
                break;
            case 1:
                test = 1;
                break;
            case 2:
                test = 1;
                ifWork = 1;
                break;
            default:
                return RetResponse.makeErrRsp("error");
        }
        switch (type1) {
            case "0":
                result = userfundingMapper.getUserfundingListByApplytime(value, test, ifWork, type3);
                break;
            case "1":
                result = userfundingMapper.getUserfundingListByAuthorId(value, test, ifWork, type3);
                break;
            case "2":
                result.add(userfundingMapper.getUserfundingByNumber(value));
                break;
            default:
                return RetResponse.makeErrRsp("error");
        }
        if (usertype < 4) {
            for (Userfunding userfunding : result) {
                userfunding.setSourceAccount(null);
                userfunding.setGoaccount(null);
                userfunding.setTestuser(null);
                userfunding.setWorkUser(null);
            }
        }
        return RetResponse.makeOKRsp(result);
    }

    public Response sysoutResult(Map<String, Object> result) {
        Response response = null;
        try {
            if ("200".equals(StringUtils.trim(result.get(XmlData.STATUSCODE)))) {
                response = JsonUtil.fromJson(StringUtils.trim(result.get(XmlData.DATA)), Response.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/searchArticle", method = RequestMethod.POST)
    public RetResult<List<Map<String, Object>>> searchArticle(@RequestBody Map<String, String> map) {
        String go = map.get("username");  // 用户名
        // int type = Integer.valueOf(map.get("type"));
        List<Userfunding> result = new ArrayList<>();
        try {
            result = userfundingMapper.getUserfundingByGoWithmoneyType(go,1);
        } catch (Exception e) {
            System.out.println(e);
            return RetResponse.makeErrRsp("查询错误");
        }

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            for (Userfunding userfunding : result) {
                Map map1 = PropertyUtils.describe(userfunding);
                List<Record> records = recordMapper.getRecord(userfunding.getNumber());
                map1.put("details", records);
                list.add(map1);
            }
        } catch (Exception e) {
            System.out.println(e);
            return RetResponse.makeErrRsp("查询record错误");
        }


        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/searchHealth", method = RequestMethod.POST)
    public RetResult<List<Map<String, Object>>> searchHealth(@RequestBody Map<String, String> map) {
        String go = map.get("username");  // 用户名
        // int type = Integer.valueOf(map.get("type"));
        List<Userfunding> result = new ArrayList<>();
        try {
            result = userfundingMapper.getUserfundingByGoWithmoneyType(go,4);
        } catch (Exception e) {
            System.out.println(e);
            return RetResponse.makeErrRsp("查询错误");
        }

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            for (Userfunding userfunding : result) {
                Map map1 = PropertyUtils.describe(userfunding);
                List<Record2> records = recordMapper.getRecord2(userfunding.getNumber());
                map1.put("details", records);
                list.add(map1);
            }
        } catch (Exception e) {
            System.out.println(e);
            return RetResponse.makeErrRsp("查询record错误");
        }


        return RetResponse.makeOKRsp(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/unit2unit", method = RequestMethod.POST)
    public RetResult unit2unit(@RequestBody Map<String,String> json) throws Exception {
        double money = Double.valueOf(json.get("money"));
        String username = json.get("username").toString();
        String sourcename = json.get("sourcename").toString();
        User from = userMapper.getUserByUsername(sourcename).get(0);
        User to = userMapper.getUserByUsername(username).get(0);
        Userfunding userfunding = new Userfunding();
        String number = numberUtils.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        userfunding.setNumber(number);
        userfunding.setAuthorID(sourcename);
        userfunding.setSource(sourcename);
        userfunding.setGo(username);
        userfunding.setMount(money);
        userfunding.setTest(1);
        userfunding.setIfWork(1);
        userfunding.setApplyTime(time);
        userfunding.setType(4);
        if (money > from.getMoney()){
            return RetResponse.makeErrRsp("余额不足");
        }
        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);
        try {
            userMapper.updateUser(from);
            userMapper.updateUser(to);
            userfundingMapper.insertUserfunding(userfunding);
        }catch (Exception e){
            return RetResponse.makeErrRsp("转账出错");
        }
        return RetResponse.makeOKRsp();
    }

    @RequestMapping(value = "/searchUnit", method = RequestMethod.POST)
    public RetResult<List<Userfunding>> searchUnit(@RequestBody Map<String, String> map) {
        List<Userfunding> result = new ArrayList<>();
        String username = map.get("username");
        String type = map.get("type");
        //这里把type去了
        List<Userfunding> tolist = userfundingMapper.getUserfundingByGoWithoutType(username);
        List<Userfunding> fromlist = userfundingMapper.getUserfundingBySource(username);
        result.addAll(tolist);
        result.addAll(fromlist);
        return RetResponse.makeOKRsp(result);
    }
}
