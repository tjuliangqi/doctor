package cn.tju.doctor.controller;

import cn.tju.doctor.Config;
import cn.tju.doctor.dao.ArticleMapper;
import cn.tju.doctor.daomain.ArticleBean;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import cn.tju.doctor.utils.EsUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
        String fullContent = String.valueOf(map.get("fullContent"));
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
        json.put("fullContent", fullContent);
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
        articleBean.setFullContent(fullContent);
        articleBean.setPicURL(picURL);
        articleBean.setVideoURL(videoURL);
        articleBean.setLabel(label);
        articleBean.setPart(part);
        articleBean.setIfVideo(ifVideo);

        //String result = addData(json);
        System.out.println(articleBean);
        int result = articleMapper.insertArticle(articleBean);
        return RetResponse.makeOKRsp("ok");
    }
    @RequestMapping(value = "/searchById", method = RequestMethod.POST)
    public RetResult<ArticleBean> searchById(@RequestBody Map<String,String> map) throws JSONException {
        String uuid = map.get("uuid");
        EsUtils esUtils = new EsUtils();
        RestHighLevelClient client = esUtils.client;
        ArrayList<ArticleBean> articleBeans = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchQuery("uuid", uuid));
        SearchRequest searchRequest = new SearchRequest(Config.CAR_INDEX);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            try {
                esUtils.client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return RetResponse.makeErrRsp("未找到文章");
        }
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        if (searchHits.length < 1) {
            try {
                esUtils.client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return RetResponse.makeErrRsp("未找到文章");
        } else {
            ArticleBean articleBean = new ArticleBean();
            for (SearchHit searchHit : searchHits) {
                String title = (String) searchHit.getSourceAsMap().get("title");
                String source = (String) searchHit.getSourceAsMap().get("source");
                String writeTime = (String) searchHit.getSourceAsMap().get("writeTime");
                String creatTime = (String) searchHit.getSourceAsMap().get("creatTime");
                String sourceURL = (String) searchHit.getSourceAsMap().get("sourceURL");
                String fullContent = (String) searchHit.getSourceAsMap().get("fullContent");
                String picURL = (String) searchHit.getSourceAsMap().get("picURL");
                String videoURL = (String) searchHit.getSourceAsMap().get("videoURL");
                String label = (String) searchHit.getSourceAsMap().get("label");
                String part = (String) searchHit.getSourceAsMap().get("part");
                int ifVideo = (int) searchHit.getSourceAsMap().get("ifVideo");
                int likes = (int) searchHit.getSourceAsMap().get("likes");
                int views = (int) searchHit.getSourceAsMap().get("views");
                int download = (int) searchHit.getSourceAsMap().get("download");
                int berecord = (int) searchHit.getSourceAsMap().get("berecord");
                articleBean.setUuid(uuid);
                articleBean.setTitle(title);
                articleBean.setSource(source);
                articleBean.setWriteTime(writeTime);
                articleBean.setCreatTime(creatTime);
                articleBean.setSourceURL(sourceURL);
                articleBean.setFullContent(fullContent);
                articleBean.setPicURL(picURL);
                articleBean.setVideoURL(videoURL);
                articleBean.setLabel(label);
                articleBean.setPart(part);
                articleBean.setIfVideo(ifVideo);
                articleBean.setLikes(likes);
                articleBean.setViews(views);
                articleBean.setDownload(download);
                articleBean.setBerecord(berecord);


            }
            try {
                esUtils.client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return RetResponse.makeOKRsp(articleBean);
        }
    }

    @RequestMapping(value = "/searchHome", method = RequestMethod.POST)
    public RetResult<List> searchHome(@RequestBody Map<String,String> map) throws JSONException {
        String value = map.get("value");
        EsUtils esUtils = new EsUtils();
        RestHighLevelClient client = esUtils.client;
        ArrayList<Map> Beans = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(5);
        searchSourceBuilder.query(QueryBuilders.matchQuery("label", value));
        SearchRequest searchRequest = new SearchRequest(Config.CAR_INDEX);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            try {
                esUtils.client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return RetResponse.makeErrRsp("未找到文章");
        }
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        if (searchHits.length < 1) {
            try {
                esUtils.client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return RetResponse.makeErrRsp("未找到文章");
        } else {
//            ArticleBean articleBean = new ArticleBean();

            for (SearchHit searchHit : searchHits) {
                Map<String,String> map1 = new HashMap<>();
                String title = (String) searchHit.getSourceAsMap().get("title");
                String uuid = (String) searchHit.getSourceAsMap().get("uuid");
                String fullContent = (String) searchHit.getSourceAsMap().get("fullContent");
                String picURL = (String) searchHit.getSourceAsMap().get("picURL");
                map1.put("title",title);
                map1.put("uuid",uuid);
                map1.put("fullContent",fullContent.substring(0,10));
                map1.put("picURL",picURL);
                Beans.add(map1);

            }
            try {
                esUtils.client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return RetResponse.makeOKRsp(Beans);
        }
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public RetResult<Object> calculate (@RequestBody Map<String,String> map) throws IOException, JSONException {
        //RetResult retResult = new RetResult();
        String date = String.valueOf(map.get("date"));
        Map<String,String> map1 = new HashMap<>();
        Map<String,String> map2 = new HashMap<>();
        Map<String,Map<String,String>> map3 = new HashMap<>();
        map1.put("article","100");
        map1.put("views","200");
        map1.put("likes","300");
        map1.put("download","400");
        map1.put("hide","500");

        map2.put("article","1000");
        map2.put("views","2000");
        map2.put("likes","3000");
        map2.put("download","4000");
        map2.put("hide","5000");
        map3.put("day",map1);
        map3.put("all",map2);

        return RetResponse.makeOKRsp(map3);
    }

}
