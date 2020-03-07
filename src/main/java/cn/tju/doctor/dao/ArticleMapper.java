package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.ArticleBean;
import cn.tju.doctor.daomain.Base;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleMapper {

    int insertArticle(ArticleBean articleBean);
    List<Base> selectSumByDate(String date);

}
