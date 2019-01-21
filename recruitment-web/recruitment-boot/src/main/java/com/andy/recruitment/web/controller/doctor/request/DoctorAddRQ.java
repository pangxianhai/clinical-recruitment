package com.andy.recruitment.web.controller.doctor.request;

import java.io.Serializable;

/**
 * 添加医生参数
 *
 * @author 庞先海 2019-01-20
 */
public class DoctorAddRQ implements Serializable {

    /**
     * 地址
     */
    private String address;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 执业机构
     */
    private String medicalInstitution;
    /**
     * 执业类别
     */
    private String medicalCategory;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMedicalInstitution() {
        return medicalInstitution;
    }

    public void setMedicalInstitution(String medicalInstitution) {
        this.medicalInstitution = medicalInstitution;
    }

    public String getMedicalCategory() {
        return medicalCategory;
    }

    public void setMedicalCategory(String medicalCategory) {
        this.medicalCategory = medicalCategory;
    }
}
