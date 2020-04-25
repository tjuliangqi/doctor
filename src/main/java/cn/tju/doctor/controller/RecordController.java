package cn.tju.doctor.controller;

import cn.tju.doctor.dao.RecordMapper;
import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.dao.UserfundingMapper;
import cn.tju.doctor.dao.WorkMapper;
import cn.tju.doctor.daomain.*;
import cn.tju.doctor.service.UserFeaServer;
import cn.tju.doctor.utils.JsonToMapUtils;
import cn.tju.doctor.utils.numberUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/recording")
public class RecordController {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserfundingMapper userfundingMapper;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public RetResult<String> add(@RequestBody Map json){
        String moneyS = json.get("money").toString();
        String username = json.get("username").toString();
        String sourcename = json.get("sourcename").toString();

        User from = userMapper.getUserByUsername(sourcename).get(0);
        User to = userMapper.getUserByUsername(username).get(0);
        to.setArticleIncome(to.getArticleIncome()+Double.valueOf(moneyS));
        userMapper.updateUser(to);
        Userfunding userfunding = new Userfunding();
        String IdNumber = numberUtils.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        userfunding.setNumber(IdNumber);
        userfunding.setAuthorID(sourcename);
        userfunding.setSource(sourcename);
        userfunding.setGo(username);
        userfunding.setMount(Double.valueOf(moneyS));
        userfunding.setTest(1);
        userfunding.setIfWork(1);
        userfunding.setApplyTime(time);
        userfunding.setType(2);
        if (Double.valueOf(moneyS) > from.getMoney()){
            return RetResponse.makeErrRsp("余额不足");
        }
        from.setMoney(from.getMoney()-Double.valueOf(moneyS));
        to.setMoney(to.getMoney()+Double.valueOf(moneyS));
        try {
            userMapper.updateUser(from);
            userMapper.updateUser(to);
            userfundingMapper.insertUserfunding(userfunding);
        }catch (Exception e){
            return RetResponse.makeErrRsp("转账出错");
        }
//        try {
//            IdNumber = userFeaServer.getID(sourcename,username,Double.valueOf(moneyS));
//        } catch (Exception e) {
//            return RetResponse.makeErrRsp(e.getMessage());
//        }
        int money = Integer.parseInt(moneyS);
        int l = 1;
        while (money/l>0){
            l*=10;
        }
        int shuLiangJi = l/100;
        System.out.println(shuLiangJi);
        if(shuLiangJi<=1){
            return RetResponse.makeErrRsp("资金太少无法用于提现");
        }
        int number = ((money%shuLiangJi)==0)?(money/shuLiangJi):(money/shuLiangJi+1);
        List<String> paper = recordMapper.getPaper(number);
        Record r = new Record();
        r.setNumber(IdNumber);//流水号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        r.setPublishTime(sdf.format(date));

        int views = 0;
        int download = 0;
        int likes = 0;
        int hides = 0;
        if(money%shuLiangJi!=0){
            r.setArticleID(paper.remove(0));
            hides = (int)(Math.random()*(money%shuLiangJi/4));
            download = (int)(Math.random()*(money%shuLiangJi/8));
            likes = (int)(Math.random()*(money%shuLiangJi/4));
            views = ((money%shuLiangJi)-hides-download*2-likes)*2;
            r.setViews(views);
            r.setViewsMoney(views*0.5);
            r.setHides(hides);
            r.setHidesMoney(hides*1.0);
            r.setDownload(download);
            r.setDownloadsMoney(download*2.0);
            r.setLikes(likes);
            r.setLikesMoney(likes*1.0);
            recordMapper.insertRecord(r);
        }

        for (String each:paper){
            r.setArticleID(each);
            hides = (int)(Math.random()*shuLiangJi/4);
            download = (int)(Math.random()*shuLiangJi/8);
            likes = (int)(Math.random()*shuLiangJi/4);
            views = (shuLiangJi-hides-download*2-likes)*2;
            r.setViews(views);
            r.setViewsMoney(views*0.5);
            r.setHides(hides);
            r.setHidesMoney(hides*1.0);
            r.setDownload(download);
            r.setDownloadsMoney(download*2.0);
            r.setLikes(likes);
            r.setLikesMoney(likes*1.0);
            recordMapper.insertRecord(r);
        }

        return RetResponse.makeOKRsp("OK");
//        return RetResponse.makeErrRsp("查无数据");
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public RetResult<List<Record>> query(@RequestBody Map json){
        String number = json.get("number").toString();
        List<Record> records = recordMapper.getRecord(number);

        return RetResponse.makeOKRsp(records);
//        return RetResponse.makeErrRsp("查无数据");
    }
}
