package cn.tju.doctor.controller;


import cn.tju.doctor.dao.WorkMapper;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import cn.tju.doctor.daomain.Work;
import cn.tju.doctor.daomain.WorkState;
import cn.tju.doctor.utils.JsonToMapUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController

@RequestMapping("/work")
public class workController {
    @Autowired
    WorkMapper workMapper;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<List> search(@RequestBody Map json) throws JSONException {
        System.out.println(json.get("type"));
        System.out.println(json.get("value"));
        String type = json.get("type").toString();
        String value = json.get("value").toString();
        Map valueMap = new HashMap();
        valueMap = JsonToMapUtils.strToMap(value);
        String firstState = type.substring(0, 1);
        String secondState = type.substring(1, 2);
        WorkState workState = new WorkState();
        switch (firstState){
            case "0":
                workState.setState1("publishTime");
                workState.setStateValue1(valueMap.get("publishTime").toString());
                break;
            case "1":
                workState.setState1("workID");
                workState.setStateValue1(valueMap.get("workID").toString());
                break;
            case "2":
                workState.setState1("publishID");
                workState.setStateValue1(valueMap.get("publishID").toString());
                break;
            case "3":
                workState.setState1("acceptID");
                workState.setStateValue1(valueMap.get("acceptID").toString());
                break;
        }
        switch (secondState){
            case "0":
                workState.setState2("ifRead");
                workState.setStateValue2("0");
                break;
            case "1":
                workState.setState2("ifRead");
                workState.setStateValue2("1");
                break;
            case "2":
                workState.setState2("ifFinish");
                workState.setStateValue2("1");
                break;
            case "3":
                workState.setState2("workID");
                workState.setStateValue2(valueMap.get("workID").toString());
                break;
        }
        System.out.println(workState);
        List<Work> workByIDAndState = workMapper.getWorkByIDAndState(workState);


        return RetResponse.makeOKRsp(workByIDAndState);
//        return RetResponse.makeErrRsp("查无数据");
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RetResult<String> add(@RequestBody Map json) {
        String workID = json.get("workID").toString();
        String name = json.get("name").toString();
        String publishID = json.get("publishID").toString();
        String publishName = json.get("publishName").toString();
        String acceptID = json.get("acceptID").toString();
        String acceptName = json.get("acceptName").toString();
        String introduce = json.get("introduce").toString();
        String workfile = json.get("workfile").toString();
        Work work = new Work();
        work.setWorkID(workID);
        work.setName(name);
        work.setPublishID(publishID);
        work.setPublishName(publishName);
        work.setAcceptID(acceptID);
        work.setAcceptName(acceptName);
        work.setIntroduce(introduce);
        work.setWorkfile(workfile);
        String uuid = UUID.randomUUID().toString();
        work.setUuid(uuid);
        workMapper.insertWork(work);
        return RetResponse.makeOKRsp("ok");
//        return RetResponse.makeErrRsp("查无数据");
    }
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public RetResult<String> modify(@RequestBody Map json) {
        String uuid = json.get("uuid").toString();
        String type = json.get("type").toString();
        String workfile = json.get("workfile").toString();
        WorkState workState = new WorkState();
        workState.setType(type);
        workState.setUuid(uuid);
        workState.setWorkfile(workfile);
        System.out.println(workState.getType());
        workMapper.updateWork(workState);
        return RetResponse.makeOKRsp("ok");
//        return RetResponse.makeErrRsp("查无数据");
    }
    @RequestMapping(value = "/out", method = RequestMethod.POST)
    public RetResult<Map> out(@RequestBody Map json) {
        System.out.println(json.get("type"));
        System.out.println(json.get("value"));
        String type = json.get("type").toString();
        String value = json.get("value").toString();
        String firstState = type.substring(0, 1);
        String secondState = type.substring(1, 2);
        WorkState workState = new WorkState();
        workState.setStateValue1(value);
        switch (firstState){
            case "0":
                workState.setState1("publishTime");
                break;
            case "1":
                workState.setState1("workID");
                break;
            case "2":
                workState.setState1("publishID");
                break;
            case "3":
                workState.setState1("acceptID");
                break;
        }
        switch (secondState){
            case "0":
                workState.setState2("ifRead");
                workState.setStateValue2("0");
                break;
            case "1":
                workState.setState2("ifRead");
                workState.setStateValue2("1");
                break;
            case "2":
                workState.setState2("ifFinish");
                workState.setStateValue2("1");
                break;
        }
        System.out.println(workState);
        Map result = new HashMap();
        result.put("url","");
        return RetResponse.makeOKRsp(result);
//        return RetResponse.makeErrRsp("查无数据");
    }
}
