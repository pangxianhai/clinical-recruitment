package com.andy.recruitment.dao.reference.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.reference.mapper.ReferenceInfoMapper;
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
 * 推荐人DAO实现
 *
 * @author 庞先海 2020-01-27
 */
@Service
public class ReferenceDAOImpl implements ReferenceDAO {

    private final ReferenceInfoMapper referenceInfoMapper;

    @Autowired
    public ReferenceDAOImpl(ReferenceInfoMapper referenceInfoMapper) {
        this.referenceInfoMapper = referenceInfoMapper;
    }

    @Override
    public PageResult<ReferenceInfoDO> getReferenceInfo(ReferenceInfoQuery query, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<ReferenceInfoDO> referenceInfoDoList = this.referenceInfoMapper.select(query, page);
        return new PageResult<>(referenceInfoDoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public void addReferenceInfo(ReferenceInfoDO referenceInfoDo, String operator) {
        referenceInfoDo.setCreatedBy(operator);
        referenceInfoDo.setCreatedTime(LocalDateTime.now());
        int count = this.referenceInfoMapper.insert(referenceInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_ADD_FAILED);
        });
    }

    @Override
    public void updateReferenceInfo(ReferenceInfoDO referenceInfoDo, String operator) {
        AssertUtil.assertNull(referenceInfoDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_ID_EMPTY);
        });
        referenceInfoDo.setUpdatedBy(operator);
        referenceInfoDo.setUpdatedTime(LocalDateTime.now());
        int count = this.referenceInfoMapper.update(referenceInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_UPDATE_FAILED);
        });
    }
}
