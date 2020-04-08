package com.andy.recruitment.web.controller.hospital.util;

import com.andy.recruitment.api.hospital.request.DepartmentAddReq;
import com.andy.recruitment.api.hospital.request.DepartmentQueryReq;
import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.hospital.entity.DepartmentQuery;
import org.springframework.beans.BeanUtils;

/**
 * 机构科室工具
 *
 * @author 庞先海 2020-01-31
 */
public class DepartmentUtil {

    public static DepartmentDO transformDepartmentDo(DepartmentAddReq departmentAddReq) {
        if (departmentAddReq == null) {
            return null;
        }
        DepartmentDO organizationDepartmentDo = new DepartmentDO();
        BeanUtils.copyProperties(departmentAddReq, organizationDepartmentDo);
        return organizationDepartmentDo;
    }

    public static DepartmentQuery transformDepartmentQuery(DepartmentQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }
        DepartmentQuery departmentQuery = new DepartmentQuery();
        BeanUtils.copyProperties(queryReq, departmentQuery);
        return departmentQuery;
    }
}
