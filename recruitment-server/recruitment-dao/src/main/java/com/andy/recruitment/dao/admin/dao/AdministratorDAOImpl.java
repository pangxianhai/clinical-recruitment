package com.andy.recruitment.dao.admin.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.admin.entity.AdministratorInfoDO;
import com.andy.recruitment.dao.admin.entity.AdministratorQuery;
import com.andy.recruitment.dao.admin.mapper.AdministratorInfoMapper;
import com.soyoung.base.mybatis.paginator.Page;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.PageUtil;
import com.soyoung.base.page.Paginator;
import com.soyoung.base.util.CollectionUtil;
import com.soyoung.base.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public AdministratorInfoDO getAdministratorInfo(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }
        AdministratorQuery query = new AdministratorQuery();
        query.setUserName(userName);
        List<AdministratorInfoDO> administratorInfoDoList = this.administratorInfoMapper.select(query);
        return CollectionUtil.parseOne(administratorInfoDoList, t -> t);
    }

    @Override
    public PageResult<AdministratorInfoDO> listAdministratorInfo(AdministratorQuery query, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<AdministratorInfoDO> administratorInfoDoList = this.administratorInfoMapper.select(query, page);
        return new PageResult<>(administratorInfoDoList, PageUtil.transformToPaginator(page));
    }
}
