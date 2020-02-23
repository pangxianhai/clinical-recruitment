package com.andy.recruitment.biz.region.service;

import com.andy.recruitment.biz.region.entity.AddressInfo;
import com.andy.recruitment.dao.region.dao.RegionDAO;
import com.andy.recruitment.dao.region.entity.RegionDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地区服务实现
 *
 * @author 庞先海 2020-01-30
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionDAO regionDAO;

    @Autowired
    public RegionServiceImpl(RegionDAO regionDAO) {
        this.regionDAO = regionDAO;
    }

    @Override
    public RegionDO getRegionById(Long regionId) {
        return this.regionDAO.getRegionById(regionId);
    }

    @Override
    public String getRegionNameById(Long regionId) {
        RegionDO regionDo = this.getRegionById(regionId);
        if (regionDo != null) {
            return regionDo.getRegionName();
        }
        return null;
    }

    @Override
    public List<RegionDO> getRegionByParentId(Long parentId) {
        return this.regionDAO.getRegionByParentId(parentId);
    }

    @Override
    public RegionDO getRegionByParent(Long parentId, String regionName) {
        return this.regionDAO.getRegionByParent(parentId, regionName);
    }

    @Override
    public String parseAddressName(Long provinceId, Long cityId, Long districtId) {
        StringBuilder buffer = new StringBuilder();
        RegionDO province = this.getRegionById(provinceId);
        RegionDO city = this.getRegionById(cityId);
        RegionDO district = this.getRegionById(districtId);
        if (null != province) {
            buffer.append(province.getRegionName());
        }
        boolean cityAndProvinceSame = null != province && city.getRegionName().equals(province.getRegionName());
        if (null != city && ! cityAndProvinceSame) {
            buffer.append(city.getRegionName());
        }
        if (null != district) {
            buffer.append(district.getRegionName());
        }
        return buffer.toString();
    }

    @Override
    public AddressInfo parseAddressInfo(String text) {
        if (null == text) {
            return null;
        }
        String[] addressArr = text.split(" ");
        AddressInfo addressInfo = new AddressInfo();
        if (addressArr.length == 2) {
            this.parseAddressInfo(addressInfo, addressArr[0], addressArr[0], addressArr[1]);
        } else if (addressArr.length == 3) {
            this.parseAddressInfo(addressInfo, addressArr[0], addressArr[1], addressArr[2]);
        }
        return addressInfo;
    }

    private void parseAddressInfo(AddressInfo addressInfo, String provinceName, String cityName, String districtName) {
        RegionDO province = this.regionDAO.getRegionByParent(RegionDAO.CHINA_REGION_ID, provinceName);
        addressInfo.setProvince(province);
        if (null == province) {
            return;
        }
        RegionDO city = this.regionDAO.getRegionByParent(province.getId(), cityName);
        addressInfo.setCity(city);
        if (null == city) {
            return;
        }
        RegionDO district = this.regionDAO.getRegionByParent(city.getId(), districtName);
        addressInfo.setDistrict(district);
    }
}
