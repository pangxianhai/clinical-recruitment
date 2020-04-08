package com.andy.recruitment.web.controller.recruitment.controller;

import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.api.recruitment.request.RecruitmentAddReq;
import com.andy.recruitment.api.recruitment.request.RecruitmentQueryReq;
import com.andy.recruitment.api.recruitment.response.RecruitmentInfoDetailRes;
import com.andy.recruitment.api.recruitment.response.RecruitmentInfoRes;
import com.andy.recruitment.biz.hospital.service.DepartmentService;
import com.andy.recruitment.biz.recruitment.service.RecruitmentDepartmentService;
import com.andy.recruitment.biz.recruitment.service.RecruitmentService;
import com.andy.recruitment.common.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 招募项目controller接口
 *
 * @author 庞先海 2020-01-30
 */
@RestController
@RequestMapping("/recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    private final DepartmentService departmentService;

    private final RecruitmentDepartmentService recruitmentDepartmentService;

    @Autowired
    public RecruitmentController(RecruitmentService recruitmentService, DepartmentService departmentService,
        RecruitmentDepartmentService recruitmentDepartmentService) {
        this.recruitmentService = recruitmentService;
        this.departmentService = departmentService;
        this.recruitmentDepartmentService = recruitmentDepartmentService;
    }

    @GetMapping
    public PageResult<RecruitmentInfoRes> listRecruitment(@MyParameter RecruitmentQueryReq queryReq) {
        RecruitmentQuery recruitmentQuery = RecruitmentUtil.transformQueryParam(queryReq);
        PageResult<RecruitmentInfoDO> pageResult = this.recruitmentService.getRecruitmentInfo(recruitmentQuery,
            queryReq.getPaginator());
        List<RecruitmentInfoRes> recruitmentInfoResList = RecruitmentUtil.transformRecruitmentInfoRes(
            pageResult.getData());
        return new PageResult<>(recruitmentInfoResList, pageResult.getPaginator());
    }

    @GetMapping("/{recruitmentId:\\d+}")
    public RecruitmentInfoDetailRes getRecruitmentDetail(@PathVariable Long recruitmentId) {
        return this.recruitmentService.getRecruitmentDetailInfo(recruitmentId);
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addRecruitment(@RequestBody RecruitmentAddReq addReq) {
        RecruitmentInfoDO recruitmentInfoDo = RecruitmentUtil.transformRecruitmentInfoDo(addReq);
        this.recruitmentService.addRecruitmentInfo(recruitmentInfoDo,
            RecruitmentUtil.parseDepartmentDo(addReq.getHospitalDepartmentList()),
            ServletContext.getLoginInfo().getRealName());
        return true;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PutMapping("/{recruitmentId:\\d+}")
    public boolean updateRecruitment(@PathVariable Long recruitmentId, @RequestBody RecruitmentAddReq updateReq) {
        RecruitmentInfoDO recruitmentInfoDo = RecruitmentUtil.transformRecruitmentInfoDo(updateReq);
        recruitmentInfoDo.setId(recruitmentId);
        this.recruitmentService.updateRecruitmentInfo(recruitmentInfoDo,
            RecruitmentUtil.parseDepartmentDo(updateReq.getHospitalDepartmentList()),
            ServletContext.getLoginInfo().getRealName());
        return true;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PutMapping("/{recruitmentId:\\d+}/begin")
    public boolean beginRecruitment(@PathVariable Long recruitmentId) {
        this.recruitmentService.updateRecruitmentInfoStatus(recruitmentId, RecruitmentStatus.IN_PROCESS,
            ServletContext.getLoginInfo().getRealName());
        return true;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PutMapping("/{recruitmentId:\\d+}/end")
    public boolean endRecruitment(@PathVariable Long recruitmentId) {
        this.recruitmentService.updateRecruitmentInfoStatus(recruitmentId, RecruitmentStatus.FINISHED,
            ServletContext.getLoginInfo().getRealName());
        return true;
    }

    @GetMapping("/{recruitmentId:\\d+}/department")
    public List<DepartmentDetailRes> getDepartmentDetailOfRecruitment(@PathVariable Long recruitmentId) {
        List<Long> departmentIdList = recruitmentDepartmentService.listDepartmentIdByRecruitment(recruitmentId);
        return departmentService.getDepartmentDetailList(departmentIdList);
    }
}
