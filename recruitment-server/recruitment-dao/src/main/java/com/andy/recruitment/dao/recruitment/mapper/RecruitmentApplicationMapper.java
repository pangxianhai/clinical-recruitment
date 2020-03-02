package com.andy.recruitment.dao.recruitment.mapper;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 招募信息申请mapper
 *
 * @author 庞先海 2019-01-24
 */
@Repository
public interface RecruitmentApplicationMapper {

    /**
     * 分页查询招募申请记录信息
     *
     * @param queryParam 查询参数
     * @param page       分页参数
     * @return 招募申请信息列表
     */
    List<RecruitmentApplicationDO> select(RecruitmentApplicationQuery queryParam, Page page);

    /**
     * 查询招募申请记录信息
     *
     * @param queryParam 查询参数
     * @return 招募申请信息列表
     */
    List<RecruitmentApplicationDO> select(RecruitmentApplicationQuery queryParam);

    /**
     * 插入招募申请记录信息
     *
     * @param applicationDO 招募申请记录信息
     * @return 插入数量
     */
    int insert(RecruitmentApplicationDO applicationDO);

    /**
     * 更新招募申请记录信息
     *
     * @param applicationDO 招募申请记录信息
     * @return 更新数量
     */
    int update(RecruitmentApplicationDO applicationDO);
}
