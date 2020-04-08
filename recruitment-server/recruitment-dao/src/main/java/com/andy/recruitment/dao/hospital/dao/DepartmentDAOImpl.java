package com.andy.recruitment.dao.hospital.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.hospital.entity.DepartmentQuery;
import com.andy.recruitment.dao.hospital.mapper.DepartmentInfoMapper;
import com.andy.spring.mybatis.paginator.Page;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.PageUtil;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.CollectionUtil;
import com.andy.spring.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构科室 dao 接口实现
 *
 * @author 庞先海 2020-01-31
 */
@Service
public class DepartmentDAOImpl implements DepartmentDAO {

    private final DepartmentInfoMapper departmentInfoMapper;

    @Autowired
    public DepartmentDAOImpl(DepartmentInfoMapper departmentInfoMapper) {
        this.departmentInfoMapper = departmentInfoMapper;
    }

    @Override
    public void addDepartment(DepartmentDO departmentDo, String operator) {
        departmentDo.setCreatedBy(operator);
        departmentDo.setCreatedTime(LocalDateTime.now());
        int count = this.departmentInfoMapper.insert(departmentDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.DEPARTMENT_ADD_FAILED);
        });
    }

    @Override
    public void updateDepartment(DepartmentDO departmentDo, String operator) {
        AssertUtil.assertNull(departmentDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.DEPARTMENT_ID_EMPTY);
        });
        departmentDo.setUpdatedBy(operator);
        departmentDo.setUpdatedTime(LocalDateTime.now());
        int count = this.departmentInfoMapper.update(departmentDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.DEPARTMENT_UPDATE_FAILED);
        });
    }

    @Override
    public PageResult<DepartmentDO> getDepartment(DepartmentQuery query, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<DepartmentDO> departmentDoList = this.departmentInfoMapper.select(query, page);
        return new PageResult<>(departmentDoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public List<DepartmentDO> getDepartmentByHospital(Long hospitalId) {
        if (hospitalId == null) {
            return null;
        }
        DepartmentQuery query = new DepartmentQuery();
        query.setHospitalId(hospitalId);
        return this.departmentInfoMapper.select(query);
    }

    @Override
    public List<DepartmentDO> getDepartmentByIdList(List<Long> departmentIdList) {
        if (CollectionUtils.isEmpty(departmentIdList)) {
            return null;
        }
        DepartmentQuery query = new DepartmentQuery();
        query.setDepartmentIdList(departmentIdList);
        return this.departmentInfoMapper.select(query);
    }

    @Override
    public DepartmentDO getDepartmentById(Long departmentId) {
        if (departmentId == null) {
            return null;
        }
        DepartmentQuery query = new DepartmentQuery();
        query.setDepartmentId(departmentId);
        List<DepartmentDO> departmentDoList = this.departmentInfoMapper.select(query);
        return CollectionUtil.parseOne(departmentDoList, Function.identity());
    }
}
