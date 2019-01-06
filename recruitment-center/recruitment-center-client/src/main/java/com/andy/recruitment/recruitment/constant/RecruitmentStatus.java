package com.andy.recruitment.recruitment.constant;

import com.xgimi.commons.base.BaseType;

/**
 * 招募状态
 *
 * @author 庞先海 2018-12-29
 */
public class RecruitmentStatus extends BaseType {

    public static final RecruitmentStatus NOT_BEGIN = new RecruitmentStatus(0, "未招募");

    public static final RecruitmentStatus IN_PROCESS = new RecruitmentStatus(1, "经行中");

    public static final RecruitmentStatus FINISHED = new RecruitmentStatus(2, "已完成");

    public RecruitmentStatus(int code, String desc) {
        super(code, desc);
    }
}
