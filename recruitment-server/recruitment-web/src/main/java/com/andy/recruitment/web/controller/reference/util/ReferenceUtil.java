package com.andy.recruitment.web.controller.reference.util;

import com.andy.recruitment.api.reference.request.ReferenceAddReq;
import com.andy.recruitment.api.reference.request.ReferenceQueryReq;
import com.andy.recruitment.api.reference.request.ReferenceRegisterReq;
import com.andy.recruitment.common.reference.constant.ReferenceRole;
import com.andy.recruitment.common.reference.constant.ReferenceStatus;
import com.andy.recruitment.common.user.constant.Gender;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import org.springframework.beans.BeanUtils;

/**
 * 推荐人功能键
 *
 * @author 庞先海 2020-02-15
 */
public class ReferenceUtil {

    public static ReferenceInfoDO transformReferenceInfoDo(ReferenceAddReq referenceAddReq) {
        if (null == referenceAddReq) {
            return null;
        }
        ReferenceInfoDO referenceInfoDo = new ReferenceInfoDO();
        BeanUtils.copyProperties(referenceAddReq, referenceInfoDo);
        referenceInfoDo.setReferenceRole(ReferenceRole.parse(referenceAddReq.getRole()));
        return referenceInfoDo;
    }

    public static UserInfoDO transformUserInfo(ReferenceAddReq referenceAddReq) {
        if (null == referenceAddReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(referenceAddReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(referenceAddReq.getGender()));
        userInfoDo.setRealName(referenceAddReq.getName());
        return userInfoDo;
    }

    public static ReferenceInfoDO transformReferenceInfoDo(ReferenceRegisterReq referenceRegisterReq) {
        if (null == referenceRegisterReq) {
            return null;
        }
        ReferenceInfoDO referenceInfoDo = new ReferenceInfoDO();
        BeanUtils.copyProperties(referenceRegisterReq, referenceInfoDo);
        referenceInfoDo.setReferenceRole(ReferenceRole.parse(referenceRegisterReq.getRole()));
        return referenceInfoDo;
    }

    public static UserInfoDO transformUserInfo(ReferenceRegisterReq referenceRegisterReq) {
        if (null == referenceRegisterReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(referenceRegisterReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(referenceRegisterReq.getGender()));
        userInfoDo.setRealName(referenceRegisterReq.getName());
        return userInfoDo;
    }


    public static ReferenceInfoQuery transformReferenceQuery(ReferenceQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }

        ReferenceInfoQuery query = new ReferenceInfoQuery();
        BeanUtils.copyProperties(queryReq, query);
        if (queryReq.getStatus() != null) {
            query.setStatus(ReferenceStatus.parse(queryReq.getStatus()));
        }
        return query;
    }
}
