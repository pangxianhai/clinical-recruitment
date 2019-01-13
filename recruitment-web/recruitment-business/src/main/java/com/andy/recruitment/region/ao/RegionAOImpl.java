package com.andy.recruitment.region.ao;

import com.andy.recruitment.region.model.BMapResult;
import com.andy.recruitment.region.model.Region;
import com.andy.recruitment.region.service.RegionService;
import com.xgimi.commons.util.HttpClientUtil;
import com.xgimi.commons.util.JsonUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 地区字典业务实现
 *
 * @author 庞先海 2018-12-27
 */
@Component
public class RegionAOImpl implements RegionAO {

    @Value("${baidu.map.api}")
    private String bMapApi;

    @Value("${baidu.map.ak}")
    private String bAk;

    /**
     * 默认为南充
     */
    private final static long DEFAULT_REGION_ID = 334L;

    private final RegionService regionService;

    @Autowired
    public RegionAOImpl(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public Region getRegionById(Long regionId) {
        return this.regionService.getRegionById(regionId);
    }

    @Override
    public List<Region> getRegionByParentId(Long parentId) {
        if (parentId == null) {
            //默认获取中国下所有的省
            parentId = RegionService.CHINA_REGION_ID;
        }
        return this.regionService.getRegionByParentId(parentId);

    }

    @Override
    public Region getRegionByParent(Long parentId, String regionName) {
        return this.regionService.getRegionByParent(parentId, regionName);
    }

    @Override
    public Region getRegionByLngLat(String lng, String lat) {
        String url = bMapApi + "/geocoder/v2/";
        Map<String, Object> params = new HashMap<>(4);
        params.put("ak", bAk);
        params.put("location", lng + "," + lat);
        params.put("output", "json");
        params.put("coordtype", "wgs84ll");
        String bResult = HttpClientUtil.sendGet(url, params);
        BMapResult bMapResult = JsonUtil.fromJson(bResult, BMapResult.class);
        String provinceStr = bMapResult.getResult().getAddressComponent().getProvince();
        String cityStr = bMapResult.getResult().getAddressComponent().getCity();
        Region province = this.regionService.getRegionByParent(RegionService.CHINA_REGION_ID, provinceStr);
        if (null != province) {
            return this.regionService.getRegionByParent(province.getRegionId(), cityStr);
        }
        return this.regionService.getRegionById(DEFAULT_REGION_ID);
    }
}
