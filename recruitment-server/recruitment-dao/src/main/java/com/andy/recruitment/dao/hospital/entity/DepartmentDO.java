package com.andy.recruitment.dao.hospital.entity;

import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 机构科室信息
 *
 * @author 庞先海 2020-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DepartmentDO extends BaseDO {

    private static final long serialVersionUID = 1425012185188584306L;

    /**
     * 机构ID
     */
    private Long hospitalId;

    /**
     * 科室名称
     */
    private String name;
}
