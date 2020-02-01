package com.andy.recruitment.dao.recruitment.constant;

import com.soyoung.base.type.BaseType;
import java.util.List;

/**
 * 招募项目类目
 *
 * @author 庞先海 2020-02-01
 */
public class RecruitmentCategory extends BaseType {

    private static final long serialVersionUID = - 6216226585384844061L;

    public static final RecruitmentCategory TUMOUR = new RecruitmentCategory(1, "肿瘤");

    public static final RecruitmentCategory NON_TUMOR = new RecruitmentCategory(2, "非肿瘤");


    public RecruitmentCategory(int code, String desc) {
        super(code, desc);
    }

    public static List<RecruitmentCategory> getValues() {
        return BaseType.getValues(RecruitmentCategory.class);
    }

    public static RecruitmentCategory parse(Integer code) {
        return BaseType.parse(RecruitmentCategory.getValues(), code);
    }
}
