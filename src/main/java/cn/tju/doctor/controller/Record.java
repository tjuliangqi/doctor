//package cn.tju.doctor.controller;
//
//import cn.tju.doctor.dao.ProjectMapper;
//import cn.tju.doctor.dao.ProjectfundingMapper;
//import cn.tju.doctor.daomain.Projectfunding;
//import cn.tju.doctor.daomain.RetResponse;
//import cn.tju.doctor.daomain.RetResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/record")
//public class Record {
//
//    @Autowired
//    ProjectMapper projectMapper;
//    @Autowired
//    ProjectfundingMapper projectfundingMapper;
//    @RequestMapping(value = "/details", method = RequestMethod.POST)
//    public RetResult<Map> details(@RequestBody Map<String,String> map){
//        String go = map.get("go");
//        List<Projectfunding> list = projectfundingMapper.getProjectfundingByGo(go);
//        Map<String,List> result = new HashMap<>();
//        List<Map<String,String>> health = new ArrayList<>();
//        health.add(new HashMap<String, String>(){{
//            put("category","注册人数");
//            put("count","232");
//            put("mount","112");
//        }});
//        health.add(new HashMap<String, String>(){{
//            put("category","在线活跃人数");
//            put("count","123");
//            put("mount","4454");
//        }});
//        health.add(new HashMap<String, String>(){{
//            put("category","转发次数");
//            put("count","123");
//            put("mount","645");
//        }});
//        List<Map<String,String>> detail = new ArrayList<>();
//        detail.add(new HashMap<String, String>(){{
//            put("videoName","视频1");
//            put("category","观看");
//            put("count","2521");
//            put("money","1.2");
//        }});
//        detail.add(new HashMap<String, String>(){{
//            put("videoName","视频2");
//            put("category","转发");
//            put("count","2521");
//            put("money","1.2");
//        }});
//        detail.add(new HashMap<String, String>(){{
//            put("videoName","视频3");
//            put("category","点赞");
//            put("count","2521");
//            put("money","1.2");
//        }});
//
//        int[] mount = new int[list.size()];
//        mount[0] = 1000;
//        mount[1] = 1000;
//        mount[2] = 2000;
//        mount[3] = 1000;
//        mount[4] = 5000;
//        mount[5] = 6000;
//        mount[6] = 2000;
//        mount[7] = 1000;
//        mount[8] = 10000;
//        mount[9] = 10000;
//        mount[10] = 12000;
//        List article = new ArrayList();
//        int count = 0;
//        for (Projectfunding projectfunding : list){
//            Map<String,Object> map1 = new HashMap<>();
////            String name = projectMapper.getProjectByProjectID(projectfunding.getProjectID()).get(0).getName();
//            map1.put("number",projectfunding.getNumber());
//            map1.put("videoNumber","f2d65d21960c4d9c9cfa9403852e7744");
//            map1.put("mount",mount[count]);
//            map1.put("details",detail);
//            article.add(map1);
//            count++;
//        }
//        result.put("article",article);
//        result.put("health",health);
//        return RetResponse.makeOKRsp(result);
//    }
//
//
//}
