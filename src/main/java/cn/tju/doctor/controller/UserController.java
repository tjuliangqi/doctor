package cn.tju.doctor.controller;


import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import cn.tju.doctor.daomain.User;
import cn.tju.doctor.service.EmailService;
import cn.tju.doctor.utils.VerifyUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


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

    @RequestMapping(value = "/regis", method = RequestMethod.POST)
    public RetResult<String> login(@RequestBody User user){
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

    @RequestMapping(value = "/getTest", method = RequestMethod.POST)
    public RetResult<List<User>> getTest(){
        List<User> list = userMapper.getUserByTest("10000");
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/getHealthy", method = RequestMethod.POST)
    public RetResult<Map> getHealthy(@RequestBody Map<String,String> map){
        List<User> list = userMapper.getUserByArea(map.get("local"));
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
            data.put("wechatQun",user.getWechatQun());
            data.put("active",user.getActive());
            data.put("online",user.getOnline());
            data.put("area",user.getArea());
            data.put("region",user.getRegion());
            data.put("unit",user.getUnit());
            data.put("part",user.getPart());
            return RetResponse.makeOKRsp(data);
        }
    }

}
