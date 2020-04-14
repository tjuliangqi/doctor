//package cn.tju.doctor.controller;
//
//import cn.tju.doctor.daomain.RetResponse;
//import cn.tju.doctor.daomain.RetResult;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//import static cn.tju.doctor.service.VideoServer.getID;
//import static cn.tju.doctor.service.VideoServer.getVideo;
//@RestController
//@RequestMapping("/video")
//public class VideoController {
//
//    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
//    public RetResult<List<Map<String,String>>> search() {
//        List<Map<String,String>> result = null;
//        try {
//            result = getVideo();
//        } catch (IOException e) {
//            e.printStackTrace();
//            RetResponse.makeErrRsp("查询出错");
//        }
//        return RetResponse.makeOKRsp(result);
//    }
//
//    @RequestMapping(value = "/searchID", method = RequestMethod.POST)
//    public RetResult<Map<String,String>> searchID(@RequestBody Map map) {
//        Map<String,String> result = null;
//        try {
//            result = getID(map.get("videoId").toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            RetResponse.makeErrRsp("查询出错");
//        }
//        return RetResponse.makeOKRsp(result);
//    }
//
//}
