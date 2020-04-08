package com.andy.recruitment.biz.hospital.service;

import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.api.hospital.response.DepartmentRes;
import com.andy.recruitment.api.hospital.response.HospitalRes;
import com.andy.recruitment.dao.hospital.dao.DepartmentDAO;
import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.hospital.entity.DepartmentQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构科室服务接口实现
 *
 * @author 庞先海 2020-01-31
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO;

    private final HospitalService hospitalService;

    @Autowired
    public DepartmentServiceImpl(DepartmentDAO departmentDAO, HospitalService hospitalService) {
        this.departmentDAO = departmentDAO;
        this.hospitalService = hospitalService;
    }

    @Override
    public void addDepartment(DepartmentDO departmentDo, String operator) {
        this.departmentDAO.addDepartment(departmentDo, operator);
    }

    @Override
    public void updateDepartment(DepartmentDO departmentDo, String operator) {
        this.departmentDAO.updateDepartment(departmentDo, operator);
    }

    @Override
    public PageResult<DepartmentDetailRes> getDepartment(DepartmentQuery query, Paginator paginator) {
        PageResult<DepartmentDO> pageResult = this.departmentDAO.getDepartment(query, paginator);
        List<DepartmentDetailRes> departmentDetailResList = this.transformDepartmentDetailRes(pageResult.getData());
        return new PageResult<>(departmentDetailResList, pageResult.getPaginator());
    }

    @Override
    public List<DepartmentDetailRes> getDepartmentByHospital(Long hospitalId) {
        List<DepartmentDO> departmentDoList = this.departmentDAO.getDepartmentByHospital(hospitalId);
        return this.transformDepartmentDetailRes(departmentDoList);
    }

    @Override
    public List<DepartmentDO> getDepartmentByIdList(List<Long> departmentIdList) {
        return this.departmentDAO.getDepartmentByIdList(departmentIdList);
    }

    @Override
    public List<DepartmentDetailRes> getDepartmentDetailList(List<Long> departmentIdList) {
        List<DepartmentDO> organizationDepartmentDoList = this.departmentDAO.getDepartmentByIdList(departmentIdList);
        if (CollectionUtils.isEmpty(organizationDepartmentDoList)) {
            return Collections.emptyList();
        }
        return transformDepartmentDetailRes(organizationDepartmentDoList);
    }

    @Override
    public Map<Long, DepartmentDetailRes> getDepartmentDetailRes(List<Long> departmentIdList) {
        List<DepartmentDetailRes> departmentDetailResList = this.getDepartmentDetailList(departmentIdList);
        if (CollectionUtils.isEmpty(departmentDetailResList)) {
            return Collections.emptyMap();
        }
        return departmentDetailResList.stream().collect(
            Collectors.toMap(DepartmentRes::getDepartmentId, Function.identity(), (d1, d2) -> d1));
    }

    @Override
    public DepartmentDetailRes getDepartmentById(Long departmentId) {
        DepartmentDO departmentDo = this.departmentDAO.getDepartmentById(departmentId);
        List<DepartmentDetailRes> departmentDetailResList = this.transformDepartmentDetailRes(
            Collections.singletonList(departmentDo));
        if (CollectionUtils.isEmpty(departmentDetailResList)) {
            return null;
        }
        return departmentDetailResList.get(0);
    }

    @Override
    public DepartmentDO getDepartmentDoById(Long departmentId) {
        return this.departmentDAO.getDepartmentById(departmentId);
    }

    private List<DepartmentDetailRes> transformDepartmentDetailRes(List<DepartmentDO> departmentDoList) {
        if (CollectionUtils.isEmpty(departmentDoList)) {
            return Collections.emptyList();
        }
        List<Long> hospitalIdList = departmentDoList.stream().map(DepartmentDO::getHospitalId).collect(
            Collectors.toList());
        Map<Long, HospitalRes> organizationResMap = this.hospitalService.getHospitalRes(hospitalIdList);
        return departmentDoList.stream().map(
            departmentDo -> transformDepartmentDetailRes(departmentDo, organizationResMap)).collect(
            Collectors.toList());
    }

    private DepartmentDetailRes transformDepartmentDetailRes(DepartmentDO departmentDo,
        Map<Long, HospitalRes> hospitalResMap) {
        DepartmentDetailRes departmentDetailRes = new DepartmentDetailRes();
        BeanUtils.copyProperties(departmentDo, departmentDetailRes);
        departmentDetailRes.setDepartmentId(departmentDo.getId());
        if (hospitalResMap != null) {
            departmentDetailRes.setHospitalRes(hospitalResMap.get(departmentDo.getHospitalId()));
        }
        return departmentDetailRes;
    }
}
