package cn.tju.doctor.controller;

import cn.tju.doctor.daomain.*;
import cn.tju.doctor.utils.JsonToMapUtils;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/record")
public class RecordController {
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public RetResult<Map> details(@RequestBody Map json) {
        System.out.println(json.get("authorID"));

        Map<String,List> result = new HashMap<>();
//        RecordArticle ra = new RecordArticle();
//        RecordDetail rd = new RecordDetail();
        List<RecordDetail> lRD = new ArrayList<>();
        lRD.add(new RecordDetail("f2d65d21960c4d9c9cfa9403852e7744","观看",234,1.2,112));
        lRD.add(new RecordDetail("f2d65d21960c4d9c9cfa9403852e7744","点赞",221,1.2,3423));
        lRD.add(new RecordDetail("f2d65d21960c4d9c9cfa9403852e7744","转发",2521,1.2,1451));

        List<RecordArticle> lRA = new ArrayList<>();
        lRA.add(new RecordArticle("天津大学测试项目","f2d65d21960c4d9c9cfa9403852e7744",1.0,lRD));
        lRA.add(new RecordArticle("天津大学测试项目","f2d65d21960c4d9c9cfa9403852e7744",1.0,lRD));
        lRA.add(new RecordArticle("天津大学测试项目","f2d65d21960c4d9c9cfa9403852e7744",1.0,lRD));
        result.put("article",lRA);
        result.put("health",lRD);
        return RetResponse.makeOKRsp(result);
//        return RetResponse.makeErrRsp("查无数据");
    }
}
