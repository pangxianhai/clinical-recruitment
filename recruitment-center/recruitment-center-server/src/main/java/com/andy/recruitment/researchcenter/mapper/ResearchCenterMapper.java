package com.andy.recruitment.researchcenter.mapper;

import com.andy.recruitment.researchcenter.model.ResearchCenterDO;
import com.andy.recruitment.researchcenter.model.ResearchCenterQueryParam;
import com.xgimi.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 研究中心mapper
 *
 * @author 庞先海 2019-01-08
 */
@Repository
public interface ResearchCenterMapper {

    /**
     * 研究中心分页查询
     *
     * @param queryParam 查询参数
     * @param page       分页参数
     * @return 研究中心列表
     */
    List<ResearchCenterDO> select(ResearchCenterQueryParam queryParam, Page page);


    /**
     * 研究中心查询
     *
     * @param queryParam 查询参数
     * @return 研究中心列表
     */
    List<ResearchCenterDO> select(ResearchCenterQueryParam queryParam);

    /**
     * 插入研究中心
     *
     * @param researchCenterDO 研究中心信息
     * @return 插入数量
     */
    int insert(ResearchCenterDO researchCenterDO);

    /**
     * 更新研究中心
     *
     * @param researchCenterDO 研究中心信息
     * @return 更新数量
     */
    int update(ResearchCenterDO researchCenterDO);

    /**
     * 删除研究中心
     *
     * @param centerId 研究中心ID
     * @return 删除数量
     */
    int delete(Long centerId);
}
