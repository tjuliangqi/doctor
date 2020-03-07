package cn.tju.doctor.service;

import cn.tju.doctor.Config;
import cn.tju.doctor.daomain.ArticleBean;
import cn.tju.doctor.daomain.ViewBean;
import cn.tju.doctor.utils.EsUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import static cn.tju.doctor.utils.ToBuildersUtils.queryTextToBuilder;
import static cn.tju.doctor.utils.ToBuildersUtils.queryTextToBuilder2;

public class AskService {
    /**
     *
     * @param type
     * @param value
     * @param ifPrepara
     * @param preparaString
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static Object searchList(String type, String value, boolean ifPrepara, String preparaString, Integer page) throws IOException, JSONException {

        Map<String,Object> resultMap = new HashMap<>();
        Object result = new Object();
        EsUtils esUtils = new EsUtils();
        RestHighLevelClient client = esUtils.client;
        ArrayList<Object> articleBeans = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder;
        searchSourceBuilder = queryTextToBuilder(type, value, ifPrepara, preparaString, page);

            SearchRequest searchRequest = new SearchRequest(Config.CAR_INDEX);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest);
            SearchHit[] searchHits = searchResponse.getHits().getHits();

            if (searchHits.length < 1){
                System.out.println("查询结果为空，返回空map");
            }else {
//                ArticleBean articleBean = new ArticleBean();
                ViewBean viewBean = new ViewBean();
                for (SearchHit searchHit:searchHits){
                    //System.out.println(searchHit.getSourceAsString());
                    String uuid = (String)searchHit.getSourceAsMap().get("uuid");
                    String title = (String)searchHit.getSourceAsMap().get("title");
                    String source = (String)searchHit.getSourceAsMap().get("source");
                    String writeTime = (String)searchHit.getSourceAsMap().get("writeTime");
                    String creatTime = (String)searchHit.getSourceAsMap().get("creatTime");
                    String sourceURL = (String)searchHit.getSourceAsMap().get("sourceURL");
                    String fullContent = (String)searchHit.getSourceAsMap().get("fullContent");
                    String picURL = (String)searchHit.getSourceAsMap().get("picURL");
                    String videoURL = (String)searchHit.getSourceAsMap().get("videoURL");
                    String label = (String)searchHit.getSourceAsMap().get("label");
                    String part = (String)searchHit.getSourceAsMap().get("part");
                    int ifVideo = (int)searchHit.getSourceAsMap().get("ifVideo");
                    int likes = (int)searchHit.getSourceAsMap().get("likes");
                    int views = (int)searchHit.getSourceAsMap().get("views");
                    int download = (int)searchHit.getSourceAsMap().get("download");
                    int berecord = (int)searchHit.getSourceAsMap().get("berecord");

                   viewBean.setTitle(title);
                   viewBean.setUuid(uuid);
//                   articleBean.setSource(source);
//                   articleBean.setWriteTime(writeTime);
//                   articleBean.setCreatTime(creatTime);
//                   articleBean.setSourceURL(sourceURL);
                    viewBean.setFullContent(fullContent);
                    viewBean.setPicURL(picURL);
//                   articleBean.setVideoURL(videoURL);
                    viewBean.setLabel(label);
                    viewBean.setPart(part);
//                   articleBean.setIfVideo(ifVideo);
                    viewBean.setLikes(likes);
                    viewBean.setViews(views);
                    viewBean.setDownload(download);
                    viewBean.setBerecord(berecord);

                   articleBeans.add(viewBean);
                }
                resultMap.put("result",articleBeans);
            }
            result = articleBeans;

        //return resultMap;
        return result;
    }


    /**
     * description 返回筛选类型接口，伴随和碰撞不筛选 0、2、3是因为只返回一条没必要筛选
     * @param type type只有 1、4、5 三种
     * @param value
     * @return
     * @throws IOException
     */
    public static Map<String, Object> articlePrepara(String type, String value) throws IOException, JSONException {

        EsUtils esUtils = new EsUtils();
        RestHighLevelClient client = esUtils.client;
        SearchSourceBuilder searchSourceBuilder;
        searchSourceBuilder = queryTextToBuilder2(type, value);
        SearchRequest searchRequest;

        searchRequest = new SearchRequest(Config.CAR_INDEX);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        ArrayList<String> labelList = new ArrayList<>();
        ArrayList<String> partList = new ArrayList<>();
        ArrayList<String> writeTimeList = new ArrayList<>();

        Map<String,Object> selectTags = new HashMap<>();
        if (searchHits.length < 2){
            System.out.println("查询条数过少，无法筛选");
        }else {
            for (SearchHit searchHit:searchHits){
                //System.out.println(searchHit.getSourceAsString());
                // find all carNumType
                String label = (String)searchHit.getSourceAsMap().get("label");
                String[] arr = label.replace("['","").replace("']","").split("', '");
                for(int i=0; i<arr.length; i++)
                    labelList.add(arr[i]);
                // labelList.add(label);
                // find all carNumColor
                String part = (String)searchHit.getSourceAsMap().get("part");
                partList.add(part);
                // find all carColor
                String writeTime = (String)searchHit.getSourceAsMap().get("writeTime");
                writeTimeList.add(writeTime);
            }

            Set result1 = new HashSet(labelList);
            Set result2 = new HashSet(partList);
            String[] result3 = new String[]{writeTimeList.get(0), writeTimeList.get(writeTimeList.size()-1)};

            if (result1.size()>10) {
                List <String> list = new ArrayList<String>(result1);
                result1 = new HashSet(list.subList(0,10));
            }
            if (result2.size()>10) {
                List <String> list = new ArrayList<String>(result2);
                result2 = new HashSet(list.subList(0,10));
            }


            selectTags.put("label",result1);
            selectTags.put("part",result2);
            selectTags.put("writeTime",result3);
        }
        return selectTags;
    }


    public static String addData(JSONObject json) {
        EsUtils esUtils = new EsUtils();
        RestHighLevelClient client = esUtils.client;
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        try{
            IndexRequest request = new IndexRequest("post");
            request.index(Config.CAR_INDEX)
                    .id(uuid)
                    .source(json, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return "ok";
        }catch (Exception e){
            return "上传失败";
        }
    }


}
