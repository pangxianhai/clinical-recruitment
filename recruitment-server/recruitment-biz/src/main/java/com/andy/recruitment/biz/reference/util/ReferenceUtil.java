package com.andy.recruitment.biz.reference.util;

import com.andy.recruitment.api.reference.response.ReferenceInfoRes;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import org.springframework.beans.BeanUtils;

/**
 * 推荐人工具
 *
 * @author 庞先海 2020-04-08
 */
public class ReferenceUtil {

    public static ReferenceInfoRes transformReferenceRes(ReferenceInfoDO referenceInfoDo) {
        if (referenceInfoDo == null) {
            return null;
        }
        ReferenceInfoRes referenceInfoRes = new ReferenceInfoRes();
        BeanUtils.copyProperties(referenceInfoDo, referenceInfoRes);
        referenceInfoRes.setReferenceId(referenceInfoDo.getId());
        return referenceInfoRes;
    }
}
