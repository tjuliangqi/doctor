package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.ArticleBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ArticleMapper {

    int insertArticle(ArticleBean articleBean);

}
