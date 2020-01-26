package com.andy.recruitment.dao.recruitment.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.recruitment.dao.recruitment.mapper.RecruitmentMapper;
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

/**
 * 招募信息dao实现
 *
 * @author 庞先海 2020-01-26
 */
@Service
public class RecruitmentDAOImpl implements RecruitmentDAO {

    private final RecruitmentMapper recruitmentMapper;

    @Autowired
    public RecruitmentDAOImpl(RecruitmentMapper recruitmentMapper) {
        this.recruitmentMapper = recruitmentMapper;
    }

    @Override
    public Long addRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, String operator) {
        recruitmentInfoDo.setStatus(RecruitmentStatus.NOT_BEGIN);
        recruitmentInfoDo.setCreatedBy(operator);
        recruitmentInfoDo.setCreatedTime(LocalDateTime.now());
        int count = this.recruitmentMapper.insert(recruitmentInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ADD_FAILED);
        });
        return recruitmentInfoDo.getId();
    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, String operator) {
        AssertUtil.assertNull(recruitmentInfoDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ID_EMPTY);
        });
        recruitmentInfoDo.setUpdatedBy(operator);
        recruitmentInfoDo.setUpdatedTime(LocalDateTime.now());
        int count = this.recruitmentMapper.update(recruitmentInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_UPDATE_FAILED);
        });
    }

    @Override
    public PageResult<RecruitmentInfoDO> getRecruitmentInfo(RecruitmentQuery queryParam, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<RecruitmentInfoDO> recruitmentInfoDoList = this.recruitmentMapper.select(queryParam, page);
        return new PageResult<>(recruitmentInfoDoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public RecruitmentInfoDO getRecruitmentInfoById(Long recruitmentId) {
        if (null == recruitmentId) {
            return null;
        }
        RecruitmentQuery queryParam = new RecruitmentQuery();
        queryParam.setRecruitmentId(recruitmentId);
        List<RecruitmentInfoDO> recruitmentInfoDoList = this.recruitmentMapper.select(queryParam);
        return CollectionUtil.parseOne(recruitmentInfoDoList, t -> t);
    }
}
