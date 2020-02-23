package com.andy.recruitment.web.controller.patient.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 患者注册参数
 *
 * @author 庞先海 2020-02-16
 */
@Data
public class PatientRegisterReq implements Serializable {

    private static final long serialVersionUID = 2565459530001986631L;

    /**
     * 微信openId
     */
    @Length(max = 32)
    private String openId;
    /**
     * 微信昵称
     */
    @Length(max = 64)
    private String nickname;
    /**
     * 手机号
     */
    @NotBlank
    @Length(max = 32)
    private String phone;
    /**
     * 地址信息
     */
    @NotBlank
    @Length(max = 64)
    private String address;
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
