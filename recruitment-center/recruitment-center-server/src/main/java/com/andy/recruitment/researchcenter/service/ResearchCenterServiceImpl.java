package com.andy.recruitment.researchcenter.service;

import com.andy.recruitment.exception.RecruitmentErrorCode;
import com.andy.recruitment.exception.RecruitmentException;
import com.andy.recruitment.researchcenter.mapper.ResearchCenterMapper;
import com.andy.recruitment.researchcenter.model.ResearchCenterDO;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.researchcenter.model.ResearchCenterQueryParam;
import com.andy.recruitment.researchcenter.util.ResearchCenterUtil;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 研究中心服务实现
 *
 * @author 庞先海 2019-01-08
 */
@Service
public class ResearchCenterServiceImpl implements ResearchCenterService {

    private final ResearchCenterMapper researchCenterMapper;

    @Autowired
    public ResearchCenterServiceImpl(ResearchCenterMapper researchCenterMapper) {
        this.researchCenterMapper = researchCenterMapper;
    }

    @Override
    public void addResearchCenter(ResearchCenterInfo centerInfo, String operator) {
        ResearchCenterDO researchCenterDO = ResearchCenterUtil.transformResearchCenterDO(centerInfo);
        researchCenterDO.setCreatedBy(operator);
        researchCenterDO.setCreatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.researchCenterMapper.insert(researchCenterDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCH_CENTER_ADD_FAILED);
        });
    }

    @Override
    public void updateResearchCenter(ResearchCenterInfo centerInfo, String operator) {
        AssertUtil.assertNull(centerInfo.getCenterId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCH_CENTER_ID_EMPTY);
        });
        ResearchCenterDO researchCenterDO = ResearchCenterUtil.transformResearchCenterDO(centerInfo);
        researchCenterDO.setUpdatedBy(operator);
        researchCenterDO.setUpdatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.researchCenterMapper.update(researchCenterDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCH_CENTER_UPDATE_FAILED);
        });
    }

    @Override
    public void deleteResearchCenterInfo(Long centerId) {
        AssertUtil.assertNull(centerId, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCH_CENTER_ID_EMPTY);
        });
        int count = this.researchCenterMapper.delete(centerId);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCH_CENTER_DELETE_FAILED);
        });
    }

    @Override
    public List<ResearchCenterInfo> getResearchCenterByRecruitmentId(Long recruitmentId) {
        if (null == recruitmentId) {
            return null;
        }
        ResearchCenterQueryParam queryParam = new ResearchCenterQueryParam();
        queryParam.setRecruitmentId(recruitmentId);
        List<ResearchCenterDO> researchCenterDOList = this.researchCenterMapper.select(queryParam);
        return ResearchCenterUtil.transformResearchCenter(researchCenterDOList);
    }
}
