package cn.tju.doctor.service;

import cn.tju.doctor.Config;
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

        Object result;
        EsUtils esUtils = new EsUtils();
        RestHighLevelClient client = esUtils.client;
        ArrayList<ViewBean> viewBeans = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder;
        searchSourceBuilder = queryTextToBuilder(type, value, ifPrepara, preparaString, page);

        SearchRequest searchRequest = new SearchRequest(Config.CAR_INDEX);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        if (searchHits.length < 1){
            System.out.println("查询结果为空，返回空map");
        }else {
            for (SearchHit searchHit:searchHits){
                ViewBean viewBean = new ViewBean();
                System.out.println(searchHit.getSourceAsString());
                String uuid = (String)searchHit.getSourceAsMap().get("uuid");
                String title = (String)searchHit.getSourceAsMap().get("title");
                String fullContent = (String)searchHit.getSourceAsMap().get("fullContent");
                String picURL = (String)searchHit.getSourceAsMap().get("picURL");
                String label = (String)searchHit.getSourceAsMap().get("label");
                String part = (String)searchHit.getSourceAsMap().get("part");
                int likes = (int)searchHit.getSourceAsMap().get("likes");
                int views = (int)searchHit.getSourceAsMap().get("views");
                int download = (int)searchHit.getSourceAsMap().get("download");
                int berecord = (int)searchHit.getSourceAsMap().get("berecord");

                viewBean.setTitle(title);
                viewBean.setUuid(uuid);
                viewBean.setFullContent(fullContent.substring(0,10));
                viewBean.setPicURL(picURL);
                viewBean.setLabel(label);
                viewBean.setPart(part);
                viewBean.setLikes(likes);
                viewBean.setViews(views);
                viewBean.setDownload(download);
                viewBean.setBerecord(berecord);

                viewBeans.add(viewBean);
            }
        }
        try {
            esUtils.client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        result = viewBeans;
        return result;
    }


    /**
     * description 返回筛选类型接口
     * @param type type只有 0、1
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

                // find all label
                String label = (String)searchHit.getSourceAsMap().get("label");
                String[] arr = label.replace("['","").replace("']","").split("', '");
                for(int i=0; i<arr.length; i++)
                    labelList.add(arr[i]);

                // find all part
                String part = (String)searchHit.getSourceAsMap().get("part");
                partList.add(part);

                // find all writeTime
                String writeTime = (String)searchHit.getSourceAsMap().get("writeTime");
                writeTimeList.add(writeTime);
            }

            Set result1 = new HashSet(labelList);
            Set result2 = new HashSet(partList);
            String[] result3 = new String[]{writeTimeList.get(writeTimeList.size()-1), writeTimeList.get(0)};

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
        try {
            esUtils.client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return selectTags;
    }


    public static String addDataToES(JSONObject json) {
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
