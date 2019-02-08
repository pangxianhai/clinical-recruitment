package com.andy.recruitment.web.controller.patient.response;

import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import java.io.Serializable;

/**
 * 患者信息
 *
 * @author 庞先海 2019-02-08
 */
public class PatientVO implements Serializable {

    /**
     * 患者ID
     */
    private Long patientId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户信息
     */
    private UserInfoVO userInfoVO;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 城市ID
     */
    private Long cityId;
    /**
     * 区ID
     */
    private Long districtId;
    /**
     * 年龄
     */
    private Integer age;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserInfoVO getUserInfoVO() {
        return userInfoVO;
    }

    public void setUserInfoVO(UserInfoVO userInfoVO) {
        this.userInfoVO = userInfoVO;
    }
}
