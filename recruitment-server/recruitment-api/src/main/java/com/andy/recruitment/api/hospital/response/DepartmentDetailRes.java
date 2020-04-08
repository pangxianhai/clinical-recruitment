package com.andy.recruitment.api.hospital.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构科室详细信息
 *
 * @author 庞先海 2020-02-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentDetailRes extends DepartmentRes {

    private static final long serialVersionUID = 7491334212583301894L;

    /**
     * 机构信息
     */
    private HospitalRes hospitalRes;
}
