package cn.tju.doctor.dao;


import cn.tju.doctor.daomain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    List<User> getUserByEmail(@Param("email") String email);
    List<User> getUserByUsername(@Param("username") String username);
    List<User> getUserByToken(@Param("token") String token);
    List<User> getUserByArea(@Param("area") String area);
    List<User> getUserByAuthorID(@Param("authorID") String authorID);
    List<User> getUserByTest(@Param("test") String test);
    List<User> getUserByType(@Param("type") String type);
    List<User> getUserByCompany(@Param("company") String company,@Param("type") String type);
    List<String> selectByName(@Param("company") String company);
    int insertUser(@Param("user") User user);
    int deleteUserById(@Param("authorID") String authorID);
    int updateUser(@Param("user") User user);
}
