package cn.tju.doctor.controller;


import cn.tju.doctor.Config;
import cn.tju.doctor.dao.WorkMapper;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import cn.tju.doctor.daomain.Work;
import cn.tju.doctor.daomain.WorkState;
import cn.tju.doctor.utils.JsonToMapUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
        workState.setStateValue3(valueMap.get("process").toString());
        if(valueMap.get("process").toString().equals("a")){
            workState.setState3("'a'");
        }else {
            workState.setState3("process");
        }
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
            case "4":
                workState.setState1("acceptName");
                workState.setStateValue1(valueMap.get("acceptName").toString());
                break;
        }
        switch (secondState){
            case "0":
                workState.setState2("0");
                workState.setStateValue2("0");
                break;
            case "1":
                workState.setState2("ifFinish");
                workState.setStateValue2("0");
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
    public RetResult<String> add(@RequestParam("file") MultipartFile file,
                                 @RequestParam("workID") String workID,
                                 @RequestParam("name") String name,
                                 @RequestParam("publishID") String publishID,
                                 @RequestParam("publishName") String publishName,
                                 @RequestParam("acceptID") String acceptID,
                                 @RequestParam("acceptName") String acceptName,
                                 @RequestParam("introduce") String introduce,
                                 @RequestParam("process") String process) {

        String workfile = null;
//        String process = json.get("process").toString();
        try {
            String fileName = file.getOriginalFilename();
            String destFileName = Config.UPLOAD_DIR + File.separator + name + File.separator + fileName;

            File destFile = new File(destFileName);
            if (!destFile.getParentFile().exists()){
                destFile.getParentFile().mkdir();
            }
            file.transferTo(destFile);

            workfile="http://39.96.65.14/doctorfile/upload/" + name + "/" + fileName;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return RetResponse.makeErrRsp("文件上传失败");
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpleDateFormat.format(date);

        Work work = new Work();
        work.setPublishTime(s);
        work.setWorkID(workID);
        work.setName(name);
        work.setPublishID(publishID);
        work.setPublishName(publishName);
        work.setAcceptID(acceptID);
        work.setAcceptName(acceptName);
        work.setIntroduce(introduce);
        work.setWorkfile(workfile);
        work.setProcess(process);
        String uuid = UUID.randomUUID().toString();
        work.setUuid(uuid);
        workMapper.insertWork(work);
        return RetResponse.makeOKRsp("ok");
//        return RetResponse.makeErrRsp("查无数据");
    }
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public RetResult<String> modify(@RequestParam("file") MultipartFile file,
                                    @RequestParam("uuid") String uuid,
                                    @RequestParam("type") String type,
                                    @RequestParam("name") String name) {
        String workfile = null;
//        String process = json.get("process").toString();
        try {
            String fileName = file.getOriginalFilename();
            String destFileName = Config.UPLOAD_DIR + File.separator + name + File.separator + fileName;

            File destFile = new File(destFileName);
            if (!destFile.getParentFile().exists()){
                destFile.getParentFile().mkdir();
            }
            file.transferTo(destFile);

            workfile="http://39.96.65.14/doctorfile/upload/" + name + "/" + fileName;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return RetResponse.makeErrRsp("文件上传失败");
        }
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
