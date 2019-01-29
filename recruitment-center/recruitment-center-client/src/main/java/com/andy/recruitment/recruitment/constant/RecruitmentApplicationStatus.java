package com.andy.recruitment.recruitment.constant;

import com.xgimi.commons.base.BaseType;
import java.util.List;

/**
 * 招募申请状态
 *
 * @author 庞先海 2019-01-24
 */
public class RecruitmentApplicationStatus extends BaseType {

    public static final RecruitmentApplicationStatus NOT_ACCEDE = new RecruitmentApplicationStatus(1, "未入组");

    public static final RecruitmentApplicationStatus ACCEDE_SUCCESS = new RecruitmentApplicationStatus(2, "入组成功");

    public static final RecruitmentApplicationStatus ACCEDE_FAILED = new RecruitmentApplicationStatus(3, "入组失败");

    public RecruitmentApplicationStatus(int code, String desc) {
        super(code, desc);
    }

    public static List<RecruitmentApplicationStatus> getValues() {
        return BaseType.getValues(RecruitmentApplicationStatus.class);
    }

    public static RecruitmentApplicationStatus parse(Integer code) {
        return BaseType.parse(RecruitmentApplicationStatus.getValues(), code);
    }
}
