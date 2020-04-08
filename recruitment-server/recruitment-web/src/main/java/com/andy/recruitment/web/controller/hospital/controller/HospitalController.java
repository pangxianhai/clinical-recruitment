package com.andy.recruitment.web.controller.hospital.controller;

import com.andy.recruitment.api.hospital.request.HospitalAddReq;
import com.andy.recruitment.api.hospital.request.HospitalQueryReq;
import com.andy.recruitment.api.hospital.response.HospitalRes;
import com.andy.recruitment.biz.hospital.service.HospitalService;
import com.andy.recruitment.dao.hospital.entity.HospitalDO;
import com.andy.recruitment.dao.hospital.entity.HospitalQuery;
import com.andy.recruitment.web.controller.hospital.util.HospitalUtil;
import com.andy.spring.auth.RoleType;
import com.andy.spring.context.ServletContext;
import com.andy.spring.converter.MyParameter;
import com.andy.spring.page.PageResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构controller接口
 *
 * @author 庞先海 2020-01-30
 */
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    @Autowired
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public PageResult<HospitalRes> listOrganization(@MyParameter HospitalQueryReq queryReq) {
        HospitalQuery query = HospitalUtil.transformHospitalQuery(queryReq);
        return this.hospitalService.getHospital(query, queryReq.getPaginator());
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addOrganization(@RequestBody HospitalAddReq addReq) {
        HospitalDO organizationDo = HospitalUtil.transformHospitalDo(addReq);
        this.hospitalService.addHospital(organizationDo, ServletContext.getLoginInfo().getRealName());
        return true;
    }
}
