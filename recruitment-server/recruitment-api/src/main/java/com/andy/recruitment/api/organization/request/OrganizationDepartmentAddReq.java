package com.andy.recruitment.api.organization.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 机构科室添加参数
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class OrganizationDepartmentAddReq implements Serializable {

    private static final long serialVersionUID = - 4505137003248670053L;

    /**
     * 机构ID
     */
    @NotNull
    private Long organizationId;

    /**
     * 科室名称
     */
    @NotBlank
    @Length(max = 64)
    private String name;
}
