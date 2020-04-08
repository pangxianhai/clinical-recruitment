package com.andy.recruitment.web.controller.hospital.controller;

import com.andy.recruitment.api.hospital.request.DepartmentAddReq;
import com.andy.recruitment.api.hospital.request.DepartmentQueryReq;
import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.biz.hospital.service.DepartmentService;
import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.hospital.entity.DepartmentQuery;
import com.andy.recruitment.web.controller.hospital.util.DepartmentUtil;
import com.andy.spring.auth.RoleType;
import com.andy.spring.context.ServletContext;
import com.andy.spring.converter.MyParameter;
import com.andy.spring.page.PageResult;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构科室
 *
 * @author 庞先海 2020-01-31
 */
@RestController
@RequestMapping("/hospital")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department")
    public PageResult<DepartmentDetailRes> listDepartment(@MyParameter DepartmentQueryReq queryReq) {
        DepartmentQuery query = DepartmentUtil.transformDepartmentQuery(queryReq);
        return this.departmentService.getDepartment(query, queryReq.getPaginator());
    }

    @GetMapping("/{hospitalId}/department")
    public List<DepartmentDetailRes> listDepartment(@PathVariable Long hospitalId) {
        return this.departmentService.getDepartmentByHospital(hospitalId);
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping("/department")
    public boolean addDepartment(@RequestBody DepartmentAddReq departmentAddReq) {
        DepartmentDO departmentDo = DepartmentUtil.transformDepartmentDo(departmentAddReq);
        this.departmentService.addDepartment(departmentDo, ServletContext.getLoginInfo().getRealName());
        return true;
    }

}
