package com.andy.recruitment.web.controller.reference.request;

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