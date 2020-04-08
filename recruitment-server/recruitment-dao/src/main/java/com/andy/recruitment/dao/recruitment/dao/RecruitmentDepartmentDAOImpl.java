package com.andy.recruitment.dao.recruitment.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentDepartmentDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentDepartmentQuery;
import com.andy.recruitment.dao.recruitment.mapper.RecruitmentDepartmentMapper;
import com.andy.spring.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目研究机构DAO接口实现
 *
 * @author 庞先海 2020-01-31
 */
@Service
public class RecruitmentDepartmentDAOImpl implements RecruitmentDepartmentDAO {

    private final RecruitmentDepartmentMapper recruitmentDepartmentMapper;

    @Autowired
    public RecruitmentDepartmentDAOImpl(RecruitmentDepartmentMapper recruitmentDepartmentMapper) {
        this.recruitmentDepartmentMapper = recruitmentDepartmentMapper;
    }

    @Override
    public List<RecruitmentDepartmentDO> listRecruitmentByDepartment(Long departmentId) {
        if (departmentId == null) {
            return null;
        }
        RecruitmentDepartmentQuery query = new RecruitmentDepartmentQuery();
        query.setDepartmentId(departmentId);
        return this.recruitmentDepartmentMapper.select(query);
    }

    @Override
    public List<RecruitmentDepartmentDO> listDepartmentByRecruitment(Long recruitmentId) {
        if (recruitmentId == null) {
            return null;
        }
        RecruitmentDepartmentQuery query = new RecruitmentDepartmentQuery();
        query.setRecruitmentId(recruitmentId);
        return this.recruitmentDepartmentMapper.select(query);
    }

    @Override
    public List<RecruitmentDepartmentDO> listDepartmentByHospitalId(Long hospitalId) {
        if (hospitalId == null) {
            return null;
        }
        RecruitmentDepartmentQuery query = new RecruitmentDepartmentQuery();
        query.setHospitalId(hospitalId);
        return this.recruitmentDepartmentMapper.select(query);
    }

    @Override
    public void addRecruitmentDepartment(Long recruitmentId, Long hospitalId, Long departmentId, String operator) {
        RecruitmentDepartmentDO recruitmentOrganizationDo = new RecruitmentDepartmentDO();
        recruitmentOrganizationDo.setHospitalId(hospitalId);
        recruitmentOrganizationDo.setRecruitmentId(recruitmentId);
        recruitmentOrganizationDo.setDepartmentId(departmentId);
        recruitmentOrganizationDo.setCreatedBy(operator);
        recruitmentOrganizationDo.setCreatedTime(LocalDateTime.now());
        int count = this.recruitmentDepartmentMapper.insert(recruitmentOrganizationDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ORGANIZATION_ADD_FAILED);
        });
    }

    @Override
    public void deleteRecruitmentDepartment(Long recruitmentId, Long departmentId) {
        AssertUtil.assertFalse(recruitmentId == null || departmentId == null, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ORGANIZATION_DELETE_ID_EMPTY);
        });
        RecruitmentDepartmentQuery query = new RecruitmentDepartmentQuery();
        query.setRecruitmentId(recruitmentId);
        query.setDepartmentId(departmentId);
        int count = this.recruitmentDepartmentMapper.delete(query);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ORGANIZATION_DELETE_FAILED);
        });
    }
}
