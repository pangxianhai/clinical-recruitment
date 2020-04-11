package com.andy.recruitment.dao.admin.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.admin.entity.AdministratorInfoDO;
import com.andy.recruitment.dao.admin.entity.AdministratorQuery;
import com.andy.recruitment.dao.admin.mapper.AdministratorInfoMapper;
import com.andy.spring.mybatis.paginator.Page;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.PageUtil;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.CollectionUtil;
import com.andy.spring.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员dao实现
 *
 * @author 庞先海 2020-01-26
 */
@Service
public class AdministratorDAOImpl implements AdministratorDAO {

    private final AdministratorInfoMapper administratorInfoMapper;

    @Autowired
    public AdministratorDAOImpl(AdministratorInfoMapper administratorInfoMapper) {
        this.administratorInfoMapper = administratorInfoMapper;
    }

    @Override
    public void addAdministrator(AdministratorInfoDO administratorInfoDo, String operator) {
        administratorInfoDo.setCreatedBy(operator);
        administratorInfoDo.setCreatedTime(LocalDateTime.now());
        int count = this.administratorInfoMapper.insert(administratorInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_ADD_FAILED);
        });
    }

    @Override
    public void updateAdministrator(AdministratorInfoDO administratorInfoDo, String operator) {
        AssertUtil.assertNull(administratorInfoDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_ID_EMPTY);
        });
        administratorInfoDo.setUpdatedBy(operator);
        administratorInfoDo.setUpdatedTime(LocalDateTime.now());
        int count = this.administratorInfoMapper.update(administratorInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_UPDATE_FAILED);
        });
    }

    @Override
    public AdministratorInfoDO getAdministratorByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        AdministratorQuery query = new AdministratorQuery();
        query.setUserId(userId);
        List<AdministratorInfoDO> administratorInfoDoList = this.administratorInfoMapper.select(query);
        return CollectionUtil.parseOne(administratorInfoDoList, Function.identity());
    }

    @Override
    public AdministratorInfoDO getAdministratorById(Long adminId) {
        if (adminId == null) {
            return null;
        }
        AdministratorQuery query = new AdministratorQuery();
        query.setAdminId(adminId);
        List<AdministratorInfoDO> administratorInfoDoList = this.administratorInfoMapper.select(query);
        return CollectionUtil.parseOne(administratorInfoDoList, Function.identity());
    }

    @Override
    public PageResult<AdministratorInfoDO> listAdministratorInfo(AdministratorQuery query, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<AdministratorInfoDO> administratorInfoDoList = this.administratorInfoMapper.select(query, page);
        return new PageResult<>(administratorInfoDoList, PageUtil.transformToPaginator(page));
    }
}
