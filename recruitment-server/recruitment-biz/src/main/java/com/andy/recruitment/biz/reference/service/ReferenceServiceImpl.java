package com.andy.recruitment.biz.reference.service;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.reference.dao.ReferenceDAO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.dao.UserDAO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import com.soyoung.base.util.asserts.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 推荐人服务接口实现
 *
 * @author 庞先海 2020-02-15
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

    private final ReferenceDAO referenceDAO;

    private final UserDAO userDAO;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public ReferenceServiceImpl(ReferenceDAO referenceDAO, UserDAO userDAO, TransactionTemplate transactionTemplate) {
        this.referenceDAO = referenceDAO;
        this.userDAO = userDAO;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void registerReference(ReferenceInfoDO referenceInfoDo, UserInfoDO userInfoDo, String operator) {
        transactionTemplate.execute((status) -> {
            Long userId = this.userDAO.registerUser(userInfoDo, operator);
            referenceInfoDo.setUserId(userId);
            ReferenceInfoDO existReferenceInfoDo = this.referenceDAO.getReferenceInfoByUserId(userId);
            if (existReferenceInfoDo == null) {
                this.referenceDAO.addReferenceInfo(referenceInfoDo, operator);
            } else {
                referenceInfoDo.setId(existReferenceInfoDo.getId());
                this.referenceDAO.updateReferenceInfo(referenceInfoDo, operator);
            }
            return null;
        });
    }

    @Override
    public void updateReference(ReferenceInfoDO referenceInfoDo, UserInfoDO userInfoDo, String operator) {
        ReferenceInfoDO sourceReferenceInfoDo = this.referenceDAO.getReferenceInfoById(referenceInfoDo.getId());
        AssertUtil.assertNull(sourceReferenceInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_NOT_EXIST);
        });
        transactionTemplate.execute((status) -> {
            this.referenceDAO.updateReferenceInfo(referenceInfoDo, operator);
            userInfoDo.setId(sourceReferenceInfoDo.getUserId());
            this.userDAO.updateUserInfo(userInfoDo, operator);
            return null;
        });
    }

    @Override
    public PageResult<ReferenceInfoDO> getReference(ReferenceInfoQuery query, Paginator paginator) {
        return this.referenceDAO.getReferenceInfo(query, paginator);
    }

    @Override
    public ReferenceInfoDO getReference(Long referenceId) {
        return this.referenceDAO.getReferenceInfoById(referenceId);
    }
}
