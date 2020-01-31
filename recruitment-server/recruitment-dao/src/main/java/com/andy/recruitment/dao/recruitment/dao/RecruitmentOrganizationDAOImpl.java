package com.andy.recruitment.dao.recruitment.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentOrganizationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentOrganizationQuery;
import com.andy.recruitment.dao.recruitment.mapper.RecruitmentOrganizationMapper;
import com.soyoung.base.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目研究机构DAO接口实现
 *
 * @author 庞先海 2020-01-31
 */
@Service
public class RecruitmentOrganizationDAOImpl implements RecruitmentOrganizationDAO {

    private final RecruitmentOrganizationMapper recruitmentOrganizationMapper;

    @Autowired
    public RecruitmentOrganizationDAOImpl(RecruitmentOrganizationMapper recruitmentOrganizationMapper) {
        this.recruitmentOrganizationMapper = recruitmentOrganizationMapper;
    }

    @Override
    public List<Long> listRecruitmentByOrganization(Long organizationId) {
        if (organizationId == null) {
            return null;
        }
        RecruitmentOrganizationQuery query = new RecruitmentOrganizationQuery();
        query.setOrganizationId(organizationId);
        List<RecruitmentOrganizationDO> recruitmentOrganizationDoList = this.recruitmentOrganizationMapper.select(
            query);
        if (CollectionUtils.isEmpty(recruitmentOrganizationDoList)) {
            return null;
        }
        return recruitmentOrganizationDoList.stream().map(RecruitmentOrganizationDO::getRecruitmentId).collect(
            Collectors.toList());
    }

    @Override
    public List<Long> listOrganizationByRecruitment(Long recruitmentId) {
        if (recruitmentId == null) {
            return null;
        }
        RecruitmentOrganizationQuery query = new RecruitmentOrganizationQuery();
        query.setRecruitmentId(recruitmentId);
        List<RecruitmentOrganizationDO> recruitmentOrganizationDoList = this.recruitmentOrganizationMapper.select(
            query);
        if (CollectionUtils.isEmpty(recruitmentOrganizationDoList)) {
            return null;
        }
        return recruitmentOrganizationDoList.stream().map(RecruitmentOrganizationDO::getOrganizationId).collect(
            Collectors.toList());
    }

    @Override
    public void addRecruitmentOrganization(Long recruitmentId, Long organizationId, String operator) {
        RecruitmentOrganizationDO recruitmentOrganizationDo = new RecruitmentOrganizationDO();
        recruitmentOrganizationDo.setOrganizationId(organizationId);
        recruitmentOrganizationDo.setRecruitmentId(recruitmentId);
        recruitmentOrganizationDo.setCreatedBy(operator);
        recruitmentOrganizationDo.setCreatedTime(LocalDateTime.now());
        int count = this.recruitmentOrganizationMapper.insert(recruitmentOrganizationDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ORGANIZATION_ADD_FAILED);
        });
    }

    @Override
    public void deleteRecruitmentOrganization(Long recruitmentId, Long organizationId) {
        AssertUtil.assertFalse(recruitmentId == null || organizationId == null, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ORGANIZATION_DELETE_ID_EMPTY);
        });
        RecruitmentOrganizationQuery query = new RecruitmentOrganizationQuery();
        query.setRecruitmentId(recruitmentId);
        query.setOrganizationId(organizationId);
        int count = this.recruitmentOrganizationMapper.delete(query);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ORGANIZATION_DELETE_FAILED);
        });
    }
}
