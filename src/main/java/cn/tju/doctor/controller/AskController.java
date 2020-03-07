package cn.tju.doctor.controller;

import cn.tju.doctor.Config;
import cn.tju.doctor.dao.ArticleMapper;
import cn.tju.doctor.dao.DataMapper;
import cn.tju.doctor.daomain.ArticleBean;
import cn.tju.doctor.daomain.Data;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import cn.tju.doctor.utils.DateUtil;
import cn.tju.doctor.utils.EsUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

import static cn.tju.doctor.service.AskService.articlePrepara;
import static cn.tju.doctor.service.AskService.searchList;

@RestController

@RequestMapping("/article")
public class AskController {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    DataMapper dataMapper;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RetResult<Object> search(@RequestBody Map<String,String> map) throws IOException, JSONException {
        //RetResult retResult = new RetResult();
        String type = String.valueOf(map.get("type"));
        String value = String.valueOf(map.get("value"));
        Boolean ifPrepara = Boolean.valueOf(map.get("ifPrepara"));
        String preparaString = String.valueOf(map.get("preparaString"));
        Integer page = Integer.valueOf(map.get("page"));

        Object viewBeans = searchList(type, value, ifPrepara, preparaString, page);
        return RetResponse.makeOKRsp(viewBeans);
    }

    @RequestMapping(value = "/prepara", method = RequestMethod.POST)
    public RetResult<Map> prepara(@RequestBody Map<String,String> map) throws IOException, JSONException {
        //RetResult retResult = new RetResult();
        String type = String.valueOf(map.get("type"));
        String value = String.valueOf(map.get("value"));
        Map<String, Object> selectTags = articlePrepara(type, value);

        return RetResponse.makeOKRsp(selectTags);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RetResult<String> upload(@RequestBody Map<String,String> map) throws JSONException {

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
//        JSONObject json = new JSONObject();
//        json.put("title", title);
//        json.put("source", source);
//        json.put("writeTime", writeTime);
//        json.put("creatTime", creatTime);
//        json.put("sourceURL", sourceURL);
//        json.put("fullContent", fullContent);
//        json.put("picURL", picURL);
//        json.put("videoURL", videoURL);
//        json.put("label", label);
//        json.put("part", part);
//        json.put("ifVideo", ifVideo);
//        String result = addDataToES(json);
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
        articleBean.setViews(0);
        articleBean.setLikes(0);
        articleBean.setDownload(0);
        articleBean.setBerecord(0);

        System.out.println(articleBean);
        int result = articleMapper.insertArticle(articleBean);
        return RetResponse.makeOKRsp("ok");
    }
    @RequestMapping(value = "/searchById", method = RequestMethod.POST)
    public RetResult<ArticleBean> searchById(@RequestBody Map<String,String> map) throws JSONException {
        String uuid = map.get("uuid");
        EsUtils esUtils = new EsUtils();
        RestHighLevelClient client = esUtils.client;

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
        SearchResponse searchResponse;
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

    @RequestMapping(value = "/updatedata", method = RequestMethod.POST)
    public RetResult<Object> updatedata (@RequestBody Map<String,String> map) {
        //RetResult retResult = new RetResult();
        String type = String.valueOf(map.get("type"));
        String uuid = String.valueOf(map.get("uuid"));
        DateUtil.update(dataMapper,Integer.valueOf(type),DateUtil.gainDate());
        DateUtil.update(dataMapper,Integer.valueOf(type),"2000-01-01");
        /*更新对应uuid的各项参数（view，download...）可参考DateUtil*/

        /*更新文章对应作者的各项参数（view，download...）可参考DateUtil,同时根据参数给用户发放经费，来源是医之研*/
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/getdata", method = RequestMethod.POST)
    public RetResult<Object> data (@RequestBody Map<String,String> map) {
        //RetResult retResult = new RetResult();
        List<Data> list = new ArrayList<>();
        Data data1 = dataMapper.getDataById("2000-01-01");
        Data data2 = dataMapper.getDataById(DateUtil.gainDate());
        list.add(data1);
        list.add(data2);
        return RetResponse.makeOKRsp();
    }

}
