package cn.tju.doctor.controller;

import cn.tju.doctor.dao.RecordMapper;
import cn.tju.doctor.dao.WorkMapper;
import cn.tju.doctor.daomain.*;
import cn.tju.doctor.utils.JsonToMapUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RetResult<String> add(@RequestBody Map json){
        String moneyS = json.get("money").toString();
        String username = json.get("username").toString();
        String sourcename = json.get("sourcename").toString();

        int money = Integer.parseInt(moneyS);
        int number = ((money%1000)==0)?(money/1000):(money/1000+1);
        List<String> paper = recordMapper.getPaper(number);
        Record r = new Record();
        r.setNumber("123");//流水号
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        r.setPublishTime(sdf.format(date));
        for (String each:paper){
            r.setArticleID(each);
            int views = (int)(Math.random()*250);
            int download = (int)(Math.random()*125);
            int likes = (int)(Math.random()*250);
            int hides = (int)((1000-views-download*2-likes)/1.5);
            r.setViews(views);
            r.setViewsMoney(views*1.0);
            r.setHides(hides);
            r.setHidesMoney(hides*1.5);
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
