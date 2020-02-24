package cn.tju.doctor.controller;

import cn.tju.doctor.dao.ProjectFeaMapper;
import cn.tju.doctor.daomain.ProjectFea;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projectFea")
public class ProjectFeaController {
    @Autowired
    ProjectFeaMapper projectFeaMapper;
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public RetResult<List> test(@RequestBody Map<String,String> map){
        List<ProjectFea> list = projectFeaMapper.getByNumber(map.get("number"));
        System.out.println(list);
        return RetResponse.makeOKRsp(list);
    }

}
