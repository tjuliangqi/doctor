package cn.tju.doctor.controller;

import cn.tju.doctor.dao.RecordMapper;
import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.dao.UserfundingMapper;
import cn.tju.doctor.dao.WorkMapper;
import cn.tju.doctor.daomain.*;
//import cn.tju.doctor.service.UserFeaServer;
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
    public RetResult<String> add(@RequestBody Map json) {
        String moneyS = json.get("money").toString();
        String username = json.get("username").toString();
        String sourcename = json.get("sourcename").toString();

        User from = userMapper.getUserByUsername(sourcename).get(0);
        User to = userMapper.getUserByUsername(username).get(0);
        Userfunding userfunding = new Userfunding();
        Userfunding userfunding1 = new Userfunding();
        String IdNumber = numberUtils.getOrderNo();
        String IdNumber1 = numberUtils.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        userfunding.setNumber(IdNumber);
        userfunding.setAuthorID(sourcename);
        userfunding.setSource(sourcename);
        userfunding.setGo(username);
        userfunding.setMount(Double.valueOf(moneyS)*0.8);
        userfunding.setTest(1);
        userfunding.setIfWork(1);
        userfunding.setApplyTime(time);
        userfunding.setType(2);
        userfunding.setMoneyType(1);

        userfunding1.setNumber(IdNumber1);
        userfunding1.setAuthorID(sourcename);
        userfunding1.setSource(sourcename);
        userfunding1.setGo(username);
        userfunding1.setMount(Double.valueOf(moneyS)*0.2);
        userfunding1.setTest(1);
        userfunding1.setIfWork(1);
        userfunding1.setApplyTime(time);
        userfunding1.setType(2);
        userfunding1.setMoneyType(4);
        if (Double.valueOf(moneyS) > from.getMoney()){
            return RetResponse.makeErrRsp("余额不足");
        }
//        if (Double.valueOf(moneyS) > from.getMoney()) {
//            return RetResponse.makeErrRsp("余额不足");
//        }
        from.setMoney(from.getMoney()-Double.valueOf(moneyS));
        to.setMoney(to.getMoney()+Double.valueOf(moneyS));
        to.setArticleIncome(to.getArticleIncome()+Double.valueOf(moneyS) * 0.8);
        to.setHealthIncome(to.getHealthIncome() + Double.valueOf(moneyS) * 0.2);


//        from.setMoney(from.getMoney() - Double.valueOf(moneyS));
//        to.setMoney(to.getMoney() + Double.valueOf(moneyS));
//        to.setArticleIncome(to.getArticleIncome() + Double.valueOf(moneyS));

        try {
            userMapper.updateUser(from);
            userMapper.updateUser(to);
            userfundingMapper.insertUserfunding(userfunding);
            userfundingMapper.insertUserfunding(userfunding1);
        }catch (Exception e){
            return RetResponse.makeErrRsp("转账出错");
        }
//        try {
//            IdNumber = userFeaServer.getID(sourcename,username,Double.valueOf(moneyS));
//        } catch (Exception e) {
//            return RetResponse.makeErrRsp(e.getMessage());
//        }
        int money = (int)(Double.parseDouble(moneyS) * 0.85);
        int l = 1;
        while (money / l > 0) {
            l *= 10;
        }
        int shuLiangJi = l / 100;
        System.out.println(shuLiangJi);
        if (shuLiangJi <= 1) {
            return RetResponse.makeErrRsp("资金太少无法用于提现");
        }
        int number = (((money % shuLiangJi) == 0) ? (money / shuLiangJi) : (money / shuLiangJi + 1));
        List<String> paper = recordMapper.getPaper(number);
        Record r = new Record();
        r.setNumber(IdNumber);//流水号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        r.setPublishTime(sdf.format(date));

        int views = 0;
        int download = 0;
        int likes = 0;
        int hides = 0;
        if (money % shuLiangJi != 0) {
            r.setArticleID(paper.remove(0));
            hides = (int) (Math.random() * (money % shuLiangJi / 4));
            download = (int) (Math.random() * (money % shuLiangJi / 8));
            likes = (int) (Math.random() * (money % shuLiangJi / 4));
            views = ((money % shuLiangJi) - hides - download * 2 - likes) * 2;
            r.setViews(views);
            r.setViewsMoney(views * 0.5);
            r.setHides(hides);
            r.setHidesMoney(hides * 1.0);
            r.setDownload(download);
            r.setDownloadsMoney(download * 2.0);
            r.setLikes(likes);
            r.setLikesMoney(likes * 1.0);
            recordMapper.insertRecord(r);
        }

        for (String each : paper) {
            r.setArticleID(each);
            hides = (int) (Math.random() * shuLiangJi / 4);
            download = (int) (Math.random() * shuLiangJi / 8);
            likes = (int) (Math.random() * shuLiangJi / 4);
            views = (shuLiangJi - hides - download * 2 - likes) * 2;
            r.setViews(views);
            r.setViewsMoney(views * 0.5);
            r.setHides(hides);
            r.setHidesMoney(hides * 1.0);
            r.setDownload(download);
            r.setDownloadsMoney(download * 2.0);
            r.setLikes(likes);
            r.setLikesMoney(likes * 1.0);
            recordMapper.insertRecord(r);
        }

        double money2 = Double.parseDouble(moneyS) - money;
        double registMoney = (double) (Math.random() * 0.25 + 0.125) * money2;
        int registNum = (int) registMoney / 10;

        double onlineMoney = (double) (Math.random() * 0.25 + 0.125) * money2;
        int onlineNum = (int) onlineMoney / 20;

        double forwardMoney = money2 - registNum*10 - onlineNum*20;

        Record2 record2 = new Record2();
        record2.setNumber(IdNumber1);
        record2.setRegistNum(registNum);
        record2.setRegistMoney(registNum*10);
        record2.setForwardNum((int)forwardMoney/4);
        record2.setForwardMoney(forwardMoney);
        record2.setOnlineNum(onlineNum);
        record2.setOnlineMoney(onlineNum*20);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        record2.setPublishTime(sdf2.format(date));
        recordMapper.insertRecord2(record2);

        return RetResponse.makeOKRsp("OK");
//        return RetResponse.makeErrRsp("查无数据");
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public RetResult<List<Record>> query(@RequestBody Map json) {
        String number = json.get("number").toString();
        List<Record> records = recordMapper.getRecord(number);

        return RetResponse.makeOKRsp(records);
//        return RetResponse.makeErrRsp("查无数据");
    }

    @RequestMapping(value = "/query2", method = RequestMethod.POST)
    public RetResult<List<Record2>> query2(@RequestBody Map json) {
        String number = json.get("number").toString();
        List<Record2> records = recordMapper.getRecord2(number);

        return RetResponse.makeOKRsp(records);
//        return RetResponse.makeErrRsp("查无数据");
    }
}
