package com.andy.recruitment.researchcenter.util;

import com.andy.recruitment.researchcenter.model.ResearchCenterDO;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 研究中心工具
 *
 * @author 庞先海 2019-01-08
 */
public class ResearchCenterUtil {

    public static ResearchCenterDO transformResearchCenterDO(ResearchCenterInfo researchCenterInfo) {
        if (null == researchCenterInfo) {
            return null;
        }
        ResearchCenterDO researchCenterDO = new ResearchCenterDO();
        BeanUtil.copyProperties(researchCenterInfo, researchCenterDO);
        researchCenterDO.setId(researchCenterInfo.getCenterId());
        return researchCenterDO;
    }

    public static ResearchCenterInfo transformResearchCenter(ResearchCenterDO researchCenterDO) {
        if (null == researchCenterDO) {
            return null;
        }
        ResearchCenterInfo researchCenterInfo = new ResearchCenterInfo();
        BeanUtil.copyProperties(researchCenterDO, researchCenterInfo);
        researchCenterInfo.setCenterId(researchCenterDO.getId());
        return researchCenterInfo;
    }

    public static List<ResearchCenterInfo> transformResearchCenter(List<ResearchCenterDO> researchCenterDOList) {
        if (CollectionUtil.isEmpty(researchCenterDOList)) {
            return null;
        }
        return researchCenterDOList.stream().map(ResearchCenterUtil::transformResearchCenter).filter(
            Objects::nonNull).collect(Collectors.toList());
    }
}
