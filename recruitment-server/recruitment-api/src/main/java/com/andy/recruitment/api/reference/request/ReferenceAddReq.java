package com.andy.recruitment.api.reference.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 推荐人添加参数
 *
 * @author 庞先海 2020-02-15
 */
@Data
public class ReferenceAddReq implements Serializable {

    private static final long serialVersionUID = 466986966112127464L;

    /**
     * 手机号
     */
    @NotBlank
    @Length(max = 32)
    private String phone;
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

}