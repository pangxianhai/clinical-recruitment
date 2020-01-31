package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import java.util.List;

/**
 * 招募项目服务接口
 *
 * @author 庞先海 2020-01-30
 */
public interface RecruitmentService {

    /**
     * 分页查询招募信息
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 招募信息
     */
    PageResult<RecruitmentInfoDO> getRecruitmentInfo(RecruitmentQuery queryParam, Paginator paginator);

    /**
     * 添加招募项目
     *
     * @param recruitmentInfoDo  招募项目信息
     * @param organizationIdList 研究机构列表
     * @param operator           添加人
     */
    void addRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<Long> organizationIdList, String operator);
}
