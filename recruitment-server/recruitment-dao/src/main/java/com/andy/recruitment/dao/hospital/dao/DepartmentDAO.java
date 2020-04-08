package com.andy.recruitment.dao.hospital.dao;

import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.hospital.entity.DepartmentQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;

/**
 * 机构科室 dao 接口
 *
 * @author 庞先海 2020-01-31
 */
public interface DepartmentDAO {

    /**
     * 添加机构科室
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
    PageResult<DepartmentDO> getDepartment(DepartmentQuery query, Paginator paginator);

    /**
     * 通过机构id获取所有科室信息
     *
     * @param hospitalId 机构id
     * @return 科室信息
     */
    List<DepartmentDO> getDepartmentByHospital(Long hospitalId);

    /**
     * 通过科室id批量查询科室信息
     *
     * @param departmentIdList 科室id列表
     * @return 科室信息
     */
    List<DepartmentDO> getDepartmentByIdList(List<Long> departmentIdList);

    /**
     * 通过科室id查询科室信息
     *
     * @param departmentId 科室id
     * @return 科室信息
     */
    DepartmentDO getDepartmentById(Long departmentId);
}
