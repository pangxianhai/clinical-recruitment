package com.andy.recruitment.dao.recruitment.constant;

import com.soyoung.base.type.BaseType;
import java.util.List;

/**
 * 招募状态
 *
 * @author 庞先海 2018-12-29
 */
public class RecruitmentStatus extends BaseType {

    private static final long serialVersionUID = 5112442044876053962L;

    public static final RecruitmentStatus NOT_BEGIN = new RecruitmentStatus(0, "未开始");

    public static final RecruitmentStatus IN_PROCESS = new RecruitmentStatus(1, "进行中");

    public static final RecruitmentStatus FINISHED = new RecruitmentStatus(2, "已结束");

    public RecruitmentStatus(int code, String desc) {
        super(code, desc);
    }

    public static List<RecruitmentStatus> getValues() {
        return BaseType.getValues(RecruitmentStatus.class);
    }

    public static RecruitmentStatus parse(Integer code) {
        return BaseType.parse(RecruitmentStatus.getValues(), code);
    }
}
