package com.andy.recruitment.web.controller.recruitment.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 项目申请参数
 *
 * @author 庞先海 2020-03-01
 */
@Data
public class RecruitmentApplicationAddReq implements Serializable {

    private static final long serialVersionUID = 2390373672280970925L;
    /**
     * 选择的机构id
     */
    @NotNull
    private Long departmentId;
    /**
     * 患者姓名
     */
    @NotNull
    @NotBlank
    @Length(max = 32)
    private String patientName;
    /**
     * 患者手机号
     */
    @NotNull
    @NotBlank
    @Length(max = 32)
    private String patientPhone;
    /**
     * 患者性别
     */
    @NotNull
    private Integer patientGender;
    /**
     * 患者地址
     */
    @NotNull
    private String patientAddress;
    /**
     * 患者年龄
     */
    @NotNull
    private Integer patientAge;
    /**
     * 推荐人手机号
     */
    @Length(max = 32)
    private String referencePhone;
    /**
     * 病症描述
     */
    @Length(max = 32)
    private String diseaseDesc;
    /**
     * 病例图片
     */
    private List<String> diseaseImageList;
}
