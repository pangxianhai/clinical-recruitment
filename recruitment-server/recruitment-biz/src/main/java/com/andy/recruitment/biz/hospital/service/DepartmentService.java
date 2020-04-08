package com.andy.recruitment.biz.hospital.service;

import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.hospital.entity.DepartmentQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;
import java.util.Map;

/**
 * 机构科室服务接口
 *
 * @author 庞先海 2020-01-31
 */
public interface DepartmentService {

    /**
     * 添加科室
     *
     * @param departmentDo 科室信息
     * @param operator     操作人
     */
    void addDepartment(DepartmentDO departmentDo, String operator);

    /**
     * 更新科室信息
     *
     * @param departmentDo 科室信息
     * @param operator     操作人
     */
    void updateDepartment(DepartmentDO departmentDo, String operator);

    /**
     * 分页查询机构科室信息
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 科室信息
     */
    PageResult<DepartmentDetailRes> getDepartment(DepartmentQuery query, Paginator paginator);

    /**
     * 通过机构id获取所有科室信息
     *
     * @param hospitalId 机构id
     * @return 科室信息
     */
    List<DepartmentDetailRes> getDepartmentByHospital(Long hospitalId);

    /**
     * 通过科室ID列表查询科室信息
     *
     * @param departmentIdList 科室ID列表
     * @return 科室信息
     */
    List<DepartmentDO> getDepartmentByIdList(List<Long> departmentIdList);


    /**
     * 通过科室ID列表查询科室信息
     *
     * @param departmentIdList 科室ID列表
     * @return 科室信息
     */
    List<DepartmentDetailRes> getDepartmentDetailList(List<Long> departmentIdList);

    /**
     * 通过科室ID列表查询科室信息
     *
     * @param departmentIdList 科室ID列表
     * @return 科室详细信息
     */
    Map<Long, DepartmentDetailRes> getDepartmentDetailRes(List<Long> departmentIdList);

    /**
     * 通过科室id查询科室信息
     *
     * @param departmentId 科室id
     * @return 科室信息
     */
    DepartmentDetailRes getDepartmentById(Long departmentId);

    /**
     * 通过科室id查询科室信息
     *
     * @param departmentId 科室id
     * @return 科室信息
     */
    DepartmentDO getDepartmentDoById(Long departmentId);
}
