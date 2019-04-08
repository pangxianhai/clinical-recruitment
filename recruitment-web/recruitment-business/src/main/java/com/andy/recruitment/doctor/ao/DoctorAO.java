package com.andy.recruitment.doctor.ao;

import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;

/**
 * 医生AO
 *
 * @author 庞先海 2019-01-20
 */
public interface DoctorAO {

    /**
     * 注册医生
     *
     * @param doctorInfo 医生信息
     * @param operator   添加人
     */
    DoctorInfo registerDoctor(DoctorInfo doctorInfo, String operator);

    /**
     * 更新医生信息
     *
     * @param doctorInfo 更新后医生的信息
     * @param operator   更新人
     */
    void updateDoctorInfo(DoctorInfo doctorInfo, String operator);

    /**
     * 通过医生ID查询医生信息
     *
     * @param doctorId 医生ID
     * @return 医生信息
     */
    DoctorInfo getDoctorInfoById(Long doctorId);

    /**
     * 通过用户ID获取医生信息
     *
     * @param userId 用户ID
     * @return 医生信息
     */
    DoctorInfo getDoctorInfoByUserId(Long userId);

    /**
     * 医生信息分页查询
     *
     * @param queryParam 查询参数
     * @param paginator  分页信息
     * @return 医生信息分页查询结果
     */
    PageResult<DoctorInfo> getDoctorInfo(DoctorQueryParam queryParam, Paginator paginator);
}
