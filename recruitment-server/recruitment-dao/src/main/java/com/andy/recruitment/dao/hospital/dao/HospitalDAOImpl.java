package com.andy.recruitment.dao.hospital.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.hospital.entity.HospitalDO;
import com.andy.recruitment.dao.hospital.entity.HospitalQuery;
import com.andy.recruitment.dao.hospital.mapper.HospitalInfoMapper;
import com.andy.spring.mybatis.paginator.Page;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.PageUtil;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.CollectionUtil;
import com.andy.spring.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构信息 dao 实现
 *
 * @author 庞先海 2020-01-27
 */
@Service
public class HospitalDAOImpl implements HospitalDAO {

    private final HospitalInfoMapper hospitalInfoMapper;

    @Autowired
    public HospitalDAOImpl(HospitalInfoMapper hospitalInfoMapper) {
        this.hospitalInfoMapper = hospitalInfoMapper;
    }

    @Override
    public void addHospital(HospitalDO hospitalDo, String operator) {
        hospitalDo.setCreatedBy(operator);
        hospitalDo.setCreatedTime(LocalDateTime.now());
        int count = this.hospitalInfoMapper.insert(hospitalDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.HOSPITAL_ADD_FAILED);
        });
    }

    @Override
    public void updateHospitalInfo(HospitalDO hospitalDo, String operator) {
        AssertUtil.assertNull(hospitalDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.HOSPITAL_ID_EMPTY);
        });
        hospitalDo.setUpdatedBy(operator);
        hospitalDo.setUpdatedTime(LocalDateTime.now());
        int count = this.hospitalInfoMapper.update(hospitalDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.HOSPITAL_UPDATE_FAILED);
        });
    }

    @Override
    public List<HospitalDO> getHospitalByIdList(List<Long> hospitalIdList) {
        if (CollectionUtils.isEmpty(hospitalIdList)) {
            return null;
        }
        hospitalIdList = hospitalIdList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(hospitalIdList)) {
            return null;
        }
        HospitalQuery query = new HospitalQuery();
        query.setHospitalIdList(hospitalIdList);
        return this.hospitalInfoMapper.select(query);
    }

    @Override
    public HospitalDO getHospitalById(Long hospitalId) {
        if (hospitalId == null) {
            return null;
        }
        HospitalQuery query = new HospitalQuery();
        query.setHospitalId(hospitalId);
        List<HospitalDO> organizationDoList = this.hospitalInfoMapper.select(query);
        return CollectionUtil.parseOne(organizationDoList, Function.identity());

    }


    @Override
    public PageResult<HospitalDO> getHospital(HospitalQuery query, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<HospitalDO> organizationDoList = this.hospitalInfoMapper.select(query, page);
        return new PageResult<>(organizationDoList, PageUtil.transformToPaginator(page));
    }
}
