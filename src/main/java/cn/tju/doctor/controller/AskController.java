package cn.tju.doctor.controller;

import cn.tju.doctor.dao.ArticleMapper;
import cn.tju.doctor.daomain.ArticleBean;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static cn.tju.doctor.service.AskService.*;

@RestController

@RequestMapping("/article")
public class AskController {
    @Autowired
    ArticleMapper articleMapper;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<Object> search(@RequestBody Map<String,String> map) throws IOException, JSONException {
        //RetResult retResult = new RetResult();
        String type = String.valueOf(map.get("type"));
        String value = String.valueOf(map.get("value"));
        Boolean ifPrepara = Boolean.valueOf(map.get("ifPrepara"));
        String preparaString = String.valueOf(map.get("preparaString"));
        Integer page = Integer.valueOf(map.get("page"));

        Object articleBeans = searchList(type, value, ifPrepara, preparaString, page);
        return RetResponse.makeOKRsp(articleBeans);
    }

    @RequestMapping(value = "/prepara", method = RequestMethod.POST)
    public RetResult<Map> prepara(@RequestBody Map<String,String> map) throws IOException, JSONException {
        //RetResult retResult = new RetResult();
        String type = String.valueOf(map.get("type"));
        String value = String.valueOf(map.get("value"));
        Map<String, Object> selectTags = articlePrepara(type, value);

        return RetResponse.makeOKRsp(selectTags);
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public RetResult<Map<String,Object>> pic(@RequestParam("ﬁleName") MultipartFile file, @RequestParam("ﬁleType") int ﬁleType) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String destFileName = "D:\\tdwy_upload" + File.separator + fileName;

            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);


            String ﬁleURL = destFileName;
            resultMap.put("ﬁleURL", ﬁleURL);

            return RetResponse.makeOKRsp(resultMap);
        } catch (Exception e) {
            return RetResponse.makeErrRsp("上传文件出错");
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RetResult<String> upload(@RequestBody Map<String,String> map) throws JSONException {
        JSONObject json = new JSONObject();
        //RetResult retResult = new RetResult();
        String title = String.valueOf(map.get("title"));
        String source = String.valueOf(map.get("source"));
        String writeTime = String.valueOf(map.get("writeTime"));
        String creatTime = String.valueOf(map.get("creatTime"));
        String sourceURL = String.valueOf(map.get("sourceURL"));
        String fulltext = String.valueOf(map.get("fulltext"));
        String picURL = String.valueOf(map.get("picURL"));
        String videoURL = String.valueOf(map.get("videoURL"));
        String label = String.valueOf(map.get("label"));
        String part = String.valueOf(map.get("part"));
        Integer ifVideo = Integer.valueOf(map.get("ifVideo"));
        String uuid = UUID.randomUUID().toString();
        json.put("title", title);
        json.put("source", source);
        json.put("writeTime", writeTime);
        json.put("creatTime", creatTime);
        json.put("sourceURL", sourceURL);
        json.put("fulltext", fulltext);
        json.put("picURL", picURL);
        json.put("videoURL", videoURL);
        json.put("label", label);
        json.put("part", part);
        json.put("ifVideo", ifVideo);

        ArticleBean articleBean = new ArticleBean();
        articleBean.setUuid(uuid);
        articleBean.setTitle(title);
        articleBean.setSourceURL(sourceURL);
        articleBean.setSource(source);
        articleBean.setWriteTime(writeTime);
        articleBean.setCreatTime(creatTime);
        articleBean.setFulltext(fulltext);
        articleBean.setPicURL(picURL);
        articleBean.setVideoURL(videoURL);
        articleBean.setLabel(label);
        articleBean.setPart(part);
        articleBean.setIfVideo(ifVideo);

        //String result = addData(json);
        int result = articleMapper.insertArticle(articleBean);
        return RetResponse.makeOKRsp("ok");
    }
}
