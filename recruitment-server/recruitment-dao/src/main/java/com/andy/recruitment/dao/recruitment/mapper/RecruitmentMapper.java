package com.andy.recruitment.dao.recruitment.mapper;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 招募信息Mapper
 *
 * @author 庞先海 2018-12-29
 */
@Repository
public interface RecruitmentMapper {

    /**
     * 招募信息分页查询
     *
     * @param queryParam 查询参数
     * @param page       分页参数
     * @return 招募信息列表
     */
    List<RecruitmentInfoDO> select(RecruitmentQuery queryParam, Page page);

    /**
     * 招募信息查询
     *
     * @param queryParam 查询参数
     * @return 招募信息列表
     */
    List<RecruitmentInfoDO> select(RecruitmentQuery queryParam);

    /**
     * 招募信息更新
     *
     * @param recruitmentInfoDO 招募信息
     * @return 更新数量
     */
    int update(RecruitmentInfoDO recruitmentInfoDO);

    /**
     * 插入招募信息
     *
     * @param recruitmentInfoDO 招募信息
     * @return 插入数量
     */
    int insert(RecruitmentInfoDO recruitmentInfoDO);
}
