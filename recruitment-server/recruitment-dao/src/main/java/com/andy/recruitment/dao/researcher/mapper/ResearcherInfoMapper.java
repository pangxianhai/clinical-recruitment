package com.andy.recruitment.dao.researcher.mapper;

import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.soyoung.base.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 研究员mapper接口
 *
 * @author 庞先海 2020-01-27
 */
@Repository
public interface ResearcherInfoMapper {

    /**
     * 研究员查询
     *
     * @param query 查询参数
     * @return 研究员信息
     */
    List<ResearcherInfoDO> select(ResearcherQuery query);

    /**
     * 研究员分页查询
     *
     * @param query 查询参数
     * @param page  分页信息
     * @return 研究员信息
     */
    List<ResearcherInfoDO> select(ResearcherQuery query, Page page);

    /**
     * 研究员信息更新
     *
     * @param researcherInfoDo 研究员信息
     * @return 更新数量
     */
    int update(ResearcherInfoDO researcherInfoDo);

    /**
     * 插入研究员
     *
     * @param researcherInfoDo 研究员信息
     * @return 插入数量
     */
    int insert(ResearcherInfoDO researcherInfoDo);
}
