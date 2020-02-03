package com.andy.recruitment.web.controller.researcher.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 研究员添加参数
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class ResearcherAddReq implements Serializable {

    private static final long serialVersionUID = - 3580079645942042249L;

    /**
     * 微信openId
     */
    private String openId;
    /**
     * 微信昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    @NotBlank
    @Length(max = 32)
    private String phone;
    /**
     * 所属机构id
     */
    @NotNull
    private Long organizationId;
    /**
     * 所属科室ID
     */
    @NotNull
    private Long departmentId;
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
     * 备注信息
     */
    @Length(max = 512)
    private String remark;
}
