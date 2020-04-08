package com.andy.recruitment.api.recruitment.response;

import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.api.patient.response.PatientInfoDetailRes;
import com.andy.recruitment.api.reference.response.ReferenceDetailInfoRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 申请记录详情
 *
 * @author 庞先海 2020-03-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RecruitmentApplicationDetailRes extends RecruitmentApplicationRes {

    private static final long serialVersionUID = 527932413582538131L;

    /**
     * 项目信息
     */
    private RecruitmentInfoRes recruitmentInfoRes;

    /**
     * 选择的研究科室
     */
    private DepartmentDetailRes departmentDetailRes;

    /**
     * 患者信息
     */
    private PatientInfoDetailRes patientInfoDetailRes;

    /**
     * 推荐人信息
     */
    private ReferenceDetailInfoRes referenceDetailInfoRes;
}
