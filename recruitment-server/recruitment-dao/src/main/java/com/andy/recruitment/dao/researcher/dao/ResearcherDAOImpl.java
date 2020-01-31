package com.andy.recruitment.dao.researcher.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.dao.researcher.mapper.ResearcherInfoMapper;
import com.soyoung.base.mybatis.paginator.Page;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.PageUtil;
import com.soyoung.base.page.Paginator;
import com.soyoung.base.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 研究员DAO实现
 *
 * @author 庞先海 2020-01-27
 */
@Service
public class ResearcherDAOImpl implements ResearcherDAO {

    private final ResearcherInfoMapper researcherInfoMapper;

    @Autowired
    public ResearcherDAOImpl(ResearcherInfoMapper researcherInfoMapper) {
        this.researcherInfoMapper = researcherInfoMapper;
    }

    @Override
    public PageResult<ResearcherInfoDO> getResearcherInfo(ResearcherQuery query, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<ResearcherInfoDO> researcherInfoDoList = this.researcherInfoMapper.select(query, page);
        return new PageResult<>(researcherInfoDoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public void updateResearcherInfo(ResearcherInfoDO researcherInfoDo, String operator) {
        AssertUtil.assertNull(researcherInfoDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCHER_ID_EMPTY);
        });
        researcherInfoDo.setUpdatedBy(operator);
        researcherInfoDo.setUpdatedTime(LocalDateTime.now());
        int count = this.researcherInfoMapper.update(researcherInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCHER_UPDATE_FAILED);
        });
    }

    @Override
    public void addResearcherInfo(ResearcherInfoDO researcherInfoDo, String operator) {
        researcherInfoDo.setCreatedBy(operator);
        researcherInfoDo.setCreatedTime(LocalDateTime.now());
        int count = this.researcherInfoMapper.insert(researcherInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCHER_ADD_FAILED);
        });
    }
}