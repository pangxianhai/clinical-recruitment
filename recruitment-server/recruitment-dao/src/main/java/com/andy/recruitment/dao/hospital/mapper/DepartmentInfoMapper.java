package com.andy.recruitment.dao.hospital.mapper;

import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.hospital.entity.DepartmentQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 机构科室 mapper 接口
 *
 * @author 庞先海 2020-01-31
 */
@Repository
public interface DepartmentInfoMapper {

    /**
     * 科室查询
     *
     * @param query 查询参数
     * @return 科室信息
     */
    List<DepartmentDO> select(DepartmentQuery query);

    /**
     * 科室分页查询
     *
     * @param query 查询参数
     * @param page  分页参数
     * @return 科室信息
     */
    List<DepartmentDO> select(DepartmentQuery query, Page page);

    /**
     * 添加科室信息
     *
     * @param departmentDo 科室信息
     * @return 添加数量
     */
    int insert(DepartmentDO departmentDo);

    /**
     * 更新科室信息
     *
     * @param departmentDo 科室信息
     * @return 更新数量
     */
    int update(DepartmentDO departmentDo);
}