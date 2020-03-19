package cn.tju.doctor.service;

import cn.tju.doctor.Config;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.utils.EsUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoServer {

    public static List<Map<String,String>> getVideo() throws IOException {
        EsUtils esUtils = new EsUtils();
        List<Map<String,String>> result = new ArrayList<>();
        RestHighLevelClient client = esUtils.getConnection();
        String perfix = "http://39.96.65.14";
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.termQuery("ifVideo","1")).sort("writeTime", SortOrder.DESC).size(200);
        SearchRequest searchRequest = new SearchRequest(Config.CAR_INDEX);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            System.out.println(e);
        }
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits){
            Map<String,Object> map = searchHit.getSourceAsMap();
            Map<String,String> resultMap = new HashMap<>();
            if (result.size()>8){
                break;
            }
            if (map.get("videoURL").toString().length()>3){
                resultMap.put("videoId",map.get("uuid").toString());
                resultMap.put("title",map.get("title").toString());
                resultMap.put("pic",perfix + map.get("picURL").toString());
                resultMap.put("views",map.get("views").toString());
                resultMap.put("writeTime",map.get("writeTime").toString());
                resultMap.put("author",map.get("author").toString());
                result.add(resultMap);
            }
        }

        client.close();
        return result;
    }

    public static Map<String,String> getID(String id) throws IOException {
        EsUtils esUtils = new EsUtils();
        Map<String,String> result = new HashMap<>();
        RestHighLevelClient client = esUtils.getConnection();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.termQuery("uuid",id)).size(1);
        SearchRequest searchRequest = new SearchRequest(Config.CAR_INDEX);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            System.out.println(e);
        }
        client.close();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        Map<String,Object> map = searchHits[0].getSourceAsMap();
        for (String each : map.keySet()){
            result.put(each,map.get(each).toString().replace("['","").replace("']",""));
        }
        return result;

    }

    public static void main(String[] args) throws IOException {
        getVideo();
    }

}
