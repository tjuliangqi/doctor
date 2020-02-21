package cn.tju.doctor.utils;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONException;

import java.util.Map;

import static cn.tju.doctor.utils.JsonToMapUtils.strToMap;

public class ToBuildersUtils {

    public static SearchSourceBuilder queryTextToBuilder (String type, String value, boolean ifPrepara, String preparaString, int page) throws JSONException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder builder0;
        QueryBuilder builderAdd;
        System.out.println(preparaString);
        // {0:标题, 1:推荐}
        if (ifPrepara == false || preparaString.equals("{}")) {
            if (type.equals("0")) {
                builder0 = QueryBuilders.matchQuery("title", value);
                searchSourceBuilder.from(page)
                        .size(10)
                        .query(builder0);
            } else {
                builder0 = QueryBuilders.matchAllQuery();
                searchSourceBuilder.from(page)
                        .size(10)
                        .query(builder0);
            }
        } else { // 只筛选0、1}
            if (type.equals("0")) {
                builder0 = QueryBuilders.matchQuery("title", value);
                builderAdd = addFilterBuilder(builder0, preparaString);
                searchSourceBuilder.from(page)
                        .size(10)
                        .query(builderAdd);
            } else {
                builder0 = QueryBuilders.matchAllQuery();
                builderAdd = addFilterBuilder(builder0, preparaString);
                searchSourceBuilder.from(page)
                        .size(10)
                        .query(builderAdd);
            }
        }

        return searchSourceBuilder;
    }

    public static SearchSourceBuilder queryTextToBuilder2 (String type, String value) throws JSONException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder builder0;

        if (type.equals("0")) {
            builder0 = QueryBuilders.matchQuery("title", value);
            searchSourceBuilder.from(0)
                    .size(100)
                    .sort("writeTime",SortOrder.DESC)
                    .query(builder0);
        } else {
            builder0 = QueryBuilders.matchAllQuery();
            searchSourceBuilder.from(0)
                    .size(100)
                    .sort("writeTime",SortOrder.DESC)
                    .query(builder0);
        }


        return searchSourceBuilder;
    }
    /**
     * @ description this function add builder0 and filterbuilder
     * @param builder0
     * @param preparaString
     * @return builderAdd
     * @throws JSONException
     */
    // 过滤 label，part，writeTime
    public static QueryBuilder addFilterBuilder(QueryBuilder builder0, String preparaString) throws JSONException {
        System.out.println("start Filter.....");
        Map map = strToMap(preparaString);
        // try filtrate label
        try {
            String label = map.get("label").toString().replace("[\"", "").replace("\"]", "");
            String[] labelList = label.split("\",\"");
            QueryBuilder builder1 = QueryBuilders.matchQuery("label", labelList[0]);
            for(int i =1;i<labelList.length;i++){
                builder1 = QueryBuilders.boolQuery()
                        .should(builder1)
                        .should(QueryBuilders.matchQuery("label",labelList[i]));
            }
            builder0 = QueryBuilders.boolQuery()
                    .must(builder0)
                    .must(builder1);
        }catch (Exception e){
            System.out.println("No filtration label");
        }
        // try filtrate part
        try {
            String part = map.get("part").toString().replace("[\"", "").replace("\"]", "");
            String[] partList = part.split("\",\"");
            QueryBuilder builder1 = QueryBuilders.matchQuery("part", partList[0]);
            for(int i =1;i<partList.length;i++){
                builder1 = QueryBuilders.boolQuery()
                        .should(builder1)
                        .should(QueryBuilders.matchQuery("part",partList[i]));
            }
            builder0 = QueryBuilders.boolQuery()
                    .must(builder0)
                    .must(builder1);
        }catch (Exception e){
            System.out.println("No filtration part");
        }
        // try filtrate writeTime
        try {
            String writeTime = map.get("writeTime").toString().replace("[\"", "").replace("\"]", "");
            String[] writeTimeList = writeTime.split("\",\"");
            String beginTime = writeTimeList[0];
            String endTime = writeTimeList[1];
            QueryBuilder builder1 = QueryBuilders.rangeQuery("writeTime")
                            .from(beginTime)
                            .to(endTime);

            builder0 = QueryBuilders.boolQuery()
                    .must(builder0)
                    .must(builder1);
        }catch (Exception e){
            System.out.println("No filtration writeTime");
        }


        return builder0;
    }


}
