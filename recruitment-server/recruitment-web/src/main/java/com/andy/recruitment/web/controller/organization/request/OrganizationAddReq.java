package com.andy.recruitment.web.controller.organization.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 机构添加参数
 *
 * @author 庞先海 2020-01-30
 */
@Data
public class OrganizationAddReq implements Serializable {

    private static final long serialVersionUID = - 7213798303465383762L;

    /**
     * 机构名称
     */
    @NotBlank
    @Length(max = 64)
    private String name;

    /**
     * 省ID
     */
    @NotNull
    private Long provinceId;

    /**
     * 市ID
     */
    @NotNull
    private Long cityId;

    /**
     * 区ID
     */
    @NotNull
    private Long districtId;
}
