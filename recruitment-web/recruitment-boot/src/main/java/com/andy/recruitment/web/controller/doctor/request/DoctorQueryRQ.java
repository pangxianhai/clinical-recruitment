package com.andy.recruitment.web.controller.doctor.request;

import com.xgimi.base.BaseQueryRQ;
import java.io.Serializable;

/**
 * 查询医生参数
 *
 * @author 庞先海 2019-02-20
 */
public class DoctorQueryRQ extends BaseQueryRQ implements Serializable {

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
}
