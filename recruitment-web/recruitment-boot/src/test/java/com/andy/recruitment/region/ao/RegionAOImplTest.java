package com.andy.recruitment.region.ao;

import com.andy.recruitment.region.model.Region;
import com.andy.recruitment.web.App;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.JsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;

public class RegionAOImplTest extends TestCase {

    private RegionAO regionAO;

    public void setUp() {
        App.main("");
        ApplicationContext context = App.getContext();
        this.regionAO = context.getBean(RegionAO.class);
    }

    public void testBuildAreaJs1() {
        List<Region> provinceList = regionAO.getRegionByParentId(null);
        List<Map<String, Object>> provinceMapList = new ArrayList<>(provinceList.size());
        for (Region region : provinceList) {
            Map<String, Object> provinceMap = new HashMap<>(3);
            provinceMapList.add(provinceMap);
            provinceMap.put("value", region.getRegionId());
            provinceMap.put("label", region.getRegionName());
            List<Region> cityList = regionAO.getRegionByParentId(region.getRegionId());
            List<Map<String, Object>> cityMapList = new ArrayList<>(provinceList.size());
            provinceMap.put("children", cityMapList);
            for (Region city : cityList) {
                Map<String, Object> cityMap = new HashMap<>(3);
                cityMapList.add(cityMap);
                cityMap.put("value", city.getRegionId());
                cityMap.put("label", city.getRegionName());
                List<Region> districtList = regionAO.getRegionByParentId(city.getRegionId());
                if (CollectionUtil.isEmpty(districtList)) {
                    continue;
                }
                List<Map<String, Object>> districtMapList = new ArrayList<>(districtList.size());
                cityMap.put("children", districtMapList);
                for (Region district : districtList) {
                    Map<String, Object> districtMap = new HashMap<>(3);
                    districtMapList.add(districtMap);
                    districtMap.put("value", district.getRegionId());
                    districtMap.put("label", district.getRegionName());
                }
            }
        }
        System.out.println(JsonUtil.toJson(provinceMapList));
    }

    public void testBuildAreaJs() {
        Map<String, String> provinceMap = new LinkedHashMap<>();
        Map<String, String> cityMap = new LinkedHashMap<>();
        Map<String, String> countyMap = new LinkedHashMap<>();

        List<Region> provinceList = regionAO.getRegionByParentId(null);
        for (int j = 0; j < provinceList.size(); ++ j) {
            Region province = provinceList.get(j);
            String pid = String.valueOf(j + 10);
            provinceMap.put(pid + "0000", province.getRegionName());
            List<Region> cityList = regionAO.getRegionByParentId(province.getRegionId());
            for (int i = 0; i < cityList.size(); ++ i) {
                int ii = i + 10;
                String cid = pid + ii;
                Region city = cityList.get(i);
                cityMap.put(cid + "00", city.getRegionName());

                List<Region> countyList = regionAO.getRegionByParentId(city.getRegionId());
                if (null == countyList) {
                    continue;
                }
                for (int t = 0; t < countyList.size(); ++ t) {
                    int tt = t + 10;
                    String aid = cid + tt;
                    Region county = countyList.get(t);
                    countyMap.put(aid, county.getRegionName());
                }
            }
        }
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("province_list", provinceMap);
        m.put("city_list", cityMap);
        m.put("county_list", countyMap);
        System.out.println(JsonUtil.toJson(m));
    }
}