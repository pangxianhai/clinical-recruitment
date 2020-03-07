package com.andy.recruitment.api.patient.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 添加患者参数
 *
 * @author 庞先海 2020-02-16
 */
@Data
public class PatientAddReq implements Serializable {

    private static final long serialVersionUID = 2565459530001986631L;
    /**
     * 手机号
     */
    @NotBlank
    @Length(max = 32)
    private String phone;
    /**
     * 省份ID
     */
    @NotNull
    private Long provinceId;
    /**
     * 城市ID
     */
    @NotNull
    private Long cityId;
    /**
     * 区ID
     */
    @NotNull
    private Long districtId;
    /**
     * 姓名
     */
    @NotBlank
    @Length(max = 64)
    private String name;
    /**
     * 性别
     */
    @NotNull
    private Integer gender;
    /**
     * 年龄
     */
    @NotNull
    private Integer age;
}
