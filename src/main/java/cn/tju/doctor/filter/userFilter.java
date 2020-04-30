package cn.tju.doctor.filter;//package cn.tju.tdwy.filter;
//
//
//import cn.tju.tdwy.dao.UserMapper;
//import cn.tju.tdwy.daomain.User;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@WebFilter(urlPatterns={"/*"}, filterName="tokenAuthorFilter")
//public class userFilter implements Filter {
//    @Autowired
//    UserMapper userMapper;
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest)request;
//        String requestURI = httpRequest.getRequestURI();
//        if (requestURI.contains("user")) {
//            //不过滤走
//            chain.doFilter(request, response);
//            System.out.println("===不进过滤器");
//        }else {
//            String token = httpRequest.getHeader("token");
//            List<User> list = userMapper.getUserByToken(token);
//            if (list.size() != 0){
//                chain.doFilter(request, response);
//            }else {
//                Map<String,Object> map = new HashMap<>();
//                map.put("code",40004);
//                JSONObject JSONObj = new JSONObject(map);
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("text/html; charset=utf-8");
//                response.getOutputStream().write(JSONObj.toString().getBytes());
//                response.getOutputStream().flush();
//                response.getOutputStream().close();
//            }
//        }
//
//
//    }
//}
