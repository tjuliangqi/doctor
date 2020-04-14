//package cn.tju.doctor.controller;
//
//import cn.tju.doctor.dao.BaseMapper;
//import cn.tju.doctor.daomain.Base;
//import cn.tju.doctor.daomain.RetResponse;
//import cn.tju.doctor.daomain.RetResult;
//import com.google.gson.Gson;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//
//@RequestMapping("/base")
//public class BaseController {
//    @Autowired
//    BaseMapper baseMapper;
//    @RequestMapping(value = "/select", method = RequestMethod.POST)
//    public RetResult<Base> getBase(){
//        List list = baseMapper.getBaseList();
//        Base base = (Base) list.get(0);
////        Map<String,Float> map = new HashMap<>();
////        map.put("article",base.getArticle());
////        map.put("view",base.getView());
////        map.put("download",base.getDownload());
////        map.put("like",base.getLike());
////        map.put("hide",base.getHide());
//        return RetResponse.makeOKRsp(base);
//    }
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public RetResult<String> updateBase(Base base){
//        baseMapper.updateBase(base);
//        return RetResponse.makeOKRsp("ok");
//    }
//}
