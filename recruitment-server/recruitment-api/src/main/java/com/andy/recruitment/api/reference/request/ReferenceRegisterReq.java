package com.andy.recruitment.api.reference.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 推荐人注册参数
 *
 * @author 庞先海 2020-02-26
 */
@Data
public class ReferenceRegisterReq implements Serializable {

    private static final long serialVersionUID = - 6075185569450509952L;

    /**
     * 微信openId
     */
    @Length(max = 32)
    @NotBlank
    private String openId;
    /**
     * 微信昵称
     */
    @Length(max = 64)
    @NotBlank
    private String nickname;
    /**
     * 手机号
     */
    @NotBlank
    @Length(max = 32)
    private String phone;
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
     * 医院ID
     */
    @NotNull
    private Long hospitalId;
    /**
     * 科室ID
     */
    @NotNull
    private Long departmentId;
    /**
     * 角色
     */
    @NotNull
    private Integer role;
    /**
     * 执业机构
     */
    @NotBlank
    @Length(max = 64)
    private String medicalInstitution;
    /**
     * 执业类别
     */
    @NotBlank
    @Length(max = 64)
    private String medicalCategory;
    /**
     * 备注
     */
    @Length(max = 64)
    private String remark;

}
