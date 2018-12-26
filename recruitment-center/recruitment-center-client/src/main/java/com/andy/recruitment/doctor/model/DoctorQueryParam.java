package com.andy.recruitment.doctor.model;

import com.andy.recruitment.doctor.constant.DoctorStatus;
import java.io.Serializable;

/**
 * 医生信息查询参数
 *
 * @author 庞先海 2018-12-25
 */
public class DoctorQueryParam implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 医生ID
     */
    private Long doctorId;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 城市ID
     */
    private Long cityId;
    /**
     * 医生状态
     */
    private DoctorStatus status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public DoctorStatus getStatus() {
        return status;
    }

    public void setStatus(DoctorStatus status) {
        this.status = status;
    }
}
