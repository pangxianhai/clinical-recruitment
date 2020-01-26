package com.andy.recruitment.dao.recruitment.constant;

import com.soyoung.base.type.BaseType;
import java.util.List;

/**
 * 招募申请状态
 *
 * @author 庞先海 2019-01-24
 */
public class RecruitmentApplicationStatus extends BaseType {

    private static final long serialVersionUID = 6462447834184056207L;

    public static final RecruitmentApplicationStatus NOT_ACCEDE = new RecruitmentApplicationStatus(1, "未入组");

    public static final RecruitmentApplicationStatus ACCEDED = new RecruitmentApplicationStatus(2, "已入组");

    public static final RecruitmentApplicationStatus REFUSED = new RecruitmentApplicationStatus(3, "已拒接");


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
