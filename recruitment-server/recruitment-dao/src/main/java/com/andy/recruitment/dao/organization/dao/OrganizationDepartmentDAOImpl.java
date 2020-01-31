package com.andy.recruitment.dao.organization.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.andy.recruitment.dao.organization.mapper.OrganizationDepartmentMapper;
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
 * 机构科室 dao 接口实现
 *
 * @author 庞先海 2020-01-31
 */
@Service
public class OrganizationDepartmentDAOImpl implements OrganizationDepartmentDAO {

    private final OrganizationDepartmentMapper organizationDepartmentMapper;

    @Autowired
    public OrganizationDepartmentDAOImpl(OrganizationDepartmentMapper organizationDepartmentMapper) {
        this.organizationDepartmentMapper = organizationDepartmentMapper;
    }

    @Override
    public void addOrganizationDepartment(OrganizationDepartmentDO organizationDepartmentDo, String operator) {
        organizationDepartmentDo.setCreatedBy(operator);
        organizationDepartmentDo.setCreatedTime(LocalDateTime.now());
        int count = this.organizationDepartmentMapper.insert(organizationDepartmentDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ORGANIZATION_DEPARTMENT_ADD_FAILED);
        });
    }

    @Override
    public void updateOrganizationDepartment(OrganizationDepartmentDO organizationDepartmentDo, String operator) {
        AssertUtil.assertNull(organizationDepartmentDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ORGANIZATION_DEPARTMENT_ID_EMPTY);
        });
        organizationDepartmentDo.setUpdatedBy(operator);
        organizationDepartmentDo.setUpdatedTime(LocalDateTime.now());
        int count = this.organizationDepartmentMapper.update(organizationDepartmentDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ORGANIZATION_DEPARTMENT_UPDATE_FAILED);
        });
    }

    @Override
    public PageResult<OrganizationDepartmentDO> getOrganizationDepartment(OrganizationDepartmentQuery query,
        Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<OrganizationDepartmentDO> organizationDepartmentDoList = this.organizationDepartmentMapper.select(query,
            page);
        return new PageResult<>(organizationDepartmentDoList, PageUtil.transformToPaginator(page));
    }
}
