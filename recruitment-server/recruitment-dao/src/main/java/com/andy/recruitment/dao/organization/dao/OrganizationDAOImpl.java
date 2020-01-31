package com.andy.recruitment.dao.organization.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.andy.recruitment.dao.organization.mapper.OrganizationInfoMapper;
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
 * 机构信息 dao 实现
 *
 * @author 庞先海 2020-01-27
 */
@Service
public class OrganizationDAOImpl implements OrganizationDAO {

    private final OrganizationInfoMapper organizationInfoMapper;

    @Autowired
    public OrganizationDAOImpl(OrganizationInfoMapper organizationInfoMapper) {
        this.organizationInfoMapper = organizationInfoMapper;
    }

    @Override
    public void addOrganization(OrganizationDO organizationDo, String operator) {
        organizationDo.setCreatedBy(operator);
        organizationDo.setCreatedTime(LocalDateTime.now());
        int count = this.organizationInfoMapper.insert(organizationDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ORGANIZATION_ADD_FAILED);
        });
    }

    @Override
    public void updateOrganization(OrganizationDO organizationDo, String operator) {
        AssertUtil.assertNull(organizationDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ORGANIZATION_ID_EMPTY);
        });
        organizationDo.setUpdatedBy(operator);
        organizationDo.setUpdatedTime(LocalDateTime.now());
        int count = this.organizationInfoMapper.update(organizationDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ORGANIZATION_UPDATE_FAILED);
        });
    }

    @Override
    public PageResult<OrganizationDO> getOrganization(OrganizationQuery query, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<OrganizationDO> organizationDoList = this.organizationInfoMapper.select(query, page);
        return new PageResult<>(organizationDoList, PageUtil.transformToPaginator(page));
    }
}