package com.andy.recruitment.biz.hospital.service;

import com.andy.recruitment.api.hospital.response.HospitalRes;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.hospital.dao.HospitalDAO;
import com.andy.recruitment.dao.hospital.entity.HospitalDO;
import com.andy.recruitment.dao.hospital.entity.HospitalQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构服务实现
 *
 * @author 庞先海 2020-01-30
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalDAO hospitalDAO;

    private final RegionService regionService;

    @Autowired
    public HospitalServiceImpl(HospitalDAO hospitalDAO, RegionService regionService) {
        this.hospitalDAO = hospitalDAO;
        this.regionService = regionService;
    }

    @Override
    public void addHospital(HospitalDO hospitalDo, String operator) {
        this.hospitalDAO.addHospital(hospitalDo, operator);
    }

    @Override
    public void updateHospital(HospitalDO hospitalDo, String operator) {
        this.hospitalDAO.updateHospitalInfo(hospitalDo, operator);
    }

    @Override
    public PageResult<HospitalRes> getHospital(HospitalQuery query, Paginator paginator) {
        if (StringUtils.isEmpty(paginator.getOrderSegment())) {
            paginator.setOrderSegment("created_time.desc");
        }
        PageResult<HospitalDO> pageResult = this.hospitalDAO.getHospital(query, paginator);
        List<HospitalRes> hospitalResList = this.transformOrganizationRes(pageResult.getData());
        return new PageResult<>(hospitalResList, pageResult.getPaginator());
    }

    @Override
    public List<HospitalDO> getHospitalByIdList(List<Long> hospitalIdList) {
        return this.hospitalDAO.getHospitalByIdList(hospitalIdList);
    }

    @Override
    public Map<Long, HospitalRes> getHospitalRes(List<Long> hospitalIdList) {
        List<HospitalDO> hospitalDoList = this.hospitalDAO.getHospitalByIdList(hospitalIdList);
        List<HospitalRes> hospitalResList = this.transformOrganizationRes(hospitalDoList);
        return hospitalResList.stream().collect(
            Collectors.toMap(HospitalRes::getHospitalId, Function.identity(), (o1, o2) -> o1));
    }

    @Override
    public HospitalDO getHospitalById(Long hospitalId) {
        return this.hospitalDAO.getHospitalById(hospitalId);
    }

    private List<HospitalRes> transformOrganizationRes(List<HospitalDO> hospitalDoList) {
        if (CollectionUtils.isEmpty(hospitalDoList)) {
            return Collections.emptyList();
        }
        return hospitalDoList.stream().map(this::transformOrganizationRes).filter(Objects::nonNull).collect(
            Collectors.toList());
    }


    private HospitalRes transformOrganizationRes(HospitalDO organizationDo) {
        if (organizationDo == null) {
            return null;
        }
        HospitalRes organizationRes = new HospitalRes();
        BeanUtils.copyProperties(organizationDo, organizationRes);
        organizationRes.setProvinceName(regionService.getRegionNameById(organizationDo.getProvinceId()));
        organizationRes.setCityName(regionService.getRegionNameById(organizationDo.getCityId()));
        organizationRes.setDistrictName(regionService.getRegionNameById(organizationDo.getDistrictId()));
        organizationRes.setAddress(
            regionService.parseAddressName(organizationDo.getProvinceId(), organizationDo.getCityId(),
                organizationDo.getDistrictId()));
        organizationRes.setHospitalId(organizationDo.getId());
        return organizationRes;
    }
}
