package cn.tju.doctor.dao;

import cn.tju.doctor.daomain.ArticleBean;
import cn.tju.doctor.daomain.Base;
import cn.tju.doctor.daomain.Eachfunding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EachFundingMapper {

    List<Eachfunding> selectEachFundingByNumber(@Param("number")String number);
    List<Eachfunding> selectEachFundingByUUID(@Param("uuid") String uuid);
    int updateEachFunding(Eachfunding eachfunding);

}
