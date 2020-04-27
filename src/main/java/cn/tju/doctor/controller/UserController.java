package cn.tju.doctor.controller;


import cn.tju.doctor.Config;
import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import cn.tju.doctor.daomain.User;
import cn.tju.doctor.service.EmailService;
import cn.tju.doctor.utils.VerifyUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static cn.tju.doctor.utils.fileUtil.upload;


@RestController

@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    EmailService emailService;
    @RequestMapping(value = "/getCheckCode", method = RequestMethod.POST)
    public RetResult<String> getCheckCode(@RequestBody Map<String,String> json){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的验证码为："+checkCode;
        List<User> emaillist = userMapper.getUserByEmail(json.get("email"));
        if (emaillist.size()==0){
            try {
                emailService.sendSimpleMail(json.get("email"), "验证码", message);
            }catch (Exception e){
                e.printStackTrace();
                return RetResponse.makeErrRsp("邮箱验证失败，请检查邮箱是否正确");
            }
            return RetResponse.makeOKRsp(checkCode);
        }else {
            return RetResponse.makeErrRsp("该邮箱已经被注册");
        }

    }

    @RequestMapping(value = "/getCheckUser", method = RequestMethod.POST)
    public RetResult<String> getCheckUser(@RequestBody Map<String,String> json){
        List<User> usernamelist = userMapper.getUserByUsername(json.get("username"));
        List<User> emaillist = userMapper.getUserByEmail(json.get("email"));
        if (usernamelist.size()==0 && emaillist.size() == 0){
            return RetResponse.makeOKRsp("ok");
        }else if (usernamelist.size() != 0){
            return RetResponse.makeErrRsp("该用户名已经被注册");
        }else if (emaillist.size() != 0){
            return RetResponse.makeErrRsp("该邮箱已经被注册");
        }else {
            return RetResponse.makeErrRsp("ERROR");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RetResult<Map> login(@RequestBody Map<String,String> json){
        List<User> list = userMapper.getUserByEmail(json.get("email"));
        if (list.size()==0){
            return RetResponse.makeErrRsp("邮箱不正确");
        }else {
            User user = list.get(0);
            if (user.getPassword().equals(json.get("password"))){
                if (user.getTest().equals("0")){
                    userMapper.deleteUserById(user.getAuthorID());
                    return RetResponse.makeErrRsp("审核未通过");
                }
                if (user.getTest().equals("10000")){
                    return RetResponse.makeErrRsp("请等待审核");
                }
                Map<String,Object> map = new HashMap<>();
                map.put("username",user.getUsername());
                String token= JWT.create().withAudience(String.valueOf(user.getActureID())).sign(Algorithm.HMAC256(user.getPassword()));
                user.setToken(token);
                userMapper.updateUser(user);
                map.put("token",token);
                map.put("type",user.getType());
                map.put("authorID",user.getAuthorID());
                map.put("area",user.getArea());
                map.put("region",user.getRegion());
                map.put("test",user.getTest());
                map.put("money",user.getMoney());
                return RetResponse.makeOKRsp(map);
            }else {
                return RetResponse.makeErrRsp("密码不正确");
            }
        }
    }

    @RequestMapping(value = "/getImg", method = RequestMethod.POST)
    public RetResult<Object> getImg(HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session

        return RetResponse.makeOKRsp(objs);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public RetResult<String> verify(@RequestBody Map<String,String> json){
        String testResult = json.get("testResult");
        String percent = json.get("percent");
        String authorID = json.get("authorID");

        double percentDou = Double.parseDouble(percent);
        User user = new User();
        user.setTest(testResult);
        user.setPercent(percentDou);
        user.setAuthorID(authorID);
        userMapper.updateUser(user);
        return RetResponse.makeOKRsp("OK");
    }

    @RequestMapping(value = "/verify1", method = RequestMethod.POST)
    public RetResult<String> verify1(@RequestBody Map<String,String> json){
        String testResult = json.get("testResult");
        String authorID = json.get("authorID");

        User user = new User();
        user.setTest(testResult);
        user.setAuthorID(authorID);
        userMapper.updateUser(user);
        return RetResponse.makeOKRsp("OK");
    }

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public RetResult<Object> checkUser(@RequestBody Map<String,String> map){
        List<User> list = userMapper.getUserByEmail(map.get("email"));
        if (list.size()==0){
            return RetResponse.makeErrRsp("邮箱不正确");
        }else {
            User user = list.get(0);
            if (user.getUsername().equals(map.get("username"))){
                return RetResponse.makeOKRsp();
            }else {
                return RetResponse.makeErrRsp("用户名不正确");
            }
        }
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public RetResult<Map> getUser(@RequestBody Map<String,String> map){
        List<User> list = userMapper.getUserByToken(map.get("token"));
        Map<String,Object> data = new HashMap<>();
        if (list.size()==0){
            return RetResponse.makeErrRsp("用户不存在");
        }else {
            User user;
            try{
                user = list.get(0);
            }catch (Exception e){
                e.printStackTrace();
                return RetResponse.makeErrRsp("获取用户信息出错");
            }
            data.put("username",user.getUsername());
            data.put("modify",user.getModify());
            data.put("email",user.getEmail());
            data.put("ares",user.getArea());
            data.put("region",user.getRegion());
            data.put("unit",user.getUnit());
            data.put("part",user.getPart());
            data.put("like",user.getLike());
            data.put("download",user.getDownload());
            data.put("record",user.getRecord());
            data.put("view",user.getView());
            data.put("getMoney",user.getGetMoney());
            data.put("getmoneyrecord",user.getGetmoneyrecord());
            data.put("state",user.getState());
            data.put("money",user.getMoney());
            return RetResponse.makeOKRsp(data);
        }
    }

    @RequestMapping(value = "/modify")
    public RetResult<String> updateUser(@RequestBody User user){
        List<User> list = userMapper.getUserByEmail(user.getEmail());
        User newUser = list.get(0);
        user.setAuthorID(newUser.getAuthorID());
        /*newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setArea(user.getArea());
        newUser.setRegion(user.getRegion());
        newUser.setUnit(user.getUnit());
        newUser.setPart(user.getPart());
        newUser.setName(user.getName());
        newUser.setActureID(user.getActureID());
        newUser.setPhone(user.getPhone());
        newUser.setAddress(user.getAddress());
        newUser.setBankID(user.getBankID());
        newUser.setIfDoc(user.getIfDoc());
        newUser.setDocID(user.getDocID());
        newUser.setDocIDurl(user.getDocIDurl());
        newUser.setType(user.getType());*/
        int flag = userMapper.updateUser(user);
        if (flag == 1){
            return RetResponse.makeOKRsp("ok");
        }else{
            return RetResponse.makeErrRsp("ERROR");
        }
    }

    @RequestMapping(value = "/getDoc")
    public RetResult<List> getDoc(@RequestBody Map<String,String> map){
        List<User> list = userMapper.getUserByArea(map.get("area"));
        List<Map> result = new ArrayList<>();
        if (list.size()==0){
            return RetResponse.makeErrRsp("用户不存在");
        }else {
            for (User user : list){
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("username",user.getUsername());
                resultMap.put("modify",user.getModify());
                resultMap.put("wechatQun",user.getWechatQun());
                resultMap.put("active",user.getActive());
                resultMap.put("online",user.getOnline());
                resultMap.put("area",user.getArea());
                resultMap.put("region",user.getRegion());
                resultMap.put("unit",user.getUnit());
                resultMap.put("part",user.getPart());
                result.add(resultMap);
            }
            return RetResponse.makeOKRsp(result);
        }

    }

    @RequestMapping(value = "/serachList", method = RequestMethod.POST)
    public RetResult<List<User>> searchList(@RequestBody Map<String,String> map){
        String type = map.get("type");
        List<User> list = userMapper.getUserByType(type);
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/selectByName", method = RequestMethod.POST)
    public RetResult<List<Map<String,String>>> selectByName(@RequestBody Map<String,String> map){
        String company = map.get("text");
        List<String> list = userMapper.selectByName(company);
        List<Map<String,String>> result = new ArrayList<>();
        List<String> newList = list.stream().distinct().collect(Collectors.toList());
        for(int i =0; i<newList.size();i++){
            if (i==10){
                return RetResponse.makeOKRsp(result);
            }
            Map<String,String> map1 = new HashMap<>();
            map1.put("value",newList.get(i));
            result.add(map1);
        }
        return RetResponse.makeOKRsp(result);
    }


    @RequestMapping(value = "/regis", method = RequestMethod.POST)
    public RetResult<String> login(@RequestBody User user){
        user.setTest("10000");
        user.setAuthorID(UUID.randomUUID().toString());
        user.setType("0");
        System.out.println("ok");
        //调用审核未写
        int flag = userMapper.insertUser(user);
        if (flag==1){
            return RetResponse.makeOKRsp("ok");
        }else {
            return RetResponse.makeErrRsp("注册失败");
        }
    }

    // 管理员注册接口
    @RequestMapping(value = "/regis2", method = RequestMethod.POST)
    public RetResult<String> login2(@RequestParam("data") MultipartFile file,
                                    User user) {

        String dataURL = upload(file,user.getUsername());
        user.setType("3");
        user.setUnit(""); //管理用户没有上级，设为空
        user.setFileURL(dataURL);
        //user.setManageLevel(user.getManageLevel());
        user.setTest("10000");
        user.setAuthorID(UUID.randomUUID().toString());
        System.out.println("ok");
        //调用审核未写
        int flag = userMapper.insertUser(user);
        if (flag==1){
            return RetResponse.makeOKRsp("ok");
        }else {
            return RetResponse.makeErrRsp("注册失败");
        }
    }
    //超级管理员查询待审核公司管理用户接口
    @RequestMapping(value = "/searchUnverify2", method = RequestMethod.POST)
    public RetResult<List<User>> searchUnverify2(){
        List<User> lists = userMapper.getUserByUnitForGuan("3");
        return RetResponse.makeOKRsp(lists);
    }

    //超级管理员查询待审核公司业务账户接口
    @RequestMapping(value = "/searchUnverify3", method = RequestMethod.POST)
    public RetResult<List<User>> searchUnverify3(){
        List<User> lists = userMapper.getUserByUnitForGuan("7");
        return RetResponse.makeOKRsp(lists);
    }

    @RequestMapping(value = "/regis1", method = RequestMethod.POST)
    public RetResult<String> login1(@RequestParam("file") MultipartFile file, User user){
        user.setTest("10000");
        user.setAuthorID(UUID.randomUUID().toString());
        System.out.println("ok");
        //调用审核未写
        String fileName = file.getOriginalFilename();
        String destFileName = Config.UPLOAD_DIR + File.separator + user.getUsername() + File.separator + fileName;

        File destFile = new File(destFileName);
        if (!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdir();
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setFileURL("http://39.96.65.14/doctorfile/upload/" + user.getUsername() + "/" + fileName);
        int flag = userMapper.insertUser1(user);
        if (flag==1){
            return RetResponse.makeOKRsp("ok");
        }else {
            return RetResponse.makeErrRsp("注册失败");
        }
    }

    @RequestMapping(value = "/getTest", method = RequestMethod.POST)
    public RetResult<List<User>> getTest(){
        List<User> list = userMapper.getUserByTest("10000");
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/getHealthy", method = RequestMethod.POST)
    public RetResult<List> getHealthy(@RequestBody Map<String,String> map){
        List<User> users = userMapper.getUserByArea(map.get("local"));
        List<Map> list = new ArrayList<>();
        if (users.size()==0){
            return RetResponse.makeOKRsp(list);
        }else {
            for(User each:users){
                Map<String,Object> data = new HashMap<>();
                data.put("pic","https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
                //data.put("username",each.getUsername());
                data.put("wechatQun",each.getWechatQun());
                data.put("active",each.getActive());
                data.put("online",each.getOnline());
                data.put("area",each.getArea());
                data.put("region",each.getRegion());
                data.put("unit",each.getUnit());
                data.put("result",each.getResult());
                list.add(data);
            }
            return RetResponse.makeOKRsp(list);
        }
    }

    @RequestMapping(value = "/serachList2", method = RequestMethod.POST)
    public RetResult<List<String>> searchList2(@RequestBody Map<String,String> map){
        String guan_username = map.get("username");
        List<String> res = new ArrayList();
        try {
            List<User> guan_list = userMapper.getUserByUsername(guan_username);
            String company = guan_list.get(0).getCompany();
            List<User> pu_list = userMapper.getUserByCompany(company,"0");
            for(User user:pu_list){
                res.add(user.getUsername());
            }
        } catch (Exception E){
            return RetResponse.makeErrRsp("无可选择的指派用户");
        }

        return RetResponse.makeOKRsp(res);
    }

    @RequestMapping(value = "/serachList3", method = RequestMethod.POST)
    public RetResult<List<String>> searchList3(@RequestBody Map<String,String> map){
        String gong_username = map.get("username");
        List<String> res = new ArrayList();
        try {
            List<User> gong_list = userMapper.getUserByUsername(gong_username);
            String company = gong_list.get(0).getCompany();
            List<User> guan_list = userMapper.getUserByCompany(company,"7");
            for(User user:guan_list){
                res.add(user.getUsername());
            }
        } catch (Exception E){
            return RetResponse.makeErrRsp("无可选择的管理人员");
        }

        return RetResponse.makeOKRsp(res);
    }
    @RequestMapping(value = "/searchList4", method = RequestMethod.POST)
    public RetResult<Map<String,Double>> searchList4(@RequestBody Map<String,String> map){
        String authorID = map.get("authorID");
        List<User> users = userMapper.getUserByAuthorID(authorID);
        Map<String,Double> result = new HashMap<>();
        result.put("articleIncome",users.get(0).getArticleIncome());
        result.put("projectIncome",users.get(0).getProjectIncome());
        result.put("trainingIncome",users.get(0).getTrainingIncome());
        result.put("healthIncome",users.get(0).getHealthIncome());
        result.put("amountAvailable",users.get(0).getMoney());
        return RetResponse.makeOKRsp(result);
    }

    @RequestMapping(value = "/searchAllCompany", method = RequestMethod.POST)
    public RetResult<List<Map>> searchAllCompany(){
        List<User> users = userMapper.getUserByType("3");
        if(users.size()<1) return RetResponse.makeErrRsp("查询数量为0");
        List<Map> list = new ArrayList<>();
        for(User each:users){
            Map<String,Object> map = new HashMap<>();
            map.put("company",each.getCompany());
            map.put("address",each.getAddress());
            map.put("phone",each.getPhone());
            map.put("money",each.getMoney());
            map.put("testUser",each.getTestUser());

            list.add(map);
        }
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/searchOneCompany", method = RequestMethod.POST)
    public RetResult<Map> searchOneCompany(@RequestBody Map<String,String> map){
        String username = map.get("username");
        List<User> users = userMapper.getUserByUsername(username);
        if(users.size()<1) return RetResponse.makeErrRsp("查询数量为0");
        double money = users.get(0).getMoney();
        Map<String, Double> amap = new HashMap<>();
        amap.put("money", money);
        return RetResponse.makeOKRsp(amap);
    }

    @RequestMapping(value = "/updateCompanyMount", method = RequestMethod.POST)
    public RetResult<String> updateCompanyMount(@RequestBody Map<String,String> map){
        String company = map.get("username");
        String money = (map.get("money"));
        List<User> list = userMapper.getUserByCompany(company,"3");
        Double m = list.get(0).getMoney();
        m = m + Double.valueOf(money);
        try {
            userMapper.updateMoney(company, String.valueOf(m));
            return RetResponse.makeOKRsp("ok");
        } catch (Exception e){
            return RetResponse.makeErrRsp("更新失败");
        }
    }

    @RequestMapping(value = "/searchUnverify", method = RequestMethod.POST)
    public RetResult<List<User>> searchUnverify(@RequestBody Map<String,String> map){
        List<User> list = new ArrayList<>();
        String username = map.get("username");
        list = userMapper.getUserByUnit(username,"0");
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/searchUnverify1", method = RequestMethod.POST)
    public RetResult<List<User>> searchUnverify1(@RequestBody Map<String,String> map){
        List<User> list = new ArrayList<>();
        String username = map.get("username");
        list = userMapper.getUserByUnit(username,"7");
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/searchList6", method = RequestMethod.POST)
    public RetResult<List<User>> searchList6(@RequestBody Map<String,String> map){
        List<User> list = new ArrayList<>();
        String username = map.get("username");
        list = userMapper.getUserByUsernameWithType(username,"7");
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/searchList5", method = RequestMethod.POST)
    public RetResult<List<User>> searchList5(@RequestBody Map<String,String> map){
        List<User> list = new ArrayList<>();
        String username = map.get("username");
        list = userMapper.getUserByUsernameWithType(username,"0");
        return RetResponse.makeOKRsp(list);
    }



}
